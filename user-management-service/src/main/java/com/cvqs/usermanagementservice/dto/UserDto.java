package com.cvqs.usermanagementservice.dto;

import com.cvqs.usermanagementservice.model.Role;

import lombok.Data;


import java.util.List;

@Data
public class UserDto {
    private String id;
    private String name;
    private String lastName;
    private String userName;
    private List<Role> roles;

}
