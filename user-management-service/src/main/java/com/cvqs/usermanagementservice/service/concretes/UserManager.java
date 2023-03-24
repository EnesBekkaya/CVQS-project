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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserManager implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private static final Logger LOGGER= LoggerFactory.getLogger(UserManager.class);

    @Override
    public List<UserDto> getAll() {
        List <User> users=userRepository.findAll();
        List<UserDto> userDtos=users.stream().map(user1 -> modelMapper.map(user1,UserDto.class)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public UserDto save(UserDto userDto) {
        User newUser=new User();
        List<Role> roles=new ArrayList<>();
        userDto.getRoles().forEach(role -> {
            Role savedRole=roleService.findRoleByName(role.getName());

            roles.add(savedRole);
        });
        newUser.setDeleted(false);
        newUser.setUserName(userDto.getUserName());
        newUser.setName(userDto.getName());
        newUser.setLastName(userDto.getLastName());
        newUser.setPassword(userDto.getPassword());
        newUser.setRoles(roles);
        return modelMapper.map(userRepository.save(newUser), UserDto.class);
    }
    @Override
    public UserDto updateUser(UserDto userDto ) {
        User user=userRepository.findUserByUserName(userDto.getUserName());
        List<Role> roles=new ArrayList<>();
        if(user==null) {
            LOGGER.warn("Parameter user is null in updateUser() method.");
            throw new EntityNotFoundException(userDto.getUserName() + " kullanıcı isimli bir kullanıcı bulunamadı");
        }
        userDto.getRoles().forEach(role -> {
            Role savedRole=roleService.findRoleByName(role.getName());

            roles.add(savedRole);
        });
        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setRoles(roles);
        return modelMapper.map(userRepository.save(user),UserDto.class);
    }

    @Override
    public UserDto delete(UserDto userDto) {
        User user=userRepository.findUserByUserName(userDto.getUserName());
        if(user==null) {
            LOGGER.warn("Parameter user is null in delete() method.");

            throw new EntityNotFoundException(userDto.getUserName() + "  kullanıcı isimli bir kullanıcı bulunamadı");
        }
        user.setDeleted(true);
        return modelMapper.map(userRepository.save(user),UserDto.class);
    }

    @Override
    public User findUserByUserName(String userName) {
       return userRepository.findUserByUserName(userName);

    }
}
