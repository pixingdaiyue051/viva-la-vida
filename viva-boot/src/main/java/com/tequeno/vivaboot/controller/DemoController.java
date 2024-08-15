package com.tequeno.vivaboot.controller;

import com.tequeno.constants.HtCommonPage;
import com.tequeno.dto.HtResultModel;
import com.tequeno.dto.demo.DemoCrtDto;
import com.tequeno.dto.demo.DemoDetailDto;
import com.tequeno.dto.demo.DemoQueryDto;
import com.tequeno.dto.demo.DemoUptDto;
import com.tequeno.utils.HtResultUtil;
import com.tequeno.vivaboot.service.DemoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("demo")
public class DemoController {

    @Resource
    private DemoService demoService;

    @RequestMapping("page")
    public HtResultModel page(DemoQueryDto dto) {
        HtCommonPage<DemoDetailDto> page = demoService.queryPage(dto);
        return HtResultUtil.success(page);
    }

    @RequestMapping("add")
    public HtResultModel add(DemoCrtDto dto) {
        return HtResultUtil.success(demoService.add(dto));
    }

    @RequestMapping("upt")
    public HtResultModel upt(DemoUptDto dto) {
        return HtResultUtil.success(demoService.upt(dto));
    }

    @RequestMapping("del")
    public HtResultModel del(DemoUptDto dto) {
        return HtResultUtil.success(demoService.del(dto));
    }
}