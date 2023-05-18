package com.cvqs.usermanagementservice.service.abstracts;

import com.cvqs.usermanagementservice.dto.RoleDto;
import com.cvqs.usermanagementservice.model.Role;
/**
 * RoleService is an interface used for performing database operations on Role objects.
 *
 * @author Enes Bekkaya
 * @since  26.02.2023
 */
public interface RoleService {
     /**
      * Saves the given RoleDto object to the database, and returns the saved Role object converted to a RoleDto object.
      * @param roleDto the RoleDto object to be saved
      * @return the saved Role object converted to a RoleDto object
      */
     RoleDto save(RoleDto roleDto);

     /**
      * Searches the Role object in the database with the given role name and returns the found Role object.
      * If there is no Role object in the database with the searched role name, it throws an EntityNotFoundException.
      * @param name the role name to search
      * @return the found Role object
      */
     Role findRoleByName(String name);

}
