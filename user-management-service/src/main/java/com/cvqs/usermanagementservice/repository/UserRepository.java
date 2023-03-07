package com.cvqs.usermanagementservice.repository;

import com.cvqs.usermanagementservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    User findUserByUserName(String userName);
}
