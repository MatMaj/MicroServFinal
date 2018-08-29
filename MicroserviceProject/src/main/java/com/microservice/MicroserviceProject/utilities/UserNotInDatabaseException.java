package com.microservice.MicroserviceProject.utilities;

public class UserNotInDatabaseException extends Exception {
    public UserNotInDatabaseException(String message){
        super(message);
    }
}
