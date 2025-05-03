package com.authservice.http.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.authservice.core.io.CoreError;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class HttpError extends CoreError {
    public HttpError(String level, String message) {
        super("Http", level, message);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public static class BadRequest extends HttpError {
        public BadRequest(String level) {
            super(level, "bad-request");
        }
    }
}
