package com.tequeno.email;

/**
 * @Desription:
 * @Author: hexk
 */
public class EmailException extends RuntimeException {

    public EmailException() {
    }

    public EmailException(String message) {
        super(message);
    }
}
