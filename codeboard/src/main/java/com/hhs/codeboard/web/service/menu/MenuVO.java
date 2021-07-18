package com.hhs.codeboard.web.service.menu;

import com.hhs.codeboard.jpa.entity.menu.MenuEntity;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

/**
 * 내부 vo에서 뽑아옴.
 * Lombok사용안함.
 */
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
    }

    private MenuVO parentMenu;
    private List<MenuVO> childrenMenu;
    private MenuEntity menu;
    private Integer depth;

    public Integer getSeq() {
        return this.menu.getSeq();
    }

    public void setSeq(Integer seq) {
        this.menu.setSeq(seq);
    }


    public MenuVO getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(MenuVO parentMenu) {
        this.parentMenu = parentMenu;
    }

    public MenuEntity getMenu() {
        return menu;
    }

    public void setMenu(MenuEntity menu) {
        this.menu = menu;
    }

    public Integer getMenuOrder() {
        return this.menu.getMenuOrder();
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menu.setMenuOrder(menuOrder);
    }

    public Integer getParentSeq() {
        return this.menu.getParentSeq();
    }

    public void setParentSeq(Integer parentSeq) {
        this.menu.setParentSeq(parentSeq);
    }

    public String getPublicF() {
        return this.menu.getPublicF();
    }

    public void setPublicF(String publicF) {
        this.menu.setPublicF(publicF);
    }

    public String getUuid() {
        return this.menu.getUuid();
    }

    public void setUuid(String uuid) {
        this.menu.setUuid(uuid);
    }

    public String getMenuType() {
        return this.menu.getMenuType();
    }

    public void setMenuType(String menuType) {
        this.menu.setMenuType(menuType);
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public List<MenuVO> getChildrenMenu() {
        return childrenMenu;
    }

    public void setChildrenMenu(List<MenuVO> childrenMenu) {
        this.childrenMenu = childrenMenu;
    }
}
