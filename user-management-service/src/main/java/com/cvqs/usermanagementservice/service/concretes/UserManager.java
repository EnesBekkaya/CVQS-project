package com.cvqs.usermanagementservice.service.concretes;

import com.cvqs.usermanagementservice.dto.UserDto;
import com.cvqs.usermanagementservice.exception.EntityNotFoundException;
import com.cvqs.usermanagementservice.model.Role;
import com.cvqs.usermanagementservice.model.User;
import com.cvqs.usermanagementservice.repository.UserRepository;
import com.cvqs.usermanagementservice.service.abstracts.RoleService;
import com.cvqs.usermanagementservice.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
     *
     * @return kaydedilen UserDto nesnesi

     */
    @Override
    public UserDto save(UserDto userDto) {
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
            LOGGER.warn("Parameter user is null in updateUser() method.");
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
     * @param userDto silinecek UserDto nesnesi
     * @return silinen UserDto nesnesi
     * @throws EntityNotFoundException kullanıcı adına sahip bir kullanıcı yoksa fırlatılır
     */
    @Override
    public UserDto delete(UserDto userDto) {
        User user=userRepository.findUserByUsername(userDto.getUsername());
        if(user==null) {
            LOGGER.warn("Parameter user is null in delete() method.");

            throw new EntityNotFoundException(userDto.getUsername() + "  kullanıcı isimli bir kullanıcı bulunamadı");
        }
        user.setDeleted(true);
        return modelMapper.map(userRepository.save(user),UserDto.class);
    }

    /**
     * Verilen kullanıcı adına sahip kullanıcıyı veritabanında arar ve bulduğu takdirde kullanıcıyı
     * User nesnesi olarak döndürür. Eğer kullanıcı bulunamazsa null döndürür.
     * @param userName aranacak kullanıcının kullanıcı adı
     * @return bulunan kullanıcıyı User nesnesi olarak döndürür. Kullanıcı bulunamazsa null döndürür.

     */
    @Override
    public User findUserByUserName(String userName) {
       return userRepository.findUserByUsername(userName);

    }
}
