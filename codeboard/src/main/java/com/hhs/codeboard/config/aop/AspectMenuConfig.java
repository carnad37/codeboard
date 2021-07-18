package com.hhs.codeboard.config.aop;

import com.hhs.codeboard.config.anno.AspectMenuActive;
import com.hhs.codeboard.enumeration.MenuTypeEnum;
import com.hhs.codeboard.util.common.SessionUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
@Aspect
@EnableAspectJAutoProxy
public class AspectMenuConfig {

    @Around("@annotation(com.hhs.codeboard.config.anno.AspectMenuActive)")
    public Object menuActive (ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        AspectMenuActive menuAnno = methodSignature.getMethod().getAnnotation(AspectMenuActive.class);
        MenuTypeEnum targetEnum = menuAnno.menuType();
        SessionUtil.setSession(request, "viewMenuType", targetEnum.getMenuType());

        //기본적으로 현재 메뉴가 유저가 등록한 컨텐츠일때만 uuid를 부여
        //그 외에는 null로 초기화
        if (MenuTypeEnum.MENU.equals(targetEnum) || MenuTypeEnum.BOARD.equals(targetEnum)) {
            Map<String, String> pathVariable = (Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            SessionUtil.setSession(request, "viewMenuUUID", pathVariable.get("uuid"));
        } else {
            SessionUtil.setSession(request, "viewMenuUUID", null);
        }

        return pjp.proceed(pjp.getArgs());
    }

}
