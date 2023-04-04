package com.cvqs.usermanagementservice.service.concretes;

import com.cvqs.usermanagementservice.dto.RoleDto;
import com.cvqs.usermanagementservice.exception.EntityNotFoundException;
import com.cvqs.usermanagementservice.model.Role;
import com.cvqs.usermanagementservice.repository.RoleRepository;
import com.cvqs.usermanagementservice.service.abstracts.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleManager implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private static final Logger LOGGER= LoggerFactory.getLogger(RoleManager.class);

    @Override
    public RoleDto save(RoleDto roleDto) {
        Role role=new Role();
        role.setName(roleDto.getName());
        return modelMapper.map(roleRepository.save(role),RoleDto.class);
    }

    @Override
    public Role findRoleByName(String name) {
        Role role= roleRepository.findRoleByName(name);
        if(role==null) {
            LOGGER.warn("Parameter role is null in findRoleByName() method.");
            throw new EntityNotFoundException(name + ": Bu isimde kayıtlı bir rol bulunamadı.");
        }
        else
            return role;
    }

}
