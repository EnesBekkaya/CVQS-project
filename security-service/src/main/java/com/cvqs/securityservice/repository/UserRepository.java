package com.cvqs.securityservice.repository;

import com.cvqs.securityservice.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepository is the interface used to perform database operations of User objects.
 */
public interface UserRepository extends JpaRepository<User,String> {
    User findByUsername(String username);
}
