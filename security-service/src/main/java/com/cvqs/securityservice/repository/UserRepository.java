package com.cvqs.securityservice.repository;

import com.cvqs.securityservice.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,String> {
    User findByUsername(String username);
}
