package com.authservice.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RegisterUserDTO {
    public String name;
    public String email;
    public String password;

    public boolean validate() {
        return name != null && !name.isEmpty() &&
               email != null && !email.isEmpty() &&
               password != null && !password.isEmpty();
    }
}
