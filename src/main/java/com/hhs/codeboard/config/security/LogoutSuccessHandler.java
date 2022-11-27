package com.hhs.codeboard.config.security;

import com.hhs.codeboard.enumeration.UserSessionData;
import com.hhs.codeboard.util.common.SessionUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class LogoutSuccessHandler implements LogoutHandler {

//    private final UserCache userCache;
//
//    public LogoutSuccessHandler(UserCache userCache) {
//        this.userCache = userCache;
//    }

    @Override
    public void logout(final HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Arrays.stream(UserSessionData.values()).forEach(
            sessionData -> {
                request.getSession().removeAttribute(sessionData.getValue());
            }
        );
    }

}
