package com.tequeno.vivaboot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tequeno.constants.HtCommonPage;
import com.tequeno.dto.hero.HeroDetailDto;
import com.tequeno.dto.hero.HeroQueryDto;
import com.tequeno.vivaboot.converter.HeroConverter;
import com.tequeno.vivaboot.mapper.HeroMapper;
import com.tequeno.vivaboot.entity.Hero;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HeroService {

    @Resource
    private HeroMapper baseMapper;

    public HtCommonPage<HeroDetailDto> queryPage(HeroQueryDto dto) {
        QueryWrapper<Hero> query = this.getHeroQueryWrapper(dto);
        HtCommonPage<HeroDetailDto> page = new HtCommonPage<>();
        Page<Hero> selectedPage = baseMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()), query);
        if (selectedPage.getTotal() < 1) {
            return page;
        }
        page.setTotal(selectedPage.getTotal());
        page.setSize(selectedPage.getRecords().size());
        List<HeroDetailDto> list = selectedPage.getRecords().stream()
                .map(HeroConverter.INSTANCE::entity2Detail)
                .collect(Collectors.toList());
        page.setRecords(list);
        return page;
    }

    public void exportExcel(MultipartFile file) throws Exception {

        try (XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream())) {
            XSSFSheet sheet0 = wb.getSheetAt(0);
            int lastRowNum = sheet0.getLastRowNum();
            List<String> errList = new ArrayList<>();
            for (int i = 1; i < lastRowNum + 1; i++) {
                try {
                    XSSFRow row = sheet0.getRow(i);
                    String rank = row.getCell(0).getStringCellValue();
                    String name = row.getCell(1).getStringCellValue();
                    String constellation = row.getCell(2).getStringCellValue();
                    String nickName = row.getCell(3).getStringCellValue();
                    String weaponry = row.getCell(4).getStringCellValue();
                    String position = row.getCell(5).getStringCellValue();
                    String finale = row.getCell(6).getStringCellValue();


                    Hero entity = new Hero();
                    entity.setId((long) i);
                    entity.setName(name);
                    entity.setConstellation(constellation);
                    entity.setShortConstellation(entity.getConstellation().substring(1, 2));
                    entity.setNickName(nickName);
                    entity.setWeaponry(weaponry);
                    entity.setPosition(position);
                    entity.setFinale(finale);
                    entity.setFullRank(Integer.valueOf(rank));
                    if (i < 37) {
                        entity.setType(1);
                        entity.setRank(entity.getFullRank());
                    } else {
                        entity.setType(2);
                        entity.setRank(entity.getFullRank() - 36);
                    }
                    baseMapper.insert(entity);
                } catch (Exception e) {
                    String msg = String.format("第%d行导入失败[%s]", i + 1, e.getMessage());
                    errList.add(msg);
                }
            }
            if (CollectionUtils.isNotEmpty(errList)) {
                errList.add("其余均导入成功");
                String str = String.join(",", errList);
                throw new RuntimeException(str);
            }
        }
    }

    public QueryWrapper<Hero> getHeroQueryWrapper(HeroQueryDto dto) {
        QueryWrapper<Hero> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(dto.getName())) {
            query.eq("name", dto.getName());
        }
        if (StringUtils.isNotEmpty(dto.getNameLike())) {
            query.like("name", dto.getNameLike());
        }
        if (StringUtils.isNotEmpty(dto.getNickName())) {
            query.eq("nick_name", dto.getNickName());
        }
        if (StringUtils.isNotEmpty(dto.getNickName())) {
            query.like("nick_name", dto.getNickNameLike());
        }
        if (null != dto.getType()) {
            query.eq("type", dto.getType());
        }
        if (null != dto.getRankGt()) {
            query.gt("rank", dto.getRankGt());
        }
        if (null != dto.getRankLt()) {
            query.lt("rank", dto.getRankLt());
        }
        return query;
    }
}
