package com.microservice.MicroserviceProject.controllers;

import com.microservice.MicroserviceProject.entities.Micro;
import com.microservice.MicroserviceProject.entities.User;
import com.microservice.MicroserviceProject.services.LoginService;
import com.microservice.MicroserviceProject.services.MicroService;
import com.microservice.MicroserviceProject.utilities.JsonResponseBody;
import com.microservice.MicroserviceProject.utilities.ToDoValidator;
import com.microservice.MicroserviceProject.utilities.UserNotInDatabaseException;
import com.microservice.MicroserviceProject.utilities.UserNotLoggedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    LoginService loginService;
    @Autowired
    MicroService microService;

    @RequestMapping("/hello")
    public String sayHello() {
        return "hello";
    }

    @RequestMapping("/userInOutput")
    public User giveMeAUser() {
        return new User("roger@wp.pl", "Roger Federrer", "passwd");
    }

    @RequestMapping("/microInput")
    public String microInput(Micro micro) {
        return "Micro description: " + micro.getDescription() + " micro priority: " + micro.getPriority();
    }

    @RequestMapping("/microInput2")
    public String microInput2(@Valid Micro micro) {
        return "Micro description: " + micro.getDescription() + " micro priority: " + micro.getPriority();
    }

    @RequestMapping("/microInput3")
    public String microInput3(@Valid Micro micro, BindingResult result) {
        if (result.hasErrors()) {
            return "Error error format nieprawidłowy" + result.toString();
        }
        return "Micro description: " + micro.getDescription() + " micro priority: " + micro.getPriority();
    }

    @RequestMapping("/microInput4")
    public String microInput4(Micro micro, BindingResult result) {
        ToDoValidator validator = new ToDoValidator();
        validator.validate(micro, result);
        if (result.hasErrors()) {
            return "Error error format nieprawidłowy" + result.toString();
        }
        return "Micro description: " + micro.getDescription() + " micro priority: " + micro.getPriority();
    }

    @RequestMapping("/microInput5")
    public String microInput5(@Valid Micro micro, BindingResult result) {
        ToDoValidator validator = new ToDoValidator();
        validator.validate(micro, result);
        if (result.hasErrors()) {
            return "Error error format nieprawidłowy" + result.toString();
        }
        return "Micro description: " + micro.getDescription() + " micro priority: " + micro.getPriority();
    }

    /*@RequestMapping("/exampleUrl")
    public ResponseEntity<JsonResponseBody> returnMyStandardResponse(){
        return ResponseEntity.status(HttpStatus.OK).header("jwt", jwt).body( new JsonResponseBody());
    }*/

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<JsonResponseBody> login(@RequestParam(value = "email") String email, @RequestParam(value = "password") String pwd) {
        try {
            Optional<User> userr = loginService.getUserFromDb(email, pwd);
            User user = userr.get();
            String jwt = loginService.createJwt(email, user.getName(), new Date());
            return ResponseEntity.status(HttpStatus.OK).header("jwt", jwt).body(new JsonResponseBody(HttpStatus.OK.value(), "Succes User Login"));
        } catch (UserNotInDatabaseException e1) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "Forbidden" + e1.toString()));
        } catch (UnsupportedEncodingException e2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "Bad request" + e2.toString()));
        }
    }

    @RequestMapping(value = "/showMicro")
    public ResponseEntity<JsonResponseBody> showMicros(HttpServletRequest request) {
        try {
            Map<String, Object> userData = loginService.verifyJwtAndGetData(request);
            return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseBody(HttpStatus.OK.value(), microService.getMicro((String) userData.get("email"))));
        } catch (UnsupportedEncodingException e1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "Bad Request" + e1.toString()));
        } catch (UserNotLoggedException e2) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "Bad Request" + e2.toString()));
        } catch (ExpiredJwtException e3) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new JsonResponseBody(HttpStatus.GATEWAY_TIMEOUT.value(), "Bad Request" + e3.toString()));
        }
    }

    @RequestMapping(value = "/newMicro", method = RequestMethod.POST)
    public ResponseEntity<JsonResponseBody> newMicro(HttpServletRequest request, @Valid Micro micro, BindingResult result) {
        ToDoValidator validator = new ToDoValidator();
        validator.validate(micro, result);

        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "Data not valid" + result.toString()));
        }
        try {
            loginService.verifyJwtAndGetData(request);
            return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseBody(HttpStatus.OK.value(), microService.addMicro(micro)));
        } catch (UnsupportedEncodingException e1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "Bad Request" + e1.toString()));
        } catch (UserNotLoggedException e2) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "Bad Request" + e2.toString()));
        } catch (ExpiredJwtException e3) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new JsonResponseBody(HttpStatus.GATEWAY_TIMEOUT.value(), "Bad Request" + e3.toString()));
        }
    }

    @RequestMapping("/deleteMicro/{id}")
    public ResponseEntity<JsonResponseBody> deleteMicro(HttpServletRequest request, @PathVariable(name="id") Integer microId) {
        try{
            loginService.verifyJwtAndGetData(request);
            microService.deleteMicro(microId);
            return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseBody(HttpStatus.OK.value(), "Micro correctly delete"));
        } catch (UnsupportedEncodingException e1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "Bad Request" + e1.toString()));
        } catch (UserNotLoggedException e2) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "Bad Request" + e2.toString()));
        } catch (ExpiredJwtException e3) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new JsonResponseBody(HttpStatus.GATEWAY_TIMEOUT.value(), "Bad Request" + e3.toString()));
        }
    }
}