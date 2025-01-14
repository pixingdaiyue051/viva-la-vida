package com.tequeno.vivaboot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tequeno.constants.HtCommonPage;
import com.tequeno.dto.dept.DeptDetailDto;
import com.tequeno.dto.emp.EmpDetailDto;
import com.tequeno.dto.emp.EmpQueryDto;
import com.tequeno.dto.emp.EmpUpsertDto;
import com.tequeno.vivaboot.converter.DeptConverter;
import com.tequeno.vivaboot.converter.EmpConverter;
import com.tequeno.vivaboot.mapper.DeptMapper;
import com.tequeno.vivaboot.mapper.EmpMapper;
import com.tequeno.vivaboot.entity.Emp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpService {

    @Resource
    private EmpMapper baseMapper;

    @Resource
    private DeptMapper deptMapper;

    public HtCommonPage<EmpDetailDto> queryPage(EmpQueryDto empQueryDto) {
        QueryWrapper<Emp> query = new QueryWrapper<>();

        HtCommonPage<EmpDetailDto> page = new HtCommonPage<>();
        Page<Emp> selectedPage = baseMapper.selectPage(new Page<>(empQueryDto.getPageNum(), empQueryDto.getPageSize()), query);
        if (selectedPage.getTotal() < 1) {
            return page;
        }
        page.setTotal(selectedPage.getTotal());
        page.setSize(selectedPage.getRecords().size());
        List<EmpDetailDto> list = selectedPage.getRecords().stream()
                .map(item -> {
                    EmpDetailDto detail = EmpConverter.INSTANCE.entity2Detail(item);
                    DeptDetailDto dept = DeptConverter.INSTANCE.entity2Detail(deptMapper.selectById(item.getDeptId()));
                    detail.setDept(dept);
                    return detail;
                })
                .collect(Collectors.toList());
        page.setRecords(list);
        return page;
    }

    public void updateEmp(EmpUpsertDto dto) {
        if (null == dto.getId()) {
            return;
        }
        Emp entity = EmpConverter.INSTANCE.upt2Entity(dto);
        baseMapper.updateById(entity);

    }
}
