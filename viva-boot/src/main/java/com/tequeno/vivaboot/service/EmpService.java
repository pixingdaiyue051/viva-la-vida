package com.tequeno.vivaboot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tequeno.constants.HtCommonPage;
import com.tequeno.dto.dept.DeptDetailDto;
import com.tequeno.dto.emp.EmpDetailDto;
import com.tequeno.dto.emp.EmpQueryDto;
import com.tequeno.vivaboot.converter.DeptConverter;
import com.tequeno.vivaboot.converter.EmpConverter;
import com.tequeno.vivaboot.dao.DeptMapper;
import com.tequeno.vivaboot.dao.EmpMapper;
import com.tequeno.vivaboot.entity.Emp;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpService {

    private final static Logger log = LoggerFactory.getLogger(EmpService.class);

    private final static EmpConverter converter = Mappers.getMapper(EmpConverter.class);

    private final static DeptConverter deptConverter = Mappers.getMapper(DeptConverter.class);

    @Resource
    private EmpMapper empMapper;

    @Resource
    private DeptMapper deptMapper;

    public HtCommonPage<EmpDetailDto> queryPage(EmpQueryDto empQueryDto) {
        QueryWrapper<Emp> query = new QueryWrapper<>();

        HtCommonPage<EmpDetailDto> page = new HtCommonPage<>();
        Page<Emp> selectedPage = empMapper.selectPage(new Page<>(empQueryDto.getPageNum(), empQueryDto.getPageSize()), query);
        if (selectedPage.getTotal() < 1) {
            return page;
        }
        page.setTotal(selectedPage.getTotal());
        page.setSize(selectedPage.getRecords().size());
        List<EmpDetailDto> list = selectedPage.getRecords().stream()
                .map(item -> {
                    EmpDetailDto detail = converter.entity2Detail(item);
                    DeptDetailDto dept = deptConverter.entity2Detail(deptMapper.selectById(item.getDeptId()));
                    detail.setDept(dept);
                    return detail;
                })
                .collect(Collectors.toList());
        page.setRecords(list);
        return page;
    }

}
