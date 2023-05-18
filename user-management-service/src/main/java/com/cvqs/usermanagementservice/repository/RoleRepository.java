package com.cvqs.usermanagementservice.repository;

import com.cvqs.usermanagementservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * RoleRepository is an interface used for performing database operations on Role objects.
 *
 * @author Enes Bekkaya
 * @since  26.02.2023
 */
public interface RoleRepository extends JpaRepository<Role,Integer> {
    /**
     * Finds a role from the database based on the name.
     * @param name The name of the role.
     * @return The role with the given name.
     */
        Role findRoleByName(String name);


}
