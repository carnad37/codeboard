package com.hhs.codeboard.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    	web.ignoring().antMatchers("/dist/**", "/plugins/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
        		.antMatchers("/admin/**").hasRole("ADMIN")        		
        		.antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/actionLogin").permitAll()
                .antMatchers("/actionRegister").permitAll()
                .antMatchers("/**").authenticated();
         
        http.formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/actionLogin")
            .failureHandler(loginFailureHandler())
	        .defaultSuccessUrl("/")
	        .permitAll();
	        
        http.logout()
        	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        	.invalidateHttpSession(true);
        
        http.exceptionHandling()
        	.accessDeniedPage("/denied");
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new AuthenticationProviderImpl();
    }

    
}