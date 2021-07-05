package com.hhs.codeboard.web.controller;

import java.util.List;

import com.hhs.codeboard.config.anno.AspectMenuActive;
import com.hhs.codeboard.config.common.LoggerController;
import com.hhs.codeboard.enumeration.MenuTypeEnum;
import com.hhs.codeboard.jpa.entity.board.BoardArticleEntity;
import com.hhs.codeboard.jpa.entity.menu.MenuEntity;
import com.hhs.codeboard.jpa.service.MenuDAO;
import com.hhs.codeboard.web.service.member.MemberVO;
import com.hhs.codeboard.jpa.service.ArticleDAO;

import com.hhs.codeboard.web.service.menu.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/board")
@Controller
public class BoardController extends LoggerController {

    @Autowired
    ArticleDAO articleDAO;

    @Autowired
    MenuDAO menuDAO;

    //TODO :: 테스트 페이지. 추후엔 기본을 list로 한다.
    @RequestMapping("/config")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD_CONFIG)
    public String config() {
        //게시판 추가가 가능해야함.
        return "/board/config";
    }

    @RequestMapping("/list")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD_CONFIG)
    public String managerList(@AuthenticationPrincipal MemberVO memberVO,
              @ModelAttribute MenuVO menuVO, Model model) {
        //regUserSeq 랑 delDate로 조건을 건다.(모든 게시물)
//        List<BoardArticleEntity> articleList = articleDAO.findAllByRegUserSeqAndUuidAndDelDateIsNull(memberVO.getSeq(), menuVO.getSeq());
//        model.addAttribute("articleList", articleList);
        return "/board/list";
    }

    @RequestMapping("/read")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD_CONFIG)
    public String managerRead() {
        return "/board/read";
    }

    @RequestMapping("/write")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD_CONFIG)
    public String managerWrite() {

        return "/board/write";
    }

    @RequestMapping("/{uuid}/list")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD)
    @Transactional
    public String list(@AuthenticationPrincipal MemberVO memberVO,
           @PathVariable(name = "uuid") String uuid, Model model) {
        //해당 게시판의 Entity
        MenuEntity menu = menuDAO.findByUuidAndRegUserSeq(uuid, memberVO.getSeq()).orElse(new MenuEntity());
        model.addAttribute("articleList", menu.getArticleList());
        return "/board/list";
    }

    @RequestMapping("/{uuid}/write")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD)
    public String write(@AuthenticationPrincipal MemberVO memberVO,
            @PathVariable(name = "uuid") String uuid, Model model) {
        return "/board/write";
    }

    @RequestMapping("/{uuid}/read")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD)
    public String read(@AuthenticationPrincipal MemberVO memberVO,
           @PathVariable(name = "uuid") String uuid, Model model) {
        return "/board/write";
    }
    
}
