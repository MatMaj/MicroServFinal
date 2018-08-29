package com.microservice.MicroserviceProject;

import com.microservice.MicroserviceProject.daos.MicroDao;
import com.microservice.MicroserviceProject.daos.UserDao;
import com.microservice.MicroserviceProject.entities.Micro;
import com.microservice.MicroserviceProject.entities.User;
import com.microservice.MicroserviceProject.utilities.EncryptionUtils;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;


@SpringBootApplication
public class MicroserviceProjectApplication implements CommandLineRunner {

    @Autowired
    UserDao userDao;

    @Autowired
    MicroDao microDao;

    @Autowired
    EncryptionUtils encryptionUtils;

    private static final Logger log = (Logger) LoggerFactory.getLogger(MicroserviceProjectApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceProjectApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception{
        log.info("lets fill H2 in-memory database");

        String encryptedPwd;
        encryptedPwd = encryptionUtils.encrypt("password");
        userDao.save(new User("matmaj81@wp.pl", "Mateusz", encryptedPwd));

        encryptedPwd = encryptionUtils.encrypt("mojpassword");
        userDao.save(new User("matmaj81@gmail.com", "Major", encryptedPwd));

        encryptedPwd = encryptionUtils.encrypt("passwordmoj");
        userDao.save(new User("matmaj81@onet.pl", "MatMaj", encryptedPwd));

        microDao.save( new Micro(1, "Nauka microservicow", new Date(), "high", "matmaj81@wp.pl"));
        microDao.save( new Micro(2, "Microservice spring", new Date(), "low", "matmaj81@gmail.com"));
        microDao.save( new Micro(null, "Microservice dao", new Date(), "high", "matmaj81@wp.p"));

        log.info("we've finished to fill our database");
    }
}

