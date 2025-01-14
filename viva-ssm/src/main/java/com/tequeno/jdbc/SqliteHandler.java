package com.tequeno.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.tequeno.dto.hero.HeroDetailDto;
import lombok.extern.slf4j.Slf4j;
import org.sqlite.SQLiteDataSource;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
public class SqliteHandler {

    private String driver;
    private String url;

    private SQLiteDataSource sqLiteDataSource;
    private Connection connection;

    private DruidDataSource druidDataSource;
    private DruidPooledConnection druidPooledConnection;

    private void initDataSource() {
        if (null != sqLiteDataSource) {
            return;
        }
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("application.properties")) {
            Properties p = new Properties();
            p.load(is);
            driver = p.getProperty("sqlite.driver");
            url = p.getProperty("sqlite.url");

            sqLiteDataSource = new SQLiteDataSource();
            sqLiteDataSource.setUrl(url);
        } catch (Exception e) {
            log.error("加载配置文件失败", e);
        }
    }

    private void initConnection() throws SQLException, ClassNotFoundException {
        if (null != connection) {
            return;
        }
        initDataSource();
        // 1
        Class.forName(driver);
        connection = DriverManager.getConnection(url);
        // 2
        connection = sqLiteDataSource.getConnection();

        connection.setAutoCommit(false);
    }

    private void initDruidDataSource() {
        if (null != druidDataSource) {
            return;
        }
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("application.properties")) {
            Properties p = new Properties();
            p.load(is);
            driver = p.getProperty("sqlite.driver");
            url = p.getProperty("sqlite.url");

            String initialSize = p.getProperty("druid.initialSize");
            String maxActive = p.getProperty("druid.maxActive");
            String minIdle = p.getProperty("druid.minIdle");
            String maxWait = p.getProperty("druid.maxWait");
            String maxOpenPreparedStatements = p.getProperty("druid.maxOpenPreparedStatements");

            druidDataSource = new DruidDataSource();
            druidDataSource.setDriverClassName(driver);
            druidDataSource.setUrl(url);
            druidDataSource.setInitialSize(Integer.parseInt(initialSize));  //初始连接数 默认0
            druidDataSource.setMaxActive(Integer.parseInt(maxActive));  //最大连接数 默认8
            druidDataSource.setMinIdle(Integer.parseInt(minIdle));  //最小闲置数
            druidDataSource.setMaxWait(Integer.parseInt(maxWait));  //获取连接的最大等待时间 单位毫秒
            druidDataSource.setMaxOpenPreparedStatements(Integer.parseInt(maxOpenPreparedStatements)); //缓存PreparedStatement的最大数量 默认不缓存 大于0时会自动开启缓存PreparedStatement
        } catch (Exception e) {
            log.error("加载配置文件失败", e);
        }
    }

    private void initDruidConnection() throws SQLException, ClassNotFoundException {
        if (null != druidPooledConnection) {
            return;
        }
        initDruidDataSource();
        // 3
        druidPooledConnection = druidDataSource.getConnection();

        druidPooledConnection.setAutoCommit(false);
    }

    public void query() {
        try {
            initDruidConnection();

            String sql = "select * from hero where type = %d limit %d";
            Statement statement = druidPooledConnection.createStatement();
            ResultSet resultSet1 = statement.executeQuery(String.format(sql, 3, 30));
            List<HeroDetailDto> list1 = this.set2detail(resultSet1);
            list1.forEach(System.out::println);

            boolean executed = statement.execute(String.format(sql, 2, 50));
            if (executed) {
                ResultSet resultSet2 = statement.getResultSet();
                List<HeroDetailDto> list2 = this.set2detail(resultSet2);
                list2.forEach(System.out::println);
            }

            String sql1 = "select * from hero where id > ? limit ?";
            PreparedStatement statement1 = druidPooledConnection.prepareStatement(sql1);
            statement1.setInt(1, 108);
            statement1.setInt(2, 30);

            ResultSet resultSet11 = statement1.executeQuery();
            List<HeroDetailDto> list11 = this.set2detail(resultSet11);
            list11.forEach(System.out::println);

        } catch (Exception e) {
            log.error("", e);
        } finally {
            try {
                druidPooledConnection.close();
            } catch (SQLException e) {
                log.error("", e);
            }
        }
    }

    public void update() {
        try {
            initConnection();

            Statement statement = connection.createStatement();
            int executedUpdate = statement.executeUpdate("insert into hero(id,name,type) values(342,'3官方违法',4),(5334,'v订购合同',4),(235435,'在个人感受个儿矮',4),(65633,'部分是各个t',4)");
            log.info("inserted[{}]", executedUpdate);

            PreparedStatement statement1 = connection.prepareStatement("insert into hero(id,name,type) values(?,?,?)");
            statement1.setString(2, "xhr54fdf");
            statement1.setInt(3, 3);
            for (int i = 2335; i < 2535; i++) {
                statement1.setInt(1, i);
                statement1.executeUpdate();
            }

            int ok = statement.executeUpdate("delete from hero where id > 108");
            log.info("deleted[{}]", ok);

//            System.out.println(1 / 0);

            connection.commit();
        } catch (Exception e) {
            log.error("", e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.error("回滚失败", ex);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("", e);
            }
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
        log.info("结果总数[{}]", list.size());
        return list;
    }
}
