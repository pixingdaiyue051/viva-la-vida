package com.tequeno.vivabootalpha.controller;

import com.tequeno.constants.HtCommonPage;
import com.tequeno.dto.HtResultModel;
import com.tequeno.dto.demo.DemoDetailDto;
import com.tequeno.dto.demo.DemoQueryDto;
import com.tequeno.enums.JedisKeyPrefixEnum;
import com.tequeno.service.IDemoService;
import com.tequeno.utils.HtResultUtil;
import org.redisson.api.RRemoteService;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("demo")
public class DemoController {

    private final static Logger log = LoggerFactory.getLogger(DemoController.class);

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
}
