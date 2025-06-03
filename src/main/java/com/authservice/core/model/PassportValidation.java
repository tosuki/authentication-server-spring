package com.authservice.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class PassportValidation {
    public Session session;
    public String passport;
    public PassportExpiration expiration;    
}
