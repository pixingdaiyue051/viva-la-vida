package com.tequeno.servlet;

import com.alibaba.fastjson.JSON;
import com.tequeno.constants.HtCommonPage;
import com.tequeno.dto.hero.HeroDetailDto;
import com.tequeno.dto.hero.HeroQueryDto;
import com.tequeno.dto.hero.HeroUpsertDto;
import com.tequeno.mapper.HeroMapper;
import com.tequeno.utils.HtResultUtil;
import com.tequeno.utils.SnowFlakeUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "HeroServlet", value = {"/hero", "/hero/*"})
@Slf4j
public class HeroServlet extends HttpServlet {

    private static final long serialVersionUID = 8828273436091818923L;

    private SqlSession sqlSession;

    private HeroMapper heroMapper;

    @Override
    public void init() {
        try (InputStream is = Resources.getResourceAsStream("mybatis.xml")) {
            sqlSession = new SqlSessionFactoryBuilder().build(is).openSession();
            heroMapper = sqlSession.getMapper(HeroMapper.class);
        } catch (IOException e) {
            log.error("加载sqlSession异常", e);
            sqlSession = null;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        String uri = req.getRequestURI();
        try {
            switch (uri) {
                case "/hero/page":
                    page(req, writer);
                    break;
//                case "/hero/add":
//                    add(req, writer);
//                    break;
//                case "/hero/update":
//                    upt(req, writer);
//                    break;
//                case "/hero/delete":
//                    del(req, writer);
//                    break;
                default:
                    writer.write(JSON.toJSONString(HtResultUtil.fail("暂无该功能")));
            }
            writer.flush();
        } catch (Exception e) {
            log.error("异常", e);
            writer.write(JSON.toJSONString(HtResultUtil.fail(e.getMessage())));
            writer.flush();
        }
    }

    private void page(HttpServletRequest req, PrintWriter writer) {
        HeroQueryDto dto = new HeroQueryDto();
        dto.setName(req.getParameter("name"));
        dto.setNameLike(req.getParameter("nameLike"));
        dto.setNickName(req.getParameter("nickName"));
        dto.setNickNameLike(req.getParameter("nickNameLike"));
        String type = req.getParameter("type");
        try {
            dto.setType(Integer.parseInt(type));
        } catch (NumberFormatException ignored) {

        }
        String rankGt = req.getParameter("rankGt");
        try {
            dto.setRankGt(Integer.parseInt(rankGt));
        } catch (NumberFormatException ignored) {

        }
        String rankLt = req.getParameter("rankLt");
        try {
            dto.setRankLt(Integer.parseInt(rankLt));
        } catch (NumberFormatException ignored) {

        }
        HtCommonPage<HeroDetailDto> page = new HtCommonPage<>();
        long total = heroMapper.count(dto);
        page.setTotal(total);
        if (total > 0) {
            List<HeroDetailDto> list = heroMapper.select(dto);
            page.setRecords(list);
            page.setSize(list.size());
        }
        writer.write(JSON.toJSONString(HtResultUtil.success(page)));
    }

    @Override
    public void destroy() {
        if (null != sqlSession) {
            sqlSession.close();
        }
    }
}
