package com.hhs.codeboard.config.anno;

import com.hhs.codeboard.enumeration.MenuTypeEnum;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AspectMenuActive {
    MenuTypeEnum menuType() default MenuTypeEnum.MENU;
}
