package com.authservice.core.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class EmailConfirmation {
    private String userId;
    private String confirmationCode;
    private boolean isConfirmed;
}
