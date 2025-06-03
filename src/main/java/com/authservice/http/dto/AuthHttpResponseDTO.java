package com.authservice.http.dto;

import lombok.Builder;

@Builder
public class AuthHttpResponseDTO <T> {
    public boolean ok;
    public String message;
    public T data;
}
