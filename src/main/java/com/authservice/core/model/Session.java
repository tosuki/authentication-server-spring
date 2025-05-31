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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Session{")
          .append("id='").append(id).append('\'')
          .append(", name='").append(name).append('\'')
          .append(", email='").append(email).append('\'')
          .append(", timestamp=").append(timestamp)
          .append(", expiresAt=").append(expiresAt)
          .append(", issuedAt=").append(issuedAt)
          .append('}');
        
        return sb.toString();
    }
}
