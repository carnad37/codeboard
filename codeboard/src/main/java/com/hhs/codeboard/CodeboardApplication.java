package com.hhs.codeboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.hhs.codeboard")
@EnableAutoConfiguration
public class CodeboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeboardApplication.class, args);
	}

}
