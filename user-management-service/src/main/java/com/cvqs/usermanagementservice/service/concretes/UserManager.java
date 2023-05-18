package com.cvqs.usermanagementservice.service.concretes;

import com.cvqs.usermanagementservice.dto.AuthRequest;
import com.cvqs.usermanagementservice.dto.UserDto;
import com.cvqs.usermanagementservice.dto.UserResponseDto;
import com.cvqs.usermanagementservice.exception.EntityNotFoundException;
import com.cvqs.usermanagementservice.exception.ServerRequestException;
import com.cvqs.usermanagementservice.model.Role;
import com.cvqs.usermanagementservice.model.User;
import com.cvqs.usermanagementservice.repository.UserRepository;
import com.cvqs.usermanagementservice.service.abstracts.RoleService;
import com.cvqs.usermanagementservice.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 *  The UserManager class is derived from the UserService interface and manages user operations.
 *  This class uses UserRepository objects for database operations.
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


    /**
     * Gets all users from the database and returns them as a list of UserDto objects.
     * @return List of UserDto objects for all users in the database.
     */
    @Override
    public List<UserResponseDto> getAll() {
        List <User> users=userRepository.findAll();
        List<UserResponseDto> userResponseDtos=users.stream().map(user1 -> modelMapper.map(user1,UserResponseDto.class)).collect(Collectors.toList());
        return userResponseDtos;
    }

    /**
     * Saves the UserDto object to the database after checking if the username already exists in the database.
     * Otherwise, the UserDto object is converted to a User object, the roles are retrieved from the RoleService and added to the user object,
     * the password is encoded using the passwordEncoder, and the user object is saved to the UserRepository.
     * Finally, the saved User object is converted to a UserDto object and returned.
     * @param userDto the UserDto object to be saved
     * @return the saved UserDto object
     * @throws ResponseStatusException if the username already exists in the database
     */
    @Override
    public UserResponseDto save(UserDto userDto) {
        if(userRepository.findUserByUsername(userDto.getUsername())!=null){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"This username is already registered.");
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
        return modelMapper.map(userRepository.save(newUser), UserResponseDto.class);
    }

    /**
     * Takes a UserDto object and updates the user's information in the database if a user with this username exists,
     * returns the updated UserDto object. If the user does not exist, throws an EntityNotFoundException.
     * @param userDto UserDto object to be updated
     * @return Updated UserDto object
     * @throws EntityNotFoundException if there is no user with the given username
     */
    @Override
    public UserResponseDto updateUser(UserDto userDto ) {
        User user=userRepository.findUserByUsername(userDto.getUsername());
        List<Role> roles=new ArrayList<>();
        if(user==null) {
            throw new EntityNotFoundException("No user found with the username :"+userDto.getUsername());
        }
        userDto.getRoles().forEach(role -> {
            Role savedRole=roleService.findRoleByName(role.getName());

            roles.add(savedRole);
        });
        user.setName(userDto.getName());
        user.setLastname(userDto.getLastname());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(roles);
        return modelMapper.map(userRepository.save(user),UserResponseDto.class);
    }

    /**
     * Takes a UserDto object and if there is a user in the database with this username,
     * updates the deleted property of the user to true and returns the updated UserDto object.
     * If there is no user with this username, throws an EntityNotFoundException.
     * @param username username of the user to be deleted
     * @return deleted UserDto object
     * @throws EntityNotFoundException if there is no user with the given username
     */
    @Override
    public UserResponseDto delete(String username) {
        User user=userRepository.findUserByUsername(username);
        if(user==null) {
            throw new EntityNotFoundException("No user found with the username :"+username);
        }
        user.setDeleted(true);
        return modelMapper.map(userRepository.save(user),UserResponseDto.class);
    }

    /**
     * Performs authentication with the user's credentials by making a REST request to the authentication server and verifying the user's credentials.
     * @param authRequest authentication request object containing user's credentials
     * @return AuthenticationResponse object containing the user's authentication information
     * @throws ServerRequestException if there is an error making a REST request to the server
     */
    @Override
    public String login(AuthRequest authRequest) {
        try {
            String url = "http://host.docker.internal:9092/auth/authenticate";
            String response = restTemplate.postForObject(url, authRequest, String.class);
            if(!response.equals("failed")) {
                return response;
            }else {
                throw new EntityNotFoundException("Incorrect username or password");
            }
        }catch (RestClientException e){

            throw new ServerRequestException("Failed to make request to the server");
        }
    }


}
