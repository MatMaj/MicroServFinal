package com.microservice.MicroserviceProject.daos;

import com.microservice.MicroserviceProject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, String> {
    //name strategy
    Optional<User> findUserByEmail(String email);

}
