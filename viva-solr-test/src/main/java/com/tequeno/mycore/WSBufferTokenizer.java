package com.tequeno.mycore;

import org.apache.lucene.analysis.util.CharTokenizer;
import org.apache.lucene.util.AttributeFactory;

import java.util.regex.Pattern;

public class WSBufferTokenizer extends CharTokenizer {
    private final String REG = "[\\pP|\\sS]";

    public WSBufferTokenizer() {
    }

    public WSBufferTokenizer(AttributeFactory factory) {
        super(factory);
    }

    protected boolean isTokenChar(int c) {
        String token = String.valueOf(Character.toChars(c));
        return !Pattern.matches(REG, token);
    }
}