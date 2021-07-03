package com.hhs.codeboard.enumeration;

import java.util.function.Function;

public enum MenuTypeEnum {
    
    BOARD("B", (uuid) -> "/board/" + uuid + "/list")
    , MENU("M", (uuid) -> null)
    , BOARD_CONFIG("D", (uuid) -> "/board/config")
    , MENU_CONFIG("U", (uuid) -> "/menu/config")
    , COMMON_BOARD("C", (uuid) -> "/common/board")
    , STATIC_MENU("S", (uuid) -> null)
    , CATEGORY_CONFIG("Y", (uuid) -> "/category/config");

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
