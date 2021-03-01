package com.hhs.codeboard.enumeration;

public enum MenuTypeEnum {
    
    BOARD("B"), CONFIG("C");

    private String menuType;

    MenuTypeEnum (String menuType) {
        this.menuType = menuType;
    }

    public String getMenuType() {
        return this.menuType;
    }

}
