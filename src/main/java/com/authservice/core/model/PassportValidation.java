package com.authservice.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class PassportValidation {
    public Session session;
    public String passport;
    public PassportExpiration expiration;    
}
