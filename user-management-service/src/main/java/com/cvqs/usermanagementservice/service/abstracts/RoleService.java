package com.cvqs.usermanagementservice.service.abstracts;

import com.cvqs.usermanagementservice.dto.RoleDto;
import com.cvqs.usermanagementservice.model.Role;

public interface RoleService {
     RoleDto save(RoleDto roleDto);
     Role findRoleByName(String name);

}
