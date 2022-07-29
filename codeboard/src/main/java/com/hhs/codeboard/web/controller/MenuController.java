package com.hhs.codeboard.web.controller;

import com.hhs.codeboard.config.anno.AspectMenuActive;
import com.hhs.codeboard.config.common.LoggerController;
import com.hhs.codeboard.enumeration.MenuTypeEnum;
import com.hhs.codeboard.jpa.entity.menu.entity.MenuEntity;
import com.hhs.codeboard.util.common.SessionUtil;
import com.hhs.codeboard.web.service.member.MemberDto;
import com.hhs.codeboard.web.service.menu.MenuService;
import com.hhs.codeboard.web.service.menu.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/menu")
public class MenuController extends LoggerController {

    /**
     * 게시판 및 모든 메뉴 구조도가 나온다.
     * 드래그로 변경가능한건 어디까지나 수정시에만.
     * 추가할시엔 모달창으로 입력창이 뜨며 해당 내용을 전부 입력시에만 추가됨.
     * 게시판은 삭제는 불가능하고 이동만 가능.
     */

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 메뉴 업데이트
     * @param memberDto
     * @param request
     * @return
     */
    @RequestMapping("/refresh")
    public String menuRefresh(@AuthenticationPrincipal MemberDto memberDto
            , HttpServletRequest request) {
        //로그인한 회원정보로 menu정보 리셋.
        SessionUtil.setSession(request, "menuList", menuService.initMenuList(memberDto, request));
        return "redirect:/main";
    }

    /**
     * 메뉴 리스트
     * @param memberDto
     * @param request
     * @param model
     * @return
     */
    @AspectMenuActive(menuType = MenuTypeEnum.MENU_CONFIG)
    @RequestMapping("/list")
    public String menuConfig(@AuthenticationPrincipal MemberDto memberDto
            , HttpServletRequest request
            , Model model) {

        List<MenuVO> menuList = SessionUtil.getSession(request, "menuList");
        List<MenuVO> parentList = new ArrayList<>();
        final Integer preMaxDepth = (Integer) SessionUtil.getSession(request, "maxDepth") - 1;
        for (MenuVO targetVO : menuList) {
            if (targetVO.getDepth() == preMaxDepth) {
                parentList.add(targetVO);
            }
        }
        model.addAttribute("parentList", parentList);
        return "menu/list";
    }

    /**
     * 메뉴 추가
     * @param memberDto
     * @param insertMenu
     * @return
     * @throws Exception
     */
    @AspectMenuActive(menuType = MenuTypeEnum.MENU_CONFIG)
    @RequestMapping("/insert")
    public String insert(@AuthenticationPrincipal MemberDto memberDto
            , MenuEntity insertMenu) throws Exception {
        menuService.insertMenu(insertMenu, memberDto, MenuTypeEnum.MENU);
        return "redirect:/menu/refresh";
    }

    /**
     * 메뉴 수정
     * @param memberDto
     * @param updateMenu
     * @return
     * @throws Exception
     */
    @AspectMenuActive(menuType = MenuTypeEnum.MENU_CONFIG)
    @RequestMapping("/update")
    public String update(@AuthenticationPrincipal MemberDto memberDto
            , MenuEntity updateMenu) throws Exception {
        menuService.updateMenu(updateMenu, memberDto, MenuTypeEnum.MENU);
        return "redirect:/menu/refresh";
    }

    /**
     * 메뉴 삭제
     * @param memberDto
     * @param deleteMenu
     * @return
     * @throws Exception
     */
    @AspectMenuActive(menuType = MenuTypeEnum.MENU_CONFIG)
    @RequestMapping("/delete")
    public String delete(@AuthenticationPrincipal MemberDto memberDto
            , MenuEntity deleteMenu) throws Exception {
        menuService.deleteMenu(deleteMenu, memberDto, MenuTypeEnum.MENU);
        return "redirect:/menu/refresh";
    }

    /**
     * menuVO정보 호출
     * @param memberDto
     * @param menu
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/getMenuInfo")
    public ResponseEntity<MenuVO> getMenuInfo(
            @AuthenticationPrincipal MemberDto memberDto,
            @ModelAttribute MenuEntity menu,
            HttpServletRequest request) throws Exception {
        Map<Integer, MenuVO> menuMap = SessionUtil.getSession(request, "menuMap");
        return ResponseEntity.ok(menuMap.get(menu.getSeq()));
    }

    /**
     * 자식 메뉴 호출
     * (관리자에서는 menuMap에서 꺼내씀)
     * @param memberDto
     * @param menu
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/getChildrenList")
    public ResponseEntity<List<MenuVO>> getChildrenList(
            @AuthenticationPrincipal MemberDto memberDto,
            @ModelAttribute MenuEntity menu,
            HttpServletRequest request) throws Exception {
        Map<Integer, MenuVO> menuMap = SessionUtil.getSession(request, "menuMap");
        List<MenuVO> returnList = menuMap.get(menu.getSeq()).getChildrenMenu();
        return ResponseEntity.ok(returnList == null ? new ArrayList<>() : returnList);
    }

    //@Deprecated :: 2021-07-18 :: 모든 페이지 수정을 json으로 받아 일괄적으로 메뉴 업데이트하는 기능 필요없어짐.
//    @RequestMapping("/setMenuInfo")
//    public ResponseEntity<String> setMenuInfo(
//            @AuthenticationPrincipal MemberVO memberVO,
//            @RequestBody String depthOne, @RequestBody String depthTwo, @RequestBody String[] delSeqs) throws Exception {
//
//        //TODO :: 무한 뎁스구조로 바꿀시 수정해야함.
//        //수정 및 삭제가 필요.(seq값이 없는건 추가, 있는건 수정. Menu중에서 db에 없는건 삭제)
//        try {
//            //1 뎁스
//            List<MenuEntity> depthOneList = new ObjectMapper().readValue(depthOne, new TypeReference<List<MenuEntity>>() {});
//            //2 뎁스
//            List<MenuEntity> depthTwoList = new ObjectMapper().readValue(depthTwo, new TypeReference<List<MenuEntity>>() {});
//
//            List<List<MenuEntity>> allList = new ArrayList<>();
//            allList.add(depthOneList);
//
//            menuService.txUpdateDepth(allList, delSeqs, memberVO.getSeq());
//
//        } catch (Exception e) {
//            return ResponseEntity.ok("fail");
//        }
//        return ResponseEntity.ok("success");
//    }


}
