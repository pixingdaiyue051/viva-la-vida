package com.tequeno;

import com.tequeno.jdbc.SqliteHandler;
import org.junit.Test;

public class SqliteTest {

    @Test
    public void query() {
        SqliteHandler handler = new SqliteHandler();
        handler.query();
    }

    @Test
    public void update() {
        SqliteHandler handler = new SqliteHandler();
        handler.update();
    }
}