package com.cvqs.usermanagementservice.dto;

import com.cvqs.usermanagementservice.model.Role;

import lombok.Data;


import java.util.List;

@Data
public class UserDto {
    private String name;
    private String lastname;
    private String username;
    private List<Role> roles;
    private String password;

}
