package com.museri.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.CONFLICT)
public class UniqueEmailException extends RuntimeException {

    public UniqueEmailException(String message) {
        super(message);
    }

}
