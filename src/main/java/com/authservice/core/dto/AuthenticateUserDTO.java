package com.authservice.core.dto;

import lombok.Builder;

@Builder
public class AuthenticateUserDTO {
    public String email;
    public String password;    
}
