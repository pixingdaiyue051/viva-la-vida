package com.tequeno.jdbc;

import com.tequeno.dto.emp.EmpDetailDto;
import com.tequeno.dto.hero.HeroDetailDto;
import com.tequeno.dto.hero.HeroQueryDto;
import com.tequeno.dto.hero.HeroUpsertDto;
import com.tequeno.mapper.EmpMapper;
import com.tequeno.mapper.HeroMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.List;

@Slf4j
public class MybatisHandler {

    public void load() {
        DataSource dataSource = null;
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(HeroMapper.class);
    }

    public void loadXml() {
        try (InputStream is = Resources.getResourceAsStream("mybatis.xml");
             SqlSession sqlSession = new SqlSessionFactoryBuilder().build(is).openSession()) {

            EmpMapper empMapper = sqlSession.getMapper(EmpMapper.class);
            EmpDetailDto empDetailDto = empMapper.selectById(3L);
            log.info("结果[{}]", empDetailDto);


            HeroMapper heroMapper = sqlSession.getMapper(HeroMapper.class);
            HeroUpsertDto dto = new HeroUpsertDto();
            dto.setId(4363L);
            dto.setType(3);
            dto.setName("sdfwf");
            dto.setNickName("dbshs");
            dto.setRank(4363);
            int inserted = heroMapper.insert(dto);
            log.info("inserted[{}]", inserted);

            dto.setShortConstellation("3f");
            dto.setFinale("xvdsgesgsegergsfd");
            int updated = heroMapper.update(dto);
            log.info("updated[{}]", updated);

            HeroDetailDto heroDetailDto = heroMapper.selectById(3L);
            log.info("结果[{}]", heroDetailDto);

            HeroQueryDto queryDto = new HeroQueryDto();
            queryDto.setNickNameLike("天");
            queryDto.setType(2);
            queryDto.setRankGt(5);
            queryDto.setRankLt(30);
            List<HeroDetailDto> selected = heroMapper.select(queryDto);
            selected.forEach(System.out::println);
        } catch (Exception e) {
            log.error("异常", e);
        }
    }
}
