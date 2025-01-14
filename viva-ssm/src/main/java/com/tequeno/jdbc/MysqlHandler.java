package com.tequeno.jdbc;

import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class MysqlHandler {

    private Connection connection;

    private Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
        if (null == connection) {
            InputStream is = this.getClass().getResourceAsStream("/application.properties");
            Properties p = new Properties();
            p.load(is);
            String driver = p.getProperty("mysql.driver");
            String url = p.getProperty("mysql.url");
            String user = p.getProperty("mysql.user");
            String password = p.getProperty("mysql.password");

            // 1
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setUrl(url);
            dataSource.setUser(user);
            dataSource.setPassword(password);

            connection = dataSource.getConnection();
            // 2
            Class.forName(driver);
            connection = DriverManager.getConnection(url);

            connection.setAutoCommit(false);
        }
        return connection;
    }

}
