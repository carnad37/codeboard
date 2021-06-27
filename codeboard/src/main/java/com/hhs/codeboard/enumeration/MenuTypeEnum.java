package com.hhs.codeboard.enumeration;

import java.util.function.Function;

public enum MenuTypeEnum {
    
    BOARD("B", (uuid) -> "board/" + uuid + "link")
    , MENU("M", (uuid) -> null)
    , BOARD_CONFIG("D", null)
    , MENU_CONFIG("U", null)
    , COMMON_BOARD("C", null)
    , STATIC_MENU("S", null)
    , CATEGORY_CONFIG("Y", null);

    private String menuType;
    private Function<String, String> url;

    MenuTypeEnum (String menuType, Function<String, String> url) {
        this.menuType = menuType;
        this.url = url;
    }

    public String getMenuType() {
        return this.menuType;
    }

    public String getUrl(String uuid) {
        return url.apply(uuid);
    }

    public static MenuTypeEnum getEnumByMenuType(String menuType) {
        for (MenuTypeEnum targetEnum : values()) {
            if (targetEnum.getMenuType().equals(menuType)) return targetEnum;
        }
        return null;
    }

}
