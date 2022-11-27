package com.hhs.codeboard.config.security;

import com.hhs.codeboard.web.service.member.MemberService;
import com.hhs.codeboard.web.service.member.impl.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;

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

        //공통 사용
        AuthenticationSuccessHandler successHandler = loginSuccessHandler();

        http.authorizeRequests()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/open/**").permitAll()
            .antMatchers("/**").authenticated()

            .and()
            .formLogin()
            .loginPage("/open/login")
            .usernameParameter("memberId")
            .passwordParameter("memberPassword")
            .loginProcessingUrl("/open/actionLogin")
            .failureHandler(loginFailureHandler())
            .successHandler(successHandler)
	        .permitAll()

            .and()
            .logout()
        	.logoutRequestMatcher(new AntPathRequestMatcher("/open/logout"))
            .addLogoutHandler(logoutSuccessHandler())
        	.invalidateHttpSession(true)

            .and()
            .exceptionHandling()
        	.accessDeniedPage("/denied")

            .and()
            .rememberMe().key("dontReadKey")
            .userDetailsService(memberService())
            .authenticationSuccessHandler(successHandler);
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider(authenticationProvider());
    }

    /**
     * 따로 해당 클래스에서 Component나 Service로 등록하지않고
     * 일괄적으로 Bean 등록
     */

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl();
    }

    @Bean
    public LogoutHandler logoutSuccessHandler() {
        return new LogoutSuccessHandler();
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

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    
}