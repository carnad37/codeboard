package com.hhs.codeboard.menu.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.hhs.codeboard.enumeration.MenuSeqEnum;
import com.hhs.codeboard.enumeration.MenuTypeEnum;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class MenuService {

    public List<MenuVO> initMenuList() {
        List<MenuVO> menuList = new ArrayList<>();
        List<MenuVO> setInnerList = new ArrayList<>();

        //공통 설정
        MenuVO setMenu = new MenuVO(MenuSeqEnum.CONFIG_TOP.getMenuSeq(), "설정메뉴", null, null);
        //공통 게시판
        MenuVO commonBoardMenu = new MenuVO(MenuSeqEnum.COMMON_BOARD.getMenuSeq(),
            "공통 게시판", this.getBoardUrl(-1), null);

        menuList.add(setMenu);
        menuList.add(commonBoardMenu);

        //공통게시판 내용추가
        setInnerList.add(new MenuVO(MenuSeqEnum.CONFIG_BOARD.getMenuSeq(),
            "게시판 설정", "/board/config", MenuTypeEnum.CONFIG.getMenuType()));
        setInnerList.add(new MenuVO(MenuSeqEnum.CONFIG_MENU.getMenuSeq(),
            "메뉴 설정", "/menu/config", MenuTypeEnum.CONFIG.getMenuType()));
        setInnerList.add(new MenuVO(MenuSeqEnum.CONFIG_CATE.getMenuSeq(),
            "카테고리 설정", "/category/config", MenuTypeEnum.CONFIG.getMenuType()));        

        setMenu.setChildrenMenu(setInnerList);

        return menuList;
    };


    public Integer getMenuSeq(HttpServletRequest request) throws Exception {
        Cookie[] cookies = request.getCookies();
        for(int i = 0; i < cookies.length; i++) {
            if (MenuVO.MENU_SEQ_COOKIE_NAME.equals(cookies[i].getName())) return Integer.parseInt(cookies[i].getValue());
        }
        return null;
    }

    public String getBoardUrl(Integer boardSeq) {
        return "/board/" + boardSeq + "/article";
    }

    public List<Integer> getSiteAcitveByDepth (List<MenuVO> menuList, Integer activeSeq) {
        List<Integer> activeList = new ArrayList<Integer>();
        getSiteAcitveByDepthLoop(menuList, activeList, activeSeq);
        return activeList;
    }

    private void getSiteAcitveByDepthLoop (List<MenuVO> menuList, List<Integer> activeList, Integer activeSeq) {         
        for (MenuVO tMenuVO : menuList) {
            //현재 선택한 메뉴인지 확인
            if (activeSeq.equals(tMenuVO.getSeq())) {                 
                activeList.add(tMenuVO.getSeq());
                break;
            }
            //재귀 호출
            if (!CollectionUtils.isEmpty(menuList))  getSiteAcitveByDepthLoop(tMenuVO.getChildrenMenu(), activeList, activeSeq);
            //주회 종료후, activeList가 있으면 더이상 반복할 필요가 없다.
            if (activeList.size() > 0) {
                activeList.add(0, tMenuVO.getSeq());
                break;
            }                        
        }
    }
} 
