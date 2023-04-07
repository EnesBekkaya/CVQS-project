package com.cvqs.usermanagementservice.repository;

import com.cvqs.usermanagementservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * UserRepository, User nesnelerinin veritabanı işlemlerini yapmak için kullanılan arayüz.
 *
 * @author Enes Bekkaya
 * @since  26.02.2023
 */
public interface UserRepository extends JpaRepository<User,String> {
    /**
     * Kullanıcı adına göre kullanıcıyı veritabanından bulur.
     * @param userName Kullanıcı adı.
     * @return Verilen kullanıcı adına sahip olan kullanıcıyı döndürür
     */
    User findUserByUsername(String userName);
}
