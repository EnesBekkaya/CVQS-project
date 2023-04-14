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

/**
 *  RoleManager sınıfı, RoleService arayüzünden türetilmiştir ve
 *  role işlemlerini yönetir. Bu sınıf, veritabanı işlemleri için SectionRepository nesnelerini kullanmaktadır.
 *
 *  @author Enes Bekkaya
 *  @since  26.02.2023
 */
@Service
@RequiredArgsConstructor
public class RoleManager implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private static final Logger LOGGER= LoggerFactory.getLogger(RoleManager.class);

    /**
     * RoleDto nesnesini alır ve veritabanına kaydeder.
     * @param roleDto kaydedilecek RoleDto nesnesi
     * @return kaydedilen RoleDto nesnesi
     */
    @Override
    public RoleDto save(RoleDto roleDto) {
        Role role=new Role();
        role.setName(roleDto.getName());
        return modelMapper.map(roleRepository.save(role),RoleDto.class);
    }

    /**
     * Bir rol ismine göre Role nesnesi bulur.
     * @param name aranan rolün ismi
     * @return bulunan Role nesnesi
     * @throws EntityNotFoundException aranan isimde bir rol bulunamazsa fırlatılır.

     */
    @Override
    public Role findRoleByName(String name) {
        Role role= roleRepository.findRoleByName(name);
        if(role==null) {
            LOGGER.warn("işlem başarısız!!{} isminde kayıtlı bir rol bulunamadı. ",name);
            throw new EntityNotFoundException(name + ": Bu isimde kayıtlı bir rol bulunamadı.");
        }
        else
            return role;
    }

}
