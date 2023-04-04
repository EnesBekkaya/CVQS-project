package com.cvqs.usermanagementservice.service.concretes;

import com.cvqs.usermanagementservice.dto.RoleDto;
import com.cvqs.usermanagementservice.exception.EntityNotFoundException;
import com.cvqs.usermanagementservice.model.Role;
import com.cvqs.usermanagementservice.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;


class RoleManagerTest  {
    private RoleManager roleManager;
    private RoleRepository roleRepository;
    private ModelMapper modelMapper;
    @BeforeEach
    public void setUp() {
        roleRepository = Mockito.mock(RoleRepository.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        roleManager = new RoleManager(roleRepository,modelMapper);
    }
    @DisplayName("should Save Role And Return RoleDto")
    @Test
    void shouldSaveRoleAndReturnRoleDto(){
        RoleDto roleDto=new RoleDto();
        roleDto.setName("admin");

        Role role=new Role();
        role.setName("admin");

        RoleDto expectedResult=new RoleDto();
        expectedResult.setName("admin");

        Mockito.when(roleRepository.save(role)).thenReturn(role);
        Mockito.when(modelMapper.map(role, RoleDto.class)).thenReturn(roleDto);

        RoleDto result=roleManager.save(roleDto);

        Assertions.assertEquals(expectedResult,result);

        Mockito.verify(roleRepository).save(role);

    }

    @DisplayName("should Find Role By Name And Return Role")
    @Test
    void shouldFindRoleByNameAndReturnRole(){
        String name="admin";
        Role role=new Role();
        role.setName("admin");

        Role expectedResult=new Role();
        expectedResult.setName("admin");

        Mockito.when(roleRepository.findRoleByName(name)).thenReturn(role);

        Role result=roleManager.findRoleByName(name);

        Assertions.assertEquals(expectedResult,result);
        Mockito.verify(roleRepository).findRoleByName(name);


    }

    @DisplayName("should Throw EntityNotFoundException When RoleName Does Not Exist")
    @Test
    void shouldThrowEntityNotFoundExceptionWhenRoleNameDoesNotExist(){
        String roleName = "admin";
        Mockito.when(roleRepository.findRoleByName(roleName)).thenReturn(null);


        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            roleManager.findRoleByName(roleName);
        });


        String expectedMessage = "admin: Bu isimde kayıtlı bir rol bulunamadı.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);

        Mockito.verify(roleRepository).findRoleByName(roleName);
        Mockito.verifyNoMoreInteractions(roleRepository);

    }
}