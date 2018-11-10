package com.tequeno.controller;

import com.tequeno.dto.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @ResponseBody
    public String doLogin(User user) {

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            return e.getMessage();
        }

        return "登陆成功";
    }

    @RequiresRoles(value = {"admin","admin1"},logical = Logical.OR)
    @RequestMapping(value = "/testShiro",method = RequestMethod.GET)
    @ResponseBody
    public String testShiroAnnotation(){
        try {
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequiresRoles("admin")
//    @RequiresPermissions("user:q")
    @RequestMapping(value = "/testShiro1",method = RequestMethod.GET)
    @ResponseBody
    public String testShiroAnnotation1(){
        try {
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
