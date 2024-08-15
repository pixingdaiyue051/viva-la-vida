package com.tequeno.service;

import com.tequeno.constants.HtCommonPage;
import com.tequeno.dto.demo.DemoDetailDto;
import com.tequeno.dto.demo.DemoQueryDto;

public interface IDemoService {

    String echo();

    HtCommonPage<DemoDetailDto> queryPage(DemoQueryDto dto);

}
