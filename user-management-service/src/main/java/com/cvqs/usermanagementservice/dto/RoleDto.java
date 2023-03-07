package com.cvqs.usermanagementservice.dto;

import com.cvqs.usermanagementservice.model.User;
import lombok.Data;
import java.util.List;

@Data
public class RoleDto {
    private int id;

    private String name;

    List<User> users;
}
