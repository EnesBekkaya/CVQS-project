package com.cvqs.usermanagementservice.service.concretes;

import com.cvqs.usermanagementservice.dto.UserDto;
import com.cvqs.usermanagementservice.exception.EntityNotFoundException;
import com.cvqs.usermanagementservice.model.Role;
import com.cvqs.usermanagementservice.model.User;
import com.cvqs.usermanagementservice.repository.UserRepository;
import com.cvqs.usermanagementservice.service.abstracts.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


class UserManagerTest {
    private UserManager userManager;
    private UserRepository userRepository;
    private RoleService roleService;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        roleService=Mockito.mock(RoleService.class);
        passwordEncoder=Mockito.mock(PasswordEncoder.class);
        userManager = new UserManager(userRepository,roleService,modelMapper,passwordEncoder);
    }
    @DisplayName("should GetAll User And Return UserDto List")
    @Test
    void shouldGetAllUserAndReturnUserDtoList(){
        User user=new User();
        user.setName("testName");
        user.setUsername("testUsername");
        user.setPassword("testPassword");
        user.setDeleted(false);

        User user2=new User();
        user2.setName("testName");
        user2.setUsername("testUsername");
        user2.setPassword("testPassword");
        user2.setDeleted(false);

        List<User> users=new ArrayList<>(Arrays.asList(user,user2));

        List<UserDto> expectedResult=users.stream().map(user1 -> modelMapper.map(user1,UserDto.class)).collect(Collectors.toList());

        Mockito.when(userRepository.findAll()).thenReturn(users);

        List<UserDto> result=userManager.getAll();

        Assertions.assertEquals(expectedResult,result);

        Mockito.verify(userRepository).findAll();
    }

    @DisplayName("should Save User By UserDto And Return UserDto")
    @Test
    void shouldSaveUserByUserDtoAndReturnUserDto(){
        Role role=new Role();
        role.setName("admin");
        List<Role>roles=new ArrayList<>(Arrays.asList(role));

        UserDto userDto=new UserDto();
        userDto.setName("testName");
        userDto.setUsername("testUsername");
        userDto.setPassword("testPassword");
        userDto.setRoles(roles);
        User user=new User();
        user.setName("testName");
        user.setUsername("testUsername");
        user.setPassword("encodedPassword");
        user.setRoles(roles);
        UserDto expectedResult=modelMapper.map(user,UserDto.class);

        Mockito.when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        Mockito.when(roleService.findRoleByName(role.getName())).thenReturn(role);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        UserDto result=userManager.save(userDto);

        Assertions.assertEquals(expectedResult,result);

        Mockito.verify(passwordEncoder).encode(userDto.getPassword());
        Mockito.verify(roleService).findRoleByName(role.getName());
        Mockito.verify(userRepository).save(user);
    }

    @DisplayName("should Update User By UserDto And Return UserDto")
    @Test
    void shouldUpdateUserByUserDtoAndReturnUserDto(){
        Role role=new Role();
        role.setName("admin");
        List<Role>roles=new ArrayList<>(Arrays.asList(role));

        UserDto userDto=new UserDto();
        userDto.setName("testName");
        userDto.setUsername("testUsername");
        userDto.setPassword("testPassword");
        userDto.setRoles(roles);
        User user=new User();
        user.setName("testName");
        user.setUsername("testUsername");
        user.setPassword("encodedPassword");
        user.setRoles(roles);

        UserDto expectedResult=modelMapper.map(user,UserDto.class);

        Mockito.when(userRepository.findUserByUsername(user.getUsername())).thenReturn(user);
        Mockito.when(roleService.findRoleByName(role.getName())).thenReturn(role);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        UserDto result=userManager.updateUser(userDto);

        Assertions.assertEquals(expectedResult,result);

        Mockito.verify(userRepository).findUserByUsername(userDto.getUsername());
        Mockito.verify(roleService).findRoleByName(role.getName());
        Mockito.verify(userRepository).save(user);
    }
    @DisplayName("should Throw EntityNotFoundException When User Null")
    @Test
    void shouldThrowEntityNotFoundExceptionWhenUserNull(){
        Role role=new Role();
        role.setName("admin");
        List<Role>roles=new ArrayList<>(Arrays.asList(role));

        UserDto userDto=new UserDto();
        userDto.setName("testName");
        userDto.setUsername("testUsername");
        userDto.setPassword("testPassword");
        userDto.setRoles(roles);
        User user=new User();
        user.setName("testName");
        user.setUsername("testUsername");
        user.setPassword("encodedPassword");
        user.setRoles(roles);

        Mockito.when(userRepository.findUserByUsername(user.getUsername())).thenReturn(null);

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            userManager.updateUser(userDto);
        });

        String expectedMessage = userDto.getUsername() + " kullanıcı isimli bir kullanıcı bulunamadı";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);

        Mockito.verify(userRepository).findUserByUsername(userDto.getUsername());
    }
    @DisplayName("should Delete User By UserDto When User IsExist")
    @Test
    void shouldDeleteUserByUserDtoWhenUserIsExist(){
        UserDto userDto=new UserDto();
        userDto.setName("testName");
        userDto.setUsername("testUsername");
        userDto.setPassword("testPassword");
        User user=new User();
        user.setName("testName");
        user.setUsername("testUsername");
        user.setPassword("encodedPassword");

        UserDto expectedResult=modelMapper.map(user,UserDto.class);

        Mockito.when(userRepository.findUserByUsername(userDto.getUsername())).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        UserDto result=userManager.delete(userDto.getUsername());

        Assertions.assertEquals(expectedResult,result);
        Mockito.verify(userRepository).findUserByUsername(userDto.getUsername());
        Mockito.verify(userRepository).save(user);

    }
    @DisplayName("should Throw EntityNotFoundException By User When User Null")
    @Test
    void shouldThrowEntityNotFoundExceptionByUserDtoWhenUserNull(){
        UserDto userDto=new UserDto();
        userDto.setName("testName");
        userDto.setUsername("testUsername");
        userDto.setPassword("testPassword");
        User user=new User();
        user.setName("testName");
        user.setUsername("testUsername");
        user.setPassword("encodedPassword");


        Mockito.when(userRepository.findUserByUsername(userDto.getUsername())).thenReturn(null);

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            userManager.delete(userDto.getUsername());
        });
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(userDto.getUsername() + "  kullanıcı isimli bir kullanıcı bulunamadı", exception.getMessage());
        Mockito.verify(userRepository).findUserByUsername(userDto.getUsername());
    }
    @DisplayName("should Find User By Name And Return User")
    @Test
    void shouldFindUserByNameAndReturnUser(){
        User user=new User();
        user.setName("testName");
        user.setUsername("testUsername");
        user.setPassword("encodedPassword");


        User expectedResult=user;

        Mockito.when(userRepository.findUserByUsername(user.getUsername())).thenReturn(user);

        User result=userManager.findUserByUserName(user.getUsername());

        Assertions.assertEquals(expectedResult,result);
        Mockito.verify(userRepository).findUserByUsername(user.getUsername());
    }

}