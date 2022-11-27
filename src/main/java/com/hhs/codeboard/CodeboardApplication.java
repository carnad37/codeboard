package com.hhs.codeboard;

import com.hhs.codeboard.config.anno.InitiationProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;


@SpringBootApplication
@PropertySources({
	@PropertySource(value = "classpath:datasource.properties")
})
@EnableConfigurationProperties({InitiationProperty.class})
public class CodeboardApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(CodeboardApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(CodeboardApplication.class, args);
	}

}
