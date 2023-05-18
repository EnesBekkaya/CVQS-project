package com.cvqs.usermanagementservice.dto;

import com.cvqs.usermanagementservice.model.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
@Data
public class UserResponseDto {
    private String name;
    private String lastname;
    private String username;
    private List<Role> roles;
}
