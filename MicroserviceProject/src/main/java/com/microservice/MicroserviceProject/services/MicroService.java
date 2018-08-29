package com.microservice.MicroserviceProject.services;

import com.microservice.MicroserviceProject.entities.Micro;

import java.util.List;

public interface MicroService {

    List<Micro> getMicro(String email);

    Micro addMicro(Micro micro);

    void deleteMicro(Integer id);
}
