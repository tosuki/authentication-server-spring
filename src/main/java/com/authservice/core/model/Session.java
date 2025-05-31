package com.authservice.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Session {
    public String id;
    public String name;
    public String email;
    public long timestamp;
    public long expiresAt;
    public long issuedAt;
}
