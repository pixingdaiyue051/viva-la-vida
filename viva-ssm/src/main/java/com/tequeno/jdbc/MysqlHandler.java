package com.tequeno.jdbc;

import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.extern.slf4j.Slf4j;

import java.awt.dnd.DropTarget;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class MysqlHandler {

    private final String url = "";
    private final String user = "";
    private final String password = "";

    private MysqlDataSource dataSource;

    private Connection connection;

    private MysqlDataSource getDataSource() {
        if (null == dataSource) {
            dataSource = new MysqlDataSource();
            dataSource.setUrl(url);
            dataSource.setUser(user);
            dataSource.setPassword(password);
        }

        return dataSource;
    }

    private Connection getConnection() throws SQLException {
        if (null == connection) {
            // 1
//            connection = DriverManager.getConnection(url, user, password);
            // 2
            connection = getDataSource().getConnection();
            connection.setAutoCommit(false);
        }
        return connection;
    }

}
