package com.cvqs.securityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {

    private String name;

    private String lastName;

    private String username;

    private String password;

    private List<Role> roles;
}
