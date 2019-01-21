package com.tequeno.mycore;

import org.apache.lucene.analysis.Analyzer;

public final class WSBufferAnalyzer extends Analyzer {
    @Override
    protected Analyzer.TokenStreamComponents createComponents(String fieldName) {
        return new Analyzer.TokenStreamComponents(new WSBufferTokenizer());
    }
}
