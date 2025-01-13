package com.tequeno;

import com.tequeno.jdbc.SqliteHandler;
import org.junit.Test;

public class SqliteTest {

    @Test
    public void run() {
        SqliteHandler handler = new SqliteHandler();
        handler.statement();
    }
}