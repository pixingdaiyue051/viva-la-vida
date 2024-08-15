package com.tequeno.annos;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptionAnno {

    Type value() default Type.ENCRYPT;

    enum Type {
        ENCRYPT,
        DECRYPT,
        HANDSHAKE;

        Type() {
        }
    }
}
