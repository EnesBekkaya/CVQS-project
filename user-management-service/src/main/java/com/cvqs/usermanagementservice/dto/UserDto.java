package com.cvqs.usermanagementservice.dto;

import com.cvqs.usermanagementservice.model.Role;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.util.List;

@Data
public class UserDto {
    @NotNull(message = "name alanı girilmek zorundadır")
    @NotEmpty(message = "name alanı boş bırakılamaz")
    private String name;
    @NotNull(message = "lastname alanı girilmek zorundadır")
    @NotEmpty(message = "lastname alanı boş bırakılamaz")
    private String lastname;
    @NotNull(message = "username alanı girilmek zorundadır")
    @NotEmpty(message = "username alanı boş bırakılamaz")
    private String username;
    @NotNull(message = "roles değeri null olamaz")
    @Size(min = 1,message = "en az 1 adet rol girilmedilir.")
    @Valid
    private List<Role> roles;
    @NotNull(message = "password alanı girilmek zorundadır")
    @NotEmpty(message = "password alanı boş bırakılamaz")
    private String password;

}
