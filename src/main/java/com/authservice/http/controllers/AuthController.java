package com.authservice.http.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authservice.core.usecase.AuthUsecase;
import com.authservice.http.model.AuthenticateRequestBody;
import com.authservice.http.model.RegisterRequestBody;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthUsecase authUsecase;

    @GetMapping("/ok")
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, Object> isOk() {
        return Map.of("ok", true);
    }    

    @PostMapping("/authenticate")
    public Map<String, Object> authenticate(@RequestBody AuthenticateRequestBody authenticateRequestBody) {
        System.out.printf("Email: %s\n", authenticateRequestBody.email);
        // to do   
        return Map.of("ok", true);
    }   

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody RegisterRequestBody registerRequestBody) {
        if (!registerRequestBody.isValid()) {
            return Map.of(
                "ok", false,
                "error", "Bad Request"
            );
        }
        //authUsecase.register(registerRequestBody.name, registerRequestBody.email, registerRequestBody.password);
        return Map.of("ok", true);
    }
    
}