package com.hhs.codeboard.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping("/main")
	public String main() {
		
		return "/non/main";
	}

	@RequestMapping("/login")
	public String login() {
		
		return "/non/main";
	}
	
}
