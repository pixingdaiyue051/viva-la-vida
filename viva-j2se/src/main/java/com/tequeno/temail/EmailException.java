package com.tequeno.temail;

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
