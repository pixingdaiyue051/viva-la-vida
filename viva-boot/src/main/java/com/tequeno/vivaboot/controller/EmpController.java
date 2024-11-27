package com.tequeno.vivaboot.controller;

import com.tequeno.constants.HtCommonPage;
import com.tequeno.dto.HtResultModel;
import com.tequeno.dto.emp.EmpDetailDto;
import com.tequeno.dto.emp.EmpQueryDto;
import com.tequeno.dto.emp.EmpUptDto;
import com.tequeno.utils.HtResultUtil;
import com.tequeno.vivaboot.service.EmpService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("emp")
public class EmpController {

    @Resource
    private EmpService empService;

    @RequestMapping("page")
    public HtResultModel page(EmpQueryDto dto) {
        HtCommonPage<EmpDetailDto> page = empService.queryPage(dto);
        return HtResultUtil.success(page);
    }

    @RequestMapping("upt")
    public HtResultModel upt(EmpUptDto dto) {
        empService.updateEmp(dto);
        return HtResultUtil.success();
    }
}
