package com.hhs.codeboard.web.service.menu;

import com.hhs.codeboard.enumeration.MenuTypeEnum;
import com.hhs.codeboard.jpa.entity.menu.MenuEntity;
import com.hhs.codeboard.jpa.service.MenuDAO;
import com.hhs.codeboard.web.service.member.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class MenuService {

    private final MenuDAO menuDAO;

    @Autowired
    public MenuService(MenuDAO menuDAO) {
        this.menuDAO = menuDAO;
    }

    public List<MenuEntity> selectAllBoardMenu(int regUserSeq) {
        List<MenuEntity> resultList = new ArrayList<>();
        List<MenuEntity> menuList = menuDAO.findAllByRegUserSeq(regUserSeq);
        menuList.forEach(
                MenuEntity -> resultList.add(new MenuEntity())
        );
        return resultList;
    }


    public List<MenuVO> initMenuList(MemberVO memberVO) {

        List<MenuEntity> dbMenuList = memberVO.getMenuList();
        Map<String, MenuEntity> menuMap = new HashMap<>();

        List<MenuVO> menuList = new ArrayList<>();
        List<MenuVO> setInnerList = new ArrayList<>();

        //공통 설정
        MenuVO setMenu = new MenuVO(new MenuEntity(0, "설정메뉴", MenuTypeEnum.STATIC_MENU.getMenuType()));
        //공통게시판 내용추가
        setInnerList.add(new MenuVO(new MenuEntity(0, "게시판 설정", MenuTypeEnum.BOARD_CONFIG.getMenuType())));
        setInnerList.add(new MenuVO(new MenuEntity(0, "메뉴 설정", MenuTypeEnum.MENU_CONFIG.getMenuType())));
        setInnerList.add(new MenuVO(new MenuEntity(0, "카테고리 설정", MenuTypeEnum.CATEGORY_CONFIG.getMenuType())));

        menuList.add(setMenu);

        //공통 게시판
        Map<Integer, List<MenuVO>> childrenMap = new HashMap<>();

        //부모자식 구분하기
        for (MenuEntity dbMenu : dbMenuList) {
            MenuVO menuVO = new MenuVO(dbMenu);
            //uuid로 map을 만든다
            if (StringUtils.hasText(dbMenu.getUuid())) menuMap.put(dbMenu.getUuid(), dbMenu);
            if (dbMenu.getParentSeq() != null) {
                //부모값이 있는 하위 메뉴의 경우.
                List<MenuVO> tList = childrenMap.computeIfAbsent(dbMenu.getSeq(), (Integer parentBoard) -> new ArrayList<>());
                menuVO.setChildrenMenu(tList);
            } else if (MenuTypeEnum.MENU.getMenuType().equals(dbMenu.getMenuType())) {
                //부모값이 없고, 단순메뉴인경우.
                menuList.add(menuVO);
            }
        }

        //sort해줄 필요가 있다.
        //하위들도 sort해줄필요가 있으므로 재귀를 돌리자.

        return menuList;
    };


//    public Integer getMenuSeq(HttpServletRequest request) throws Exception {
//        Cookie[] cookies = request.getCookies();
//        for(int i = 0; i < cookies.length; i++) {
//            if (MenuEntity.MENU_SEQ_COOKIE_NAME.equals(cookies[i].getName())) return Integer.parseInt(cookies[i].getValue());
//        }
//        return null;
//    }

    public String getBoardUrl(Integer boardSeq) {
        return "/board/" + boardSeq + "/list";
    }

//    public void sortMenuList (List<MenuEntity> menuList) {
//        //메뉴리스트가 있으면 sort. 없으면 종료.
//        if (menuList.size() > 1) {
//            menuList.stream().sorted(Comparator.comparing((MenuEntity::getMenuOrder)));
//            //자식들도 다 sort
//            for(MenuEntity tMenu : menuList) {
//                sortMenuList(tMenu.getChildrenMenu());
//            }
//        }
//    }

    // public List<Integer> getSiteAcitveByDepth (List<MenuEntity> menuList, Integer activeSeq) {
    //     List<Integer> activeList = new ArrayList<Integer>();
    //     getSiteAcitveByDepthLoop(menuList, activeList, activeSeq);
    //     return activeList;
    // }

    // private void getSiteAcitveByDepthLoop (List<MenuEntity> menuList, List<Integer> activeList, Integer activeSeq) {
    //     for (MenuEntity tMenuEntity : menuList) {
    //         //현재 선택한 메뉴인지 확인
    //         if (activeSeq.equals(tMenuEntity.getSeq())) {
    //             activeList.add(tMenuEntity.getSeq());
    //             break;
    //         }
    //         //재귀 호출
    //         if (!CollectionUtils.isEmpty(menuList))  getSiteAcitveByDepthLoop(tMenuEntity.getChildrenMenu(), activeList, activeSeq);
    //         //주회 종료후, activeList가 있으면 더이상 반복할 필요가 없다.
    //         if (activeList.size() > 0) {
    //             activeList.add(0, tMenuEntity.getSeq());
    //             break;
    //         }
    //     }
    // }

}
