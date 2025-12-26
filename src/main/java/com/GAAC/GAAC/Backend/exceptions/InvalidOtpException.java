package com.GAAC.GAAC.Backend.exceptions;

public class InvalidOtpException extends RuntimeException{
    public InvalidOtpException(String message) {
        super(message);
    }
}
