package com.authservice.core.dto;

import lombok.Builder;

@Builder
public class AuthenticateUserDTO {
    public String email;
    public String password;    

    public boolean validate() {
        return email != null && !email.isEmpty() && password != null && !password.isEmpty();
    }
}
