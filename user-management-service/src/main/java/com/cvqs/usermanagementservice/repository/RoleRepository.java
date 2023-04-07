package com.cvqs.usermanagementservice.repository;

import com.cvqs.usermanagementservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * RoleRepository, Role nesnelerinin veritabanı işlemlerini yapmak için kullanılan arayüz.
 *
 * @author Enes Bekkaya
 * @since  26.02.2023
 */
public interface RoleRepository extends JpaRepository<Role,Integer> {
    /**
     * İsim alanına göre bir rolü veritabanından bulur.
     * @param name Rolün ismi.
     * @return Verilen isim alanına sahip olan rolü döndürür.
     */
        Role findRoleByName(String name);


}
