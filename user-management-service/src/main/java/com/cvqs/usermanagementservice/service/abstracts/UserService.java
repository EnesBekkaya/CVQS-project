package com.cvqs.usermanagementservice.service.abstracts;

import com.cvqs.usermanagementservice.dto.UserDto;
import com.cvqs.usermanagementservice.model.User;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();
    UserDto save(UserDto userDto);
    UserDto updateUser(UserDto userDto);
    UserDto delete(UserDto userDto);
    User findUserByUserName(String userName);

}
