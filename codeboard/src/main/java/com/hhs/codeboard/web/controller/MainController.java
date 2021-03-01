package com.hhs.codeboard.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/main")
@Controller
public class MainController {

	@RequestMapping("")
	public String main() {		
		return "main";
	}
	
	
}
