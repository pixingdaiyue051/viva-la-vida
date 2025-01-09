package com.tequeno.vivabootalpha.controller;

import com.tequeno.constants.HtCommonPage;
import com.tequeno.dto.HtResultModel;
import com.tequeno.dto.demo.DemoDetailDto;
import com.tequeno.dto.demo.DemoQueryDto;
import com.tequeno.enums.JedisKeyPrefixEnum;
import com.tequeno.service.IDemoService;
import com.tequeno.utils.HtResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRemoteService;
import org.redisson.api.RedissonClient;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("demo")
@Slf4j
public class DemoController {

    @Resource
    private RedissonClient redisson;

    @RequestMapping("echo")
    public HtResultModel echo() {
        RRemoteService remoteService = redisson.getRemoteService(JedisKeyPrefixEnum.REMOTE.getPrefix());
        IDemoService demoService = remoteService.get(IDemoService.class, 10000, TimeUnit.MILLISECONDS);
        String echo = demoService.echo();
        log.info("echo:{}", echo);

        DemoQueryDto query = new DemoQueryDto();
        HtCommonPage<DemoDetailDto> page = demoService.queryPage(query);
        return HtResultUtil.success(page);
    }

    @RequestMapping("face")
    public String face(Model model) {
        model.addAttribute("text", "波动幅度和巍峨s");
        model.addAttribute("utext", "<a href='https://www.baidu.com'>vga服务尴尬事</a>");
        return "face";
    }
}
