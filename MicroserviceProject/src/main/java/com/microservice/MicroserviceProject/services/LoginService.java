package com.microservice.MicroserviceProject.services;

import com.microservice.MicroserviceProject.entities.User;
import com.microservice.MicroserviceProject.utilities.UserNotInDatabaseException;
import com.microservice.MicroserviceProject.utilities.UserNotLoggedException;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

public interface LoginService {

    Optional<User> getUserFromDb(String email, String pwd) throws UserNotInDatabaseException;

    String createJwt(String email, String name, Date date) throws UnsupportedEncodingException;

    Map<String, Object> verifyJwtAndGetData(HttpServletRequest request) throws  UnsupportedEncodingException, UserNotLoggedException;

}