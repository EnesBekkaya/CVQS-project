package com.cvqs.usermanagementservice.service.concretes;

import com.cvqs.usermanagementservice.dto.RoleDto;
import com.cvqs.usermanagementservice.model.Role;
import com.cvqs.usermanagementservice.repository.RoleRepository;
import com.cvqs.usermanagementservice.service.abstracts.RoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleManager implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    @Override
    public RoleDto save(RoleDto roleDto) {
        Role role=new Role();
        role.setName(roleDto.getName());
        return modelMapper.map(roleRepository.save(role),RoleDto.class);
    }

    @Override
    public Role findRoleByName(String name) {
        Role role= roleRepository.findRoleByName(name);
        if(role==null)
            throw new EntityNotFoundException(name+": Bu isimde kayıtlı bir rol bulunamadı.");
        else
            return role;
    }
}
