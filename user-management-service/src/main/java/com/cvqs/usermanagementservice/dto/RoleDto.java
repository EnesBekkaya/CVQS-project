package com.cvqs.usermanagementservice.dto;

import com.cvqs.usermanagementservice.model.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class RoleDto {
    private int id;
    @NotNull(message = "name alanı girilmek zorundadır")
    @NotEmpty(message = "name alanı boş bırakılamaz")
    private String name;
    List<User> users;
}
