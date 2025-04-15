package com.sftech.sales.presentation.exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends BusinessException {
    public InternalServerErrorException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", message);
    }
}