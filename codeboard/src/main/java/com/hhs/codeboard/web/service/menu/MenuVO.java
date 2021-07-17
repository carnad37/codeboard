package com.hhs.codeboard.web.service.menu;

import com.hhs.codeboard.jpa.entity.menu.MenuEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class MenuVO implements Serializable {

    private static final long serialVersionUID = 9089120878346647881L;

    public MenuVO() {
        super();
    }

    public MenuVO(List<MenuVO> childrenMenu) {
        super();
        this.childrenMenu = childrenMenu;
    }

    public MenuVO(MenuEntity menu) {
        super();
        this.menu = menu;
        this.uuid = menu.getUuid();
        this.menuType = menu.getMenuType();
    }

    public MenuVO(MenuEntity menu, String url) {
        super();
        this.menu = menu;
        this.url = url;
        this.uuid = menu.getUuid();
        this.menuType = menu.getMenuType();
    }

    private MenuVO parentMenu;
    private List<MenuVO> childrenMenu;
    private MenuEntity menu;
    private String url;
    private String uuid;
    private String menuType;
    private Integer depth;

}
