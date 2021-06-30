package com.hhs.codeboard.web.service.menu;

import java.io.Serializable;
import java.util.List;

import com.hhs.codeboard.enumeration.MenuTypeEnum;
import com.hhs.codeboard.jpa.entity.menu.MenuEntity;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuVO implements Serializable{

    @Autowired
    MenuService menuService;

    private static final long serialVersionUID = 4741866450856421798L;
    public final static String MENU_SEQ_COOKIE_NAME = "MID";
    public final static int MENU_SEQ_NOT_SELECT = 0;

    public MenuVO () {
        super();
    }

    public MenuVO (Integer seq, String title, String url, String type) {
        super();
        this.seq =seq;
        this.title = title;
        this.url = url;
        this.type = type;
    }

    /**
     * 메뉴 entity로 초기화
     * @param menuEntity
     */
    public MenuVO (MenuEntity menuEntity) {
        super();
        this.seq = menuEntity.getSeq();
        this.title = menuEntity.getTitle();
        this.url =  MenuTypeEnum.getEnumByMenuType(menuEntity.getMenuType()).getUrl(menuEntity.getUuid());
        this.type = menuEntity.getMenuType();
        this.order = menuEntity.getMenuOrder();
        this.uuid = menuEntity.getUuid();
    }

    private Integer seq;

    private String title;

    private Integer boardSeq;

    private Integer parentSeq;

    private String url;

    private String uuid;

    private String type;

    private List<MenuVO> childrenMenu;
    
    private Integer order;

}
