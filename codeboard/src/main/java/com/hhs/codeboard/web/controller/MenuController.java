package com.hhs.codeboard.web.controller;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhs.codeboard.config.anno.AspectMenuActive;
import com.hhs.codeboard.config.common.LoggerController;
import com.hhs.codeboard.enumeration.MenuTypeEnum;
import com.hhs.codeboard.jpa.entity.menu.MenuEntity;
import com.hhs.codeboard.util.common.SessionUtil;
import com.hhs.codeboard.web.service.member.MemberVO;
import com.hhs.codeboard.web.service.menu.MenuService;

import com.hhs.codeboard.web.service.menu.MenuVO;
import com.hhs.codeboard.web.service.menu.TestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping("/refresh")
    public String menuRefresh(@AuthenticationPrincipal MemberVO memberVO
            , HttpServletRequest request) {
        //로그인한 회원정보로 menu정보 리셋.
        SessionUtil.setSession(request, "menuList", menuService.initMenuList(memberVO, request));
        return "redirect:/menu/list";
    }

    @AspectMenuActive(menuType = MenuTypeEnum.MENU_CONFIG)
    @RequestMapping("/list")
    public String menuConfig(@AuthenticationPrincipal MemberVO memberVO
            , HttpServletRequest request
            , Model model) {

        List<MenuVO> menuList = SessionUtil.getSession(request, "menuList");
        List<MenuVO> parentList = new ArrayList<>();
        Integer maxDepth = SessionUtil.getSession(request, "maxDepth");

        menuList.forEach(menuVO -> {
            if (menuVO.getDepth() == maxDepth) {
                parentList.add(menuVO);
            }
        });
        model.addAttribute("parentList", parentList);
        return "menu/list";
    }

    @AspectMenuActive(menuType = MenuTypeEnum.MENU_CONFIG)
    @RequestMapping("/insert")
    public String insert(@AuthenticationPrincipal MemberVO memberVO
            , @Validated MenuEntity insertMenu, Integer parentSeq, TestVO testVO
        , HttpServletRequest request) throws Exception {
        menuService.insertMenu(insertMenu, memberVO, MenuTypeEnum.MENU);
        return "redirect:/menu/refresh";
    }

    //ajax :: 게시판 정보 불러옴
    @ResponseBody
    @RequestMapping("/getMenuInfo")
    public ResponseEntity<MenuEntity> getMenuInfo(
            @AuthenticationPrincipal MemberVO memberVO,
            @ModelAttribute MenuEntity menu) throws Exception {
        MenuEntity targetMenu = menuService.selectMenu(memberVO.getSeq(), menu.getUuid());
        return ResponseEntity.ok(targetMenu);
    }

    @RequestMapping("/setMenuInfo")
    public ResponseEntity<String> setMenuInfo(
            @AuthenticationPrincipal MemberVO memberVO,
            @RequestBody String depthOne, @RequestBody String depthTwo, @RequestBody String[] delSeqs) throws Exception {

        //TODO :: 무한 뎁스구조로 바꿀시 수정해야함.
        //수정 및 삭제가 필요.(seq값이 없는건 추가, 있는건 수정. Menu중에서 db에 없는건 삭제)
        try {
            //1 뎁스
            List<MenuEntity> depthOneList = new ObjectMapper().readValue(depthOne, new TypeReference<List<MenuEntity>>() {});
            //2 뎁스
            List<MenuEntity> depthTwoList = new ObjectMapper().readValue(depthTwo, new TypeReference<List<MenuEntity>>() {});

            List<List<MenuEntity>> allList = new ArrayList<>();
            allList.add(depthOneList);

            menuService.txUpdateDepth(allList, delSeqs, memberVO.getSeq());

        } catch (Exception e) {
            return ResponseEntity.ok("fail");
        }
        return ResponseEntity.ok("success");
    }


}
