package com.authservice.http.model;

import com.authservice.http.error.HttpError;

public class RegisterRequestBody {
    public String name;
    public String email;
    public String password;

    public boolean isValid() {
        if (
            name != null &&
            email != null &&
            password != null
        ) {
            return true;
        }

        throw new HttpError.BadRequest("register request body");
    }
}
