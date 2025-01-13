package com.tequeno.jdbc;

import com.tequeno.dto.hero.HeroDetailDto;
import lombok.extern.slf4j.Slf4j;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SqliteHandler {

    private final String url = "jdbc:sqlite:/plugin/sqlite-x64/test.db";

    private SQLiteDataSource dataSource;

    private Connection connection;

    private SQLiteDataSource getDataSource() {
        if (null == dataSource) {
            dataSource = new SQLiteDataSource();
            dataSource.setUrl(url);
        }
        return dataSource;
    }


    private Connection getConnection() throws SQLException {
        if (null == connection) {
            // 1
//            connection = DriverManager.getConnection(url);
            // 2
            connection = getDataSource().getConnection();
            connection.setAutoCommit(false);
        }
        return connection;
    }

    public void statement() {
        try {
            Connection conn = getConnection();
            Statement statement = conn.createStatement();

            String sql = "select * from hero where type = %d limit %d";
            ResultSet resultSet1 = statement.executeQuery(String.format(sql, 3, 30));
            List<HeroDetailDto> list1 = this.set2detail(resultSet1);
            list1.forEach(System.out::println);

            boolean executed = statement.execute(String.format(sql, 2, 50));
            if (executed) {
                ResultSet resultSet2 = statement.getResultSet();
                List<HeroDetailDto> list2 = this.set2detail(resultSet2);
                list2.forEach(System.out::println);
            }

            int executedUpdate = statement.executeUpdate("insert into hero(id,name,type) values(342,'3官方违法',4),(5334,'v订购合同',4),(235435,'在个人感受个儿矮',4),(65633,'部分是各个t',4)");
            log.info("inserted[{}]", executedUpdate);

            System.out.println(1 / 0);
            int ok = statement.executeUpdate("delete from hero where id > 108");
            log.info("deleted[{}]", ok);

        } catch (Exception e) {
            log.error("", e);
        }
    }

    public void preStatement() {
        try {
            Connection conn = getConnection();
            String sql = "select * from hero where type = ? limit ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, 2);
            statement.setInt(2, 30);

            ResultSet resultSet1 = statement.executeQuery();
            List<HeroDetailDto> list1 = this.set2detail(resultSet1);
            list1.forEach(System.out::println);


        } catch (Exception e) {
            log.error("", e);
        }
    }

    private List<HeroDetailDto> set2detail(ResultSet resultSet) throws SQLException {
        List<HeroDetailDto> list = new ArrayList<>();
        while (resultSet.next()) {
            HeroDetailDto dto = new HeroDetailDto();
            dto.setId(resultSet.getLong(1));
            dto.setName(resultSet.getString(2));
            dto.setNickName(resultSet.getString(3));
            dto.setConstellation(resultSet.getString(4));
            dto.setShortConstellation(resultSet.getString(5));
            dto.setWeaponry(resultSet.getString(6));
            dto.setPosition(resultSet.getString(7));
            dto.setFinale(resultSet.getString(8));
            dto.setType(resultSet.getInt(9));
            dto.setRank(resultSet.getInt(10));
            dto.setFullRank(resultSet.getInt(11));
            list.add(dto);
        }
        return list;
    }
}
