package com.tequeno.vivaboot.controller;

import com.tequeno.dto.HtJmsRedisModel;
import com.tequeno.dto.HtResultModel;
import com.tequeno.utils.HtResultUtil;
import com.tequeno.utils.TimeUtil;
import com.tequeno.vivaboot.service.RedissonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("redisson")
public class RedissonController {

    private final static Logger logger = LoggerFactory.getLogger(RedissonController.class);

    @Resource
    private RedissonService redissonService;

    @RequestMapping("seq")
    public HtResultModel seq() {
        return HtResultUtil.success(redissonService.seq());
    }

    @RequestMapping("lock")
    public HtResultModel lock() {
        redissonService.lock();
        return HtResultUtil.success(TimeUtil.now());
    }

    @RequestMapping("delay")
    public HtResultModel delay(HtJmsRedisModel model) {
        redissonService.sendRedisDelayMsg(model);
        return HtResultUtil.success(TimeUtil.now());
    }

    @RequestMapping("pub")
    public HtResultModel pub(HtJmsRedisModel model) {
        redissonService.publishRedisMsg(model);
        return HtResultUtil.success(TimeUtil.now());
    }

    @RequestMapping("act")
    public HtResultModel act() {
        redissonService.bloom();
        return HtResultUtil.success(TimeUtil.now());
    }
}
