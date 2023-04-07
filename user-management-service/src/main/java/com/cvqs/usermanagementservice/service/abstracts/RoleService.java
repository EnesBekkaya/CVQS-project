package com.cvqs.usermanagementservice.service.abstracts;

import com.cvqs.usermanagementservice.dto.RoleDto;
import com.cvqs.usermanagementservice.model.Role;
/**
 * RoleService arayüzü, Role nesneleriyle ilgili işlemleri gerçekleştirir
 *
 * @author Enes Bekkaya
 * @since  26.02.2023
 */
public interface RoleService {
     /**
      * Verilen RoleDto nesnesini veritabanına kaydeder ve kaydedilen Role nesnesini RoleDto nesnesine dönüştürerek geri döndürür.
      * @param roleDto kaydedilecek RoleDto nesnesi
      * @return kaydedilen Role nesnesini RoleDto nesnesine dönüştürerek geri döndürür.
      */
     RoleDto save(RoleDto roleDto);

     /**
      * Verilen role adına sahip Role nesnesini veritabanından arar ve bulduğu takdirde Role nesnesini döndürür.
      * Eğer veritabanında aranan role adına sahip bir Role nesnesi yoksa EntityNotFoundException fırlatır.
      * @param name aranacak role adı
      * @return bulunan Role nesnesi
      */
     Role findRoleByName(String name);

}
