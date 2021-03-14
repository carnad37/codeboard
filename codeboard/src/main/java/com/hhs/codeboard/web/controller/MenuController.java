package com.hhs.codeboard.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu")
public class MenuController {
    
    @RequestMapping("config")
    public String menuConfig() {

        return "/main";
    }



}
