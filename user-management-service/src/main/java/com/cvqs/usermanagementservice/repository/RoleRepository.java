package com.cvqs.usermanagementservice.repository;

import com.cvqs.usermanagementservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findRoleByName(String name);


}
