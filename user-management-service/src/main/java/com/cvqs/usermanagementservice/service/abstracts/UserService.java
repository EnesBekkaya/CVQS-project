package com.cvqs.usermanagementservice.service.abstracts;

import com.cvqs.usermanagementservice.dto.AuthRequest;
import com.cvqs.usermanagementservice.dto.UserDto;
import com.cvqs.usermanagementservice.dto.UserResponseDto;

import java.util.List;
/**
 * The UserService interface performs operations related to User objects.
 *
 * @author Enes Bekkaya
 * @since  26.02.2023
 */
public interface UserService {
    /**
     * Retrieves all users.
     *
     * @return A list of UserDto objects.
     */
    List<UserResponseDto> getAll();
    /**
     * Saves a new user.
     * @param userDto UserDto object containing the information of the user to be saved.
     * @return The saved user as a UserDto object.
     */
    UserResponseDto save(UserDto userDto);
    /**
     * Updates an existing user.
     *
     * @param userDto UserDto object containing the updated user information.
     * @return Updated user information as a UserDto object.
     */
    UserResponseDto updateUser(UserDto userDto);
    /**
     *  Deletes an existing user.
     *
     * @param username UserDto object containing information about the user to be deleted.
     * @return UserDto object of the deleted user.
     */
    UserResponseDto delete(String username);

    /**
     *  Processes the user's authentication request and returns an AuthenticationResponse object as a result.
     *  @param authRequest The user's authentication request.
     * @return The AuthenticationResponse object representing the result of the authentication process.
     */
    String login(AuthRequest authRequest);

}
