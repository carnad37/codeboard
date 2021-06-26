package com.hhs.codeboard.enumeration;

public enum MenuTypeEnum {
    
    BOARD("B")
    , MENU("M")
    , BOARD_CONFIG("D")
    , MENU_CONFIG("U")
    , COMMON_BOARD("C")
    , STATIC_MENU("S")
    , CATEGORY_CONFIG("Y");

    private String menuType;

    MenuTypeEnum (String menuType) {
        this.menuType = menuType;
    }

    public String getMenuType() {
        return this.menuType;
    }

    public String getUrl(String uuid) {
        if (MenuTypeEnum.BOARD.equals(this)) {
            return "board/" + uuid + "/list";
        }
        return "menu/" + uuid + "/list";
    }

}
