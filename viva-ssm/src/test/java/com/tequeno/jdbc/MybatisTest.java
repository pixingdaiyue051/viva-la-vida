package com.tequeno.jdbc;

import org.junit.Test;

import java.io.IOException;

public class MybatisTest {

    @Test
    public void load() {
        MybatisHandler h = new MybatisHandler();
        h.load();
    }

    @Test
    public void loadXml() {
        MybatisHandler h = new MybatisHandler();
        h.loadXml();
    }
}