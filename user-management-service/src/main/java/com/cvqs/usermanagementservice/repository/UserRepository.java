package com.cvqs.usermanagementservice.repository;

import com.cvqs.usermanagementservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * UserRepository is an interface used for performing database operations on User objects.
 *
 * @author Enes Bekkaya
 * @since  26.02.2023
 */
public interface UserRepository extends JpaRepository<User,String> {
    /**
     * Finds the user from the database by the given username.
     * @param userName Username of the user.
     * @return The user with the given username.
     */
    User findUserByUsername(String userName);
}
