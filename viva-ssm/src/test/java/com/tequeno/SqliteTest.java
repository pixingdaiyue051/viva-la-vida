package com.tequeno;

import com.tequeno.jdbc.SqliteHandler;
import org.junit.Test;

public class SqliteTest {

    @Test
    public void statement() {
        SqliteHandler handler = new SqliteHandler();
        handler.statement();
    }

    @Test
    public void preStatement() {
        SqliteHandler handler = new SqliteHandler();
        handler.preStatement();
    }
}