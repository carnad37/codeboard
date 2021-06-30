package com.hhs.codeboard.web.service.menu.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.hhs.codeboard.enumeration.MenuTypeEnum;

import com.hhs.codeboard.jpa.entity.menu.MenuEntity;
import com.hhs.codeboard.jpa.service.MenuDAO;
import com.hhs.codeboard.web.service.menu.MenuService;
import com.hhs.codeboard.web.service.menu.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuDAO menuDAO;

    @Override
    public List<MenuVO> selectAllBoardMenu(int regUserSeq) {
        List<MenuVO> resultList = new ArrayList<>();
        List<MenuEntity> menuList = menuDAO.findAllByRegUserSeq(regUserSeq);
        menuList.forEach(
            menuVO -> resultList.add(new MenuVO(menuVO))
        );
        return resultList;
    }


    public List<MenuVO> initMenuList(List<MenuVO> dbMenuList) {

        List<MenuVO> menuList = new ArrayList<>();
        List<MenuVO> setInnerList = new ArrayList<>();

        //공통 설정
        MenuVO setMenu = new MenuVO(0, "설정메뉴", null, MenuTypeEnum.STATIC_MENU.getMenuType());
        //공통게시판 내용추가
        setInnerList.add(new MenuVO(0, "게시판 설정", "/board/config", MenuTypeEnum.BOARD_CONFIG.getMenuType()));
        setInnerList.add(new MenuVO(0, "메뉴 설정", "/menu/config", MenuTypeEnum.MENU_CONFIG.getMenuType()));
        setInnerList.add(new MenuVO(0, "카테고리 설정", "/category/config", MenuTypeEnum.CATEGORY_CONFIG.getMenuType()));

        setMenu.setChildrenMenu(setInnerList);
        menuList.add(setMenu);

        //공통 게시판
        Map<Integer, List<MenuVO>> childrenMap = new HashMap<>();

        //부모자식 구분하기
        for (MenuVO dbMenu : dbMenuList) {
            if (dbMenu.getParentSeq() != null) {
                //부모값이 있는 하위 메뉴의 경우.
                List<MenuVO> tList = childrenMap.computeIfAbsent(dbMenu.getSeq(), (Integer parentBoard) -> new ArrayList<>());
                dbMenu.setChildrenMenu(tList);
            } else if (MenuTypeEnum.MENU.getMenuType().equals(dbMenu.getType())) {
                //부모값이 없고, 단순메뉴인경우.
                menuList.add(dbMenu);
            }
        }

        //sort해줄 필요가 있다.
        //하위들도 sort해줄필요가 있으므로 재귀를 돌리자.

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
        return "/board/" + boardSeq + "/list";
    }

    public void sortMenuList (List<MenuVO> menuList) {
        //메뉴리스트가 있으면 sort. 없으면 종료.
        if (menuList.size() > 1) {
            menuList.stream().sorted(Comparator.comparing((MenuVO::getOrder)));
            //자식들도 다 sort
            for(MenuVO tMenu : menuList) {
                sortMenuList(tMenu.getChildrenMenu());
            }
        } 
    }

    // public List<Integer> getSiteAcitveByDepth (List<MenuVO> menuList, Integer activeSeq) {
    //     List<Integer> activeList = new ArrayList<Integer>();
    //     getSiteAcitveByDepthLoop(menuList, activeList, activeSeq);
    //     return activeList;
    // }

    // private void getSiteAcitveByDepthLoop (List<MenuVO> menuList, List<Integer> activeList, Integer activeSeq) {         
    //     for (MenuVO tMenuVO : menuList) {
    //         //현재 선택한 메뉴인지 확인
    //         if (activeSeq.equals(tMenuVO.getSeq())) {                 
    //             activeList.add(tMenuVO.getSeq());
    //             break;
    //         }
    //         //재귀 호출
    //         if (!CollectionUtils.isEmpty(menuList))  getSiteAcitveByDepthLoop(tMenuVO.getChildrenMenu(), activeList, activeSeq);
    //         //주회 종료후, activeList가 있으면 더이상 반복할 필요가 없다.
    //         if (activeList.size() > 0) {
    //             activeList.add(0, tMenuVO.getSeq());
    //             break;
    //         }                        
    //     }
    // }
} 
