package com.cvqs.usermanagementservice.service.concretes;

import com.cvqs.usermanagementservice.dto.RoleDto;
import com.cvqs.usermanagementservice.exception.EntityNotFoundException;
import com.cvqs.usermanagementservice.model.Role;
import com.cvqs.usermanagementservice.repository.RoleRepository;
import com.cvqs.usermanagementservice.service.abstracts.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 *  The RoleManager class is derived from the RoleService interface and
 * manages role operations. This class uses SectionRepository objects for database operations.
 *
 *  @author Enes Bekkaya
 *  @since  26.02.2023
 */
@Service
@RequiredArgsConstructor
public class RoleManager implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    /**
     * Takes a RoleDto object and saves it to the database.
     * @param roleDto the RoleDto object to be saved
     * @return the saved RoleDto object
     */
    @Override
    public RoleDto save(RoleDto roleDto) {
        Role role=new Role();
        role.setName(roleDto.getName());
        return modelMapper.map(roleRepository.save(role),RoleDto.class);
    }

    /**
     * Finds a Role object based on a role name.
     * @param name the name of the role to search for
     * @return the found Role object
     * @throws EntityNotFoundException if a role with the given name cannot be found
     */
    @Override
    public Role findRoleByName(String name) {
        Role role= roleRepository.findRoleByName(name);
        if(role==null) {
            throw new EntityNotFoundException("No role found with the name: "+name);
        }
        else
            return role;
    }

}
