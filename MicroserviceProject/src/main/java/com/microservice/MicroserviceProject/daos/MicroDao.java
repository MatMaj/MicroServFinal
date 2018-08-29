package com.microservice.MicroserviceProject.daos;

import com.microservice.MicroserviceProject.entities.Micro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MicroDao extends JpaRepository<Micro, Integer> {
    //name strategy
    List<Micro> findByFkUser(String email);

}