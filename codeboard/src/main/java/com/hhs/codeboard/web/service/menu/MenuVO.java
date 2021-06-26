package com.hhs.codeboard.web.service.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.hhs.codeboard.enumeration.MenuTypeEnum;
import com.hhs.codeboard.jpa.entity.board.BoardManagerEntity;
import com.hhs.codeboard.jpa.entity.menu.MenuEntity;

import com.hhs.codeboard.web.service.menu.impl.MenuServiceimpl;
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

    public MenuVO (BoardManagerEntity boardManager) {
        super();
        this.seq = boardManager.getSeq();
        this.title = boardManager.getTitle();
        this.url = menuService.getBoardUrl(boardManager.getSeq());
        this.type = MenuTypeEnum.BOARD.getMenuType();
    }

    /**
     * 메뉴 entity로 초기화
     * @param menuEntity
     */
    public MenuVO (MenuEntity menuEntity) {
        super();
        this.seq = menuEntity.getSeq();
        this.title = menuEntity.getTitle();
        this.url =  "/board" + menuEntity.getUuid();
        this.type = menuEntity.getMenuType();
        this.order = menuEntity.getMenuOrder();
    }

    /**
     *
     * @param boardManagerList
     */
    public MenuVO (List<BoardManagerEntity> boardManagerList) {
        super();
        childrenMenu = new ArrayList<>();
        for (BoardManagerEntity targetVO : boardManagerList) {
            childrenMenu.add(new MenuVO(targetVO));
        }
    }

    private Integer seq;

    private String title;

    private Integer boardSeq;

    private Integer parentSeq;

    private String url;

    private String type;

    private List<MenuVO> childrenMenu;
    
    private Integer order;

}
