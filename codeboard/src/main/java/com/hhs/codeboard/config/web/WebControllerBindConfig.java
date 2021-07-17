package com.hhs.codeboard.config.web;

import com.hhs.codeboard.config.web.property.CustomIntegerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class WebControllerBindConfig {

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(
            Integer.class,
            new CustomIntegerEditor(10,false)
        );
    }

}
