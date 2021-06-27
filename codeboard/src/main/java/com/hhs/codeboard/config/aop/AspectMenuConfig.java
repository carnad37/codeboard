package com.hhs.codeboard.config.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@EnableAspectJAutoProxy
public class AspectMenuConfig {

//    @Around("@annotation(AspectMenuActive)")
//    public Object menuActive (ProceedingJoinPoint pjp) throws Throwable {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        request.getParameter("uuid");
//        return pjp.proceed(pjp.getArgs());
//    }


}
