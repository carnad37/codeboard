package com.hhs.codeboard.web.service.menu;

import com.hhs.codeboard.enumeration.MenuTypeEnum;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


public interface MenuService {

    public List<MenuVO> selectAllBoardMenu(int regUserSeq);

    public List<MenuVO> initMenuList(List<MenuVO> dbMenuList);

    public Integer getMenuSeq(HttpServletRequest request) throws Exception;

    public String getBoardUrl(Integer boardSeq);

    public void sortMenuList (List<MenuVO> menuList);

}
