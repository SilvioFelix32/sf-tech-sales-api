package com.sftech.sales.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class GoneException extends BusinessException {
    public GoneException(String message) {
        super(HttpStatus.GONE, "GONE", message);
    }
}