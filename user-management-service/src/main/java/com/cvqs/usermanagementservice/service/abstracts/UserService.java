package com.cvqs.usermanagementservice.service.abstracts;

import com.cvqs.usermanagementservice.dto.AuthRequest;
import com.cvqs.usermanagementservice.dto.AuthenticationResponse;
import com.cvqs.usermanagementservice.dto.UserDto;

import java.util.List;
/**
 * UserService arayüzü, User nesneleriyle ilgili işlemleri gerçekleştirir
 *
 * @author Enes Bekkaya
 * @since  26.02.2023
 */
public interface UserService {
    /**
     * Tüm kullanıcıları getirir.
     *
     * @return UserDto tipinde bir liste.
     */
    List<UserDto> getAll();
    /**
     * Yeni bir kullanıcı kaydeder.
     *
     * @param userDto Kaydedilecek kullanıcının bilgilerini içeren UserDto nesnesi.
     * @return Kaydedilen kullanıcının UserDto olarak dönüştürülmüş hali.
     */
    UserDto save(UserDto userDto);
    /**
     * Var olan bir kullanıcıyı günceller.
     *
     * @param userDto Güncellenecek kullanıcının bilgilerini içeren UserDto nesnesi.
     * @return Güncellenen kullanıcının UserDto olarak dönüştürülmüş hali.
     */
    UserDto updateUser(UserDto userDto);
    /**
     * Var olan bir kullanıcıyı siler.
     *
     * @param username Silinecek kullanıcının bilgilerini içeren UserDto nesnesi.
     * @return Silinen kullanıcının UserDto olarak dönüştürülmüş hali.
     */
    UserDto delete(String username);

    /**
     * Bu metod kullanıcının kimlik doğrulama isteğini işler ve sonucunda bir AuthenticationResponse döndürür.
     * @param authRequest Kullanıcının kimlik doğrulama isteği.
     * @return Kimlik doğrulama işleminin sonucu olan AuthenticationResponse objesi.
     */
    AuthenticationResponse login(AuthRequest authRequest);

}
