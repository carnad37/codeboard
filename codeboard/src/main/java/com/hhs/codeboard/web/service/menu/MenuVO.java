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

    public MenuVO(MenuEntity menu) {
        super();
        this.menu = menu;
    }

    private List<MenuVO> childrenMenu;
    private MenuEntity menu;

}
