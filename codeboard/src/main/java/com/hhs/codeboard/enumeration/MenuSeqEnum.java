package com.hhs.codeboard.enumeration;

public enum MenuSeqEnum {

    CONFIG_TOP(-1), CONFIG_BOARD(-2), CONFIG_MENU(-3), CONFIG_CATE(-4), COMMON_BOARD(-5);

    private int menuSeq;

    MenuSeqEnum (int menuSeq) {
        this.menuSeq = menuSeq;
    }

    public int getMenuSeq() {
        return this.menuSeq;
    }
    
}
