package com.microservice.MicroserviceProject.utilities;

import com.microservice.MicroserviceProject.entities.Micro;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//SPRING VALIDATOR
public class ToDoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Micro.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Micro micro = (Micro) obj;

        String priority = micro.getPriority();

        if(!"high".equals(priority) && !"low".equals(priority)){
            errors.rejectValue("priority", "Priority must be 'high' or 'low'!");
        }

    }
}
