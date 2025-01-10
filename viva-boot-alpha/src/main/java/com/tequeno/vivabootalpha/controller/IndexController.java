package com.tequeno.vivabootalpha.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class IndexController {

    @RequestMapping("index")
    public String index(Model model) {
        model.addAttribute("text", "波动幅度和巍峨s");
        model.addAttribute("utext", "<a href='https://www.baidu.com'>vga服务尴尬事</a>");
        return "index";
    }
}
