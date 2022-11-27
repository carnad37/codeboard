package com.hhs.codeboard.web.controller;


import com.hhs.codeboard.config.common.LoggerController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/main")
@RestController
public class MainController extends LoggerController {

	@RequestMapping("")
	public String main() {
		//TODO :: 메인화면 제작
		return "main";
	}
	
	
}
