package com.hhs.codeboard.util.common;

import javax.servlet.http.HttpServletRequest;


public class SessionUtil {

    public static void setSession (HttpServletRequest request, String key, Object value) {
        request.getSession().setAttribute(key, value);
    }

    public static <T> T getSession (HttpServletRequest request, String key) {
        return (T) request.getSession().getAttribute(key);
    }

}
