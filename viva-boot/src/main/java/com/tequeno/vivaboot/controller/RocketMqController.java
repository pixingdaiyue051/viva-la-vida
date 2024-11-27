package com.tequeno.vivaboot.controller;

import com.tequeno.dto.HtJmsRocketModel;
import com.tequeno.dto.HtResultModel;
import com.tequeno.utils.HtResultUtil;
import com.tequeno.utils.TimeUtil;
import com.tequeno.vivaboot.config.mq.rocketmq.RocketProducer;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("rocket")
public class RocketMqController {

    @Resource
    private RocketProducer producer;

    @RequestMapping("send")
    public HtResultModel send(@RequestBody HtJmsRocketModel model) {
        producer.send(model);
        return HtResultUtil.success(TimeUtil.now());
    }

    @RequestMapping("batch")
    public HtResultModel batch(@RequestBody List<HtJmsRocketModel> modelList) {
        producer.send(modelList);
        return HtResultUtil.success(TimeUtil.now());
    }

    @RequestMapping("transaction")
    public HtResultModel transaction(@RequestBody HtJmsRocketModel model) {
        producer.sendTransaction(model);
        return HtResultUtil.success(TimeUtil.now());
    }
}