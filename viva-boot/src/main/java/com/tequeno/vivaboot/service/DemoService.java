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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DemoService implements IDemoService {

    @Resource
    private DemoMapper baseMapper;

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

//        Page<Demo> selectedPage = baseMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()), query);
//        if (selectedPage.getTotal() > 0) {
//            page.setTotal(selectedPage.getTotal());
//            page.setRecords(selectedPage.getRecords().stream().map(DemoConverter.INSTANCE::entity2Detail).collect(Collectors.toList()));
//        }

        long total = baseMapper.selectDemoCount(dto);
        if (total > 0) {
            page.setTotal(total);
            List<DemoDetailDto> records = baseMapper.selectDemoPage(dto);
            page.setRecords(records);
        }
        return page;
    }

    public boolean add(DemoCrtDto dto) {
        Demo entity = DemoConverter.INSTANCE.crt2Entity(dto);
        int inserted = baseMapper.insert(entity);
        return inserted > 0;
    }

    public boolean upt(DemoUptDto dto) {
        Demo entity = DemoConverter.INSTANCE.upt2Entity(dto);
        baseMapper.updateById(entity);
        return true;
    }

    public boolean del(DemoUptDto dto) {
        baseMapper.deleteById(dto.getId());
        return true;
    }
}
