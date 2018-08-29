package com.microservice.MicroserviceProject.services;

import com.microservice.MicroserviceProject.daos.MicroDao;
import com.microservice.MicroserviceProject.entities.Micro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MicroServiceImpl implements MicroService {
    @Autowired
    MicroDao microDao;

    @Override
    public List<Micro> getMicro(String email) {

        return microDao.findByFkUser(email);
    }

    @Override
    public Micro addMicro(Micro micro) {
        return microDao.save(micro);
    }

    @Override
    public void deleteMicro(Integer id)
    {
        microDao.deleteById(id);
    }
}
