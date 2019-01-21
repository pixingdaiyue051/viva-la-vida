package com.tequeno.mycore;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;

public class WSBufferTest {
    public void test() {
        try {
            String str = "Vol，。【】vo 三。，‘一 60131’【】。979 SDCY9，。；‘0K7C21";

            StringReader reader = new StringReader(str);
            WSBufferAnalyzer analyzer = new WSBufferAnalyzer();
            TokenStream ts = analyzer.tokenStream("text", reader);
            CharTermAttribute term = (CharTermAttribute) ts.addAttribute(CharTermAttribute.class);
            ts.reset();
            while (ts.incrementToken()) {
                System.out.println(term.toString());
            }
            ts.end();
            ts.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new WSBufferTest().test();
    }
}
