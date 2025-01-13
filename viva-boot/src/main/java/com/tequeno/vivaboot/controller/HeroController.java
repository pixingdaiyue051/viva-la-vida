package com.tequeno.vivaboot.controller;

import com.tequeno.constants.HtCommonPage;
import com.tequeno.dto.HtResultModel;
import com.tequeno.dto.hero.HeroDetailDto;
import com.tequeno.dto.hero.HeroQueryDto;
import com.tequeno.utils.HtResultUtil;
import com.tequeno.vivaboot.service.HeroService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("hero")
@Slf4j
public class HeroController {

    @Resource
    private HeroService heroService;

    @RequestMapping("page")
    public HtResultModel page(HeroQueryDto dto) {
        HtCommonPage<HeroDetailDto> page = heroService.queryPage(dto);
        return HtResultUtil.success(page);
    }

    @RequestMapping("import")
    public HtResultModel exportExcel(MultipartFile file) {
        try {
            heroService.exportExcel(file);
            return HtResultUtil.success("导入成功");
        } catch (Exception e) {
            log.error("导入失败", e);
            return HtResultUtil.fail(e.getMessage());
        }
    }
}
