package com.hhs.codeboard.web.service.menu;

import com.hhs.codeboard.enumeration.MenuTypeEnum;
import com.hhs.codeboard.jpa.entity.menu.MenuEntity;
import com.hhs.codeboard.jpa.service.MenuDAO;
import com.hhs.codeboard.util.common.SessionUtil;
import com.hhs.codeboard.web.service.member.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Transactional
@Service
public class MenuService {

    private final MenuDAO menuDAO;

    @Autowired
    public MenuService(MenuDAO menuDAO) {
        this.menuDAO = menuDAO;
    }

    public List<MenuEntity> selectAllBoardMenu(int regUserSeq) {
        List<MenuEntity> resultList = new ArrayList<>();
        List<MenuEntity> menuList = menuDAO.findAllByRegUserSeqAndDelDateIsNull(regUserSeq, Sort.by(Sort.Direction.DESC, "menuOrder"));
        menuList.forEach(
                MenuEntity -> resultList.add(new MenuEntity())
        );
        return resultList;
    }

    public List<MenuEntity> selectMenuList(int regUserSeq, MenuTypeEnum menuType) {
        List<MenuEntity> menuList = menuDAO.findAllByRegUserSeqAndMenuTypeAndDelDateIsNull(regUserSeq, menuType.getMenuType());
        return menuList;
    }

    public MenuEntity selectMenu(int regUserSeq, int menuSeq) throws Exception{
        return menuDAO.findBySeqAndRegUserSeqAndDelDateIsNull(menuSeq, regUserSeq).orElseThrow(()->new Exception("잘못된 접근입니다."));
    }

    public MenuEntity selectMenu(int regUserSeq, String uuid) throws Exception{
        return menuDAO.findByUuidAndRegUserSeqAndDelDateIsNull(uuid, regUserSeq).orElseThrow(()->new Exception("잘못된 접근입니다."));
    }

    public void insertMenu(MenuEntity menu, MemberVO memberVO, MenuTypeEnum menuType) throws Exception {
        if (menu.getParentSeq() > 0) {
            //parentSeq 체크, 없으면 exception
            selectMenu(memberVO.getSeq(), menu.getParentSeq());
        }

        MenuEntity insert = new MenuEntity();
        insert.setTitle(menu.getTitle());
        insert.setMenuType(menuType.getMenuType());
        insert.setPublicF(menu.getPublicF());
        insert.setMenuOrder(menu.getMenuOrder());
        insert.setParentSeq(menu.getParentSeq());
        insert.setRegUserSeq(memberVO.getSeq());
        insert.setRegDate(LocalDateTime.now());
        insert.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
        menuDAO.save(insert);
    }

    public void updateMenu(MenuEntity menu, MemberVO memberVO) throws Exception {
        if (menu.getParentSeq() > 0) {
            //parentSeq 체크, 없으면 exception
            selectMenu(memberVO.getSeq(), menu.getParentSeq());
        }

        MenuEntity update = StringUtils.hasText(menu.getUuid()) ?
                menuDAO.findByUuidAndRegUserSeqAndDelDateIsNull(menu.getUuid(), memberVO.getSeq()).orElseThrow(()->new Exception("잘못된 접근입니다."))
                : menuDAO.findBySeqAndRegUserSeqAndDelDateIsNull(menu.getSeq(), memberVO.getSeq()).orElseThrow(()->new Exception("잘못된 접근입니다."));
        update.setTitle(menu.getTitle());
        update.setModUserSeq(memberVO.getSeq());
        update.setMenuOrder(menu.getMenuOrder());
        update.setParentSeq(menu.getParentSeq());
        update.setPublicF(menu.getPublicF());
        update.setModDate(LocalDateTime.now());
        menuDAO.save(update);
    }

    public void deleteMenu(MenuEntity menu, MemberVO memberVO) throws Exception {

    }

    /**
     * 메뉴 초기화
     * @param memberVO
     * @param request
     * @return
     */
    public List<MenuVO> initMenuList(MemberVO memberVO, HttpServletRequest request) {

        List<MenuEntity> dbMenuList = menuDAO.findAllByRegUserSeqAndDelDateIsNull(memberVO.getSeq(), Sort.by(Sort.Direction.ASC, "menuOrder"));

        List<MenuVO> menuList = new ArrayList<>();
        List<MenuVO> setInnerList = new ArrayList<>();

        //공통 설정
        MenuVO setMenu = new MenuVO(new MenuEntity(0, "공통메뉴", MenuTypeEnum.STATIC_MENU.getMenuType()));
        //공통게시판 내용추가
        setInnerList.add(new MenuVO(new MenuEntity(0,  "게시판 목록", MenuTypeEnum.BOARD_CONFIG.getMenuType())));
        setInnerList.add(new MenuVO(new MenuEntity(0, "메뉴 목록", MenuTypeEnum.MENU_CONFIG.getMenuType())));
//        setInnerList.add(new MenuVO(new MenuEntity(0, "카테고리 설정", MenuTypeEnum.CATEGORY_CONFIG.getMenuType())));

        setMenu.setChildrenMenu(setInnerList);
        menuList.add(setMenu);

        //메뉴 맵 UUID::VO
        Map<Integer, MenuVO> menuMap = new HashMap<>();
        List<MenuVO> addTopList = new ArrayList<>();

        //부모자식 구분하기
        //게시판 같은경우 기본적으론 게시판 공통 메뉴 하위에 들어간다.
        //게시판 공통 메뉴 같은 경우 하위에 게시판이 있어야지만 활성화된다.
        for (MenuEntity dbMenu : dbMenuList) {
            MenuVO menuVO = new MenuVO(dbMenu);
            if (menuMap.containsKey(menuVO.getSeq())) {
                MenuVO targetVO = menuMap.get(menuVO.getSeq());
                menuVO.setChildrenMenu(targetVO.getChildrenMenu());
            }
            menuMap.put(menuVO.getSeq(), menuVO);
            if (menuVO.getParentSeq() != null && menuVO.getParentSeq() > 0) {
                //부모값이 있는경우
                //부모메뉴가 menuMap에 없을경우 임시값 생성
                MenuVO parentVO = menuMap.computeIfAbsent(menuVO.getParentSeq(), key -> new MenuVO());
                //childrenList 초기화
                List<MenuVO> childrenList = parentVO.getChildrenMenu() == null ? new ArrayList<>() : parentVO.getChildrenMenu();
                childrenList.add(menuVO);
                parentVO.setChildrenMenu(childrenList);
            } else {
                //최상위 메뉴
                addTopList.add(menuVO);
            }
        }

        MenuVO topMenu = new MenuVO();
        topMenu.setChildrenMenu(addTopList);
        menuList.addAll(addTopList);

        SessionUtil.setSession(request, "menuList", menuList);
        SessionUtil.setSession(request, "menuMap", menuMap);
        SessionUtil.setSession(request, "maxDepth", getMaxDepth(topMenu, 0));

        return menuList;
    };

    public void txUpdateDepth(List<List<MenuEntity>> targetList, String[] delSeqs, int regUserSeq) throws Exception {

        List<MenuEntity> beforeList = menuDAO.findAllByRegUserSeqAndDelDateIsNull(regUserSeq);
        //seq로 Map생성
        Map<Integer, MenuEntity> beforeMap = beforeList.stream()
                .collect(Collectors.toMap(
                    MenuEntity::getSeq,
                    Function.identity()
                ));

        for(List<MenuEntity> unitList : targetList) {
            for(MenuEntity target : unitList) {
                MenuEntity updateEntity = null;
                if (target.getSeq() > 0) {
                    //수정된 데이터
                    updateEntity = beforeMap.get(target.getSeq());
                    updateEntity.setModDate(LocalDateTime.now());
                    updateEntity.setModUserSeq(regUserSeq);
                    updateEntity.setMenuOrder(target.getMenuOrder());
                    updateEntity.setPublicF(target.getPublicF());
                    updateEntity.setTitle(target.getTitle());
                    updateEntity.setParentSeq(target.getParentSeq());
                } else {
                    //새로 입력된 데이터
                    updateEntity = new MenuEntity();
                    updateEntity.setRegDate(LocalDateTime.now());
                    updateEntity.setRegUserSeq(regUserSeq);
                    updateEntity.setMenuOrder(target.getMenuOrder());
                    updateEntity.setPublicF(target.getPublicF());
                    updateEntity.setTitle(target.getTitle());
                    updateEntity.setParentSeq(target.getParentSeq());
                }
                menuDAO.save(updateEntity);
            }
        }

        //TODO :: 삭제는 차후에 추가

    }

    /**
     * maxDept 얻기용 재귀 주회
     * @param targetVO
     * @param maxDepth
     * @return
     */
    private int getMaxDepth(MenuVO targetVO, int maxDepth) {
        //depth 설정
        targetVO.setDepth(maxDepth);

        if (targetVO.getChildrenMenu() == null) {
            //재귀 종료
            return maxDepth;
        } else {
            //재귀 진행
            maxDepth += 1;
        }

        //목록내에서 가장 큰 depth를 리턴
        for (MenuVO childrenVO : targetVO.getChildrenMenu()) {
            int depth = getMaxDepth(childrenVO, maxDepth);
            maxDepth = depth > maxDepth ? depth : maxDepth;
        }

        return maxDepth;
    }

//    public Integer getMenuSeq(HttpServletRequest request) throws Exception {
//        Cookie[] cookies = request.getCookies();
//        for(int i = 0; i < cookies.length; i++) {
//            if (MenuEntity.MENU_SEQ_COOKIE_NAME.equals(cookies[i].getName())) return Integer.parseInt(cookies[i].getValue());
//        }
//        return null;
//    }

//    public String getBoardUrl(Integer boardSeq) {
//        return "/board/" + boardSeq + "/list";
//    }

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
