package com.cvqs.usermanagementservice.service.concretes;

import com.cvqs.usermanagementservice.dto.AuthRequest;
import com.cvqs.usermanagementservice.dto.AuthenticationResponse;
import com.cvqs.usermanagementservice.dto.UserDto;
import com.cvqs.usermanagementservice.exception.EntityNotFoundException;
import com.cvqs.usermanagementservice.exception.ServerRequestException;
import com.cvqs.usermanagementservice.model.Role;
import com.cvqs.usermanagementservice.model.User;
import com.cvqs.usermanagementservice.repository.UserRepository;
import com.cvqs.usermanagementservice.service.abstracts.RoleService;
import com.cvqs.usermanagementservice.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 *  UserManager sınıfı, UserService arayüzünden türetilmiştir ve
 *  user işlemlerini yönetir. Bu sınıf, veritabanı işlemleri için UserRepository nesnelerini kullanmaktadır.
 *
 *  @author Enes Bekkaya
 *  @since  26.02.2023
 */
@Service
@RequiredArgsConstructor
public class UserManager implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private RestTemplate restTemplate;

    private static final Logger LOGGER= LoggerFactory.getLogger(UserManager.class);

    /**
     * Veritabanındaki tüm kullanıcıları alır ve UserDto listesi olarak döndürür.
     * @return Veritabanındaki tüm kullanıcıların UserDto listesi.
     */
    @Override
    public List<UserDto> getAll() {
        List <User> users=userRepository.findAll();
        List<UserDto> userDtos=users.stream().map(user1 -> modelMapper.map(user1,UserDto.class)).collect(Collectors.toList());
        return userDtos;
    }

    /**
     * UserDto nesnesini alır, bu kullanıcının rollerini veritabanından alır ve kullanıcıyı kaydeder.
     *
     * @param userDto kaydedilecek UserDto nesnesi
     * @throw new ResponseStatusException kullanıcı adı veritabanında kayıtlıysa fırlatılır
     * @return kaydedilen UserDto nesnesi

     */
    @Override
    public UserDto save(UserDto userDto) {
        if(userRepository.findUserByUsername(userDto.getUsername())!=null){
            LOGGER.warn("işlem başarısız!!{} adlı kullanıcı adı zaten kayıtlı. ", userDto.getUsername());
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Bu kullanıcı adı zaten kayıtlı.");
        }
        User newUser=new User();
        List<Role> roles=new ArrayList<>();
        userDto.getRoles().forEach(role -> {
            Role savedRole=roleService.findRoleByName(role.getName());

            roles.add(savedRole);
        });
        newUser.setDeleted(false);
        newUser.setUsername(userDto.getUsername());
        newUser.setName(userDto.getName());
        newUser.setLastname(userDto.getLastname());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setRoles(roles);
        return modelMapper.map(userRepository.save(newUser), UserDto.class);
    }

    /**
     * UserDto nesnesini alır ve veritabanında bu kullanıcı adına sahip bir kullanıcı varsa,
     * kullanıcının bilgilerini günceller ve güncellenen UserDto nesnesini döndürür.
     * Eğer kullanıcı yoksa, EntityNotFoundException fırlatır.
     * @param userDto güncellenecek UserDto nesnesi
     * @return güncellenen UserDto nesnesi
     * @throws EntityNotFoundException kullanıcı adına sahip bir kullanıcı yoksa fırlatılır
     */
    @Override
    public UserDto updateUser(UserDto userDto ) {
        User user=userRepository.findUserByUsername(userDto.getUsername());
        List<Role> roles=new ArrayList<>();
        if(user==null) {
            LOGGER.warn("işlem başarısız!!{} adlı kullanıcı bulunamadı ", userDto.getUsername());
            throw new EntityNotFoundException(userDto.getUsername() + " kullanıcı isimli bir kullanıcı bulunamadı");
        }
        userDto.getRoles().forEach(role -> {
            Role savedRole=roleService.findRoleByName(role.getName());

            roles.add(savedRole);
        });
        user.setName(userDto.getName());
        user.setLastname(userDto.getLastname());
        user.setPassword(userDto.getPassword());
        user.setRoles(roles);
        return modelMapper.map(userRepository.save(user),UserDto.class);
    }

    /**
     * UserDto nesnesini alır ve veritabanında bu kullanıcı adına sahip bir kullanıcı varsa,
     * kullanıcının deleted özelliğini true olarak günceller ve güncellenen UserDto nesnesini döndürür.
     * Eğer kullanıcı yoksa, EntityNotFoundException fırlatır.
     * @param username silinecek kullanıcının kullanıcı ismi
     * @return silinen UserDto nesnesi
     * @throws EntityNotFoundException kullanıcı adına sahip bir kullanıcı yoksa fırlatılır
     */
    @Override
    public UserDto delete(String username) {
        User user=userRepository.findUserByUsername(username);
        if(user==null) {
            LOGGER.warn("işlem başarısız!!{} adlı kullanıcı bulunamadı ", username);
            throw new EntityNotFoundException(username + "  kullanıcı isimli bir kullanıcı bulunamadı");
        }
        user.setDeleted(true);
        return modelMapper.map(userRepository.save(user),UserDto.class);
    }

    /**
     * Kullanıcının kimlik bilgileriyle kimlik doğrulamasını yapmak için, kimlik doğrulama sunucusuna REST isteği yaparak kullanıcının kimlik bilgilerini doğrular.
     * @param authRequest kullanıcının kimlik bilgilerini içeren kimlik doğrulama isteği
     * @return kullanıcının kimlik bilgisini içeren AuthenticationResponse nesnesi
     * @throws ServerRequestException sunucuya REST isteği yaparken bir hata oluşursa fırlatılır
     */
    @Override
    public AuthenticationResponse login(AuthRequest authRequest) {
        try {
            String url = "http://localhost:9092/auth/authenticate";
            AuthenticationResponse response = restTemplate.postForObject(url, authRequest, AuthenticationResponse.class);
            return response;
        }catch (Exception e){
            LOGGER.warn("Failed to make request to the server");
            throw new ServerRequestException("Failed to make request to the server");
        }
    }


}
