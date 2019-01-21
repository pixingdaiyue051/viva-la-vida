package com.tequeno.mycore;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class WSBufferTokenizerFactory extends TokenizerFactory {
    public static final String RULE_JAVA = "java";
    public static final String RULE_UNICODE = "unicode";
    private static final Collection RULE_NAMES = Arrays.asList(new String[]{RULE_JAVA, RULE_UNICODE});
    private final String rule;

    public WSBufferTokenizerFactory(Map args) {
        super(args);
        this.rule = get(args, "rule", RULE_NAMES, "java");
        if (!args.isEmpty())
            throw new IllegalArgumentException("Unknown parameters: " + args);
    }

    public Tokenizer create(AttributeFactory factory) {
        String s = this.rule;
        byte byte0 = -1;
        switch (s.hashCode()) {
            case 3254818:
                if (s.equals(RULE_JAVA))
                    byte0 = 0;
                break;
            case -287016227:
                if (s.equals(RULE_UNICODE)) {
                    byte0 = 1;
                }
        }
        switch (byte0) {
            case 0:
                return new WSBufferTokenizer(factory);
            case 1:
                return new WhitespaceTokenizer(factory);
        }
        throw new AssertionError();
    }
}