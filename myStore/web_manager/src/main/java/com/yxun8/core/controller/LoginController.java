package com.yxun8.core.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.plugin.liveconnect.SecurityContextHelper;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("login")
public class LoginController {


    @RequestMapping("loginName")
    public Map getName(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        HashMap<Object, Object> map = new HashMap<>();
        map.put("username",name);
        return map;
    }
}
