package com.hhs.codeboard.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/board")
@Controller
public class BoardController {
    
    @RequestMapping("/config")
    public String config() {
        return "/main";
    }

    @RequestMapping("/list")
    public String list() {
        
        return "/board/list";
    }

    @RequestMapping("/read")
    public String read() {
        
        return "/board/read";
    }

    @RequestMapping("/write")
    public String write() {
        
        return "/board/write";
    }

    
}
