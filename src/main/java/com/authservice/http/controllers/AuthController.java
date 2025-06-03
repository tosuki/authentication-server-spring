package com.authservice.http.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authservice.core.dto.RegisterUserDTO;
import com.authservice.core.usecase.AuthUsecase;
import com.authservice.http.dto.AuthHttpResponseDTO;
import com.authservice.http.dto.RegisterUserResponseDTO;
import com.authservice.http.model.AuthenticateRequestBody;
import com.authservice.http.model.RegisterRequestBody;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/ok")
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, Object> isOk() {
        return Map.of("ok", true);
    }    

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody RegisterUserDTO data) {
        if (!data.validate()) {
            return RegisterUserResponseDTO.lacking();
        }

        return RegisterUserResponseDTO.authorize("your token !!");
    }
}
