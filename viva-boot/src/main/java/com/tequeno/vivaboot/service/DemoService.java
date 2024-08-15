package com.tequeno.vivaboot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tequeno.constants.HtCommonPage;
import com.tequeno.dto.demo.DemoCrtDto;
import com.tequeno.dto.demo.DemoDetailDto;
import com.tequeno.dto.demo.DemoQueryDto;
import com.tequeno.dto.demo.DemoUptDto;
import com.tequeno.service.IDemoService;
import com.tequeno.vivaboot.converter.DemoConverter;
import com.tequeno.vivaboot.dao.DemoMapper;
import com.tequeno.vivaboot.entity.Demo;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DemoService implements IDemoService {

    private final static Logger logger = LoggerFactory.getLogger(DemoService.class);

    private final static DemoConverter converter = Mappers.getMapper(DemoConverter.class);

    @Resource
    private DemoMapper demoMapper;

    @Override
    public String echo() {
        return "ready";
    }

    @Override
    public HtCommonPage<DemoDetailDto> queryPage(DemoQueryDto dto) {
        QueryWrapper<Demo> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(dto.getName())) {
            query.eq("name", dto.getName());
        }

        HtCommonPage<DemoDetailDto> page = new HtCommonPage<>();

//        Page<Demo> selectedPage = demoMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()), query);
//        if (selectedPage.getTotal() > 0) {
//            page.setTotal(selectedPage.getTotal());
//            page.setRecords(selectedPage.getRecords().stream().map(converter::entity2Detail).collect(Collectors.toList()));
//        }

        long total = demoMapper.selectDemoCount(dto);
        if (total > 0) {
            page.setTotal(total);
            List<DemoDetailDto> records = demoMapper.selectDemoPage(dto);
            page.setRecords(records);
        }
        return page;
    }

    public boolean add(DemoCrtDto dto) {
        Demo entity = converter.crt2Entity(dto);
        int inserted = demoMapper.insert(entity);
        return inserted > 0;
    }

    public boolean upt(DemoUptDto dto) {
        Demo entity = converter.upt2Entity(dto);
        demoMapper.updateById(entity);
        return true;
    }

    public boolean del(DemoUptDto dto) {
        demoMapper.deleteById(dto.getId());
        return true;
    }
}
