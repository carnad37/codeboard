package com.hhs.codeboard.web.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.hhs.codeboard.config.anno.AspectMenuActive;
import com.hhs.codeboard.config.common.LoggerController;
import com.hhs.codeboard.enumeration.MenuTypeEnum;
import com.hhs.codeboard.jpa.entity.board.BoardArticleEntity;
import com.hhs.codeboard.jpa.entity.menu.MenuEntity;
import com.hhs.codeboard.jpa.service.MenuDAO;
import com.hhs.codeboard.web.service.board.BoardArticleService;
import com.hhs.codeboard.web.service.member.MemberVO;
import com.hhs.codeboard.jpa.service.ArticleDAO;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/board")
@Controller
public class BoardController extends LoggerController {

    private final BoardArticleService articleService;

    @Autowired
    public BoardController(BoardArticleService articleService) {
        this.articleService = articleService;
    }

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
              @ModelAttribute MenuEntity MenuEntity, Model model) {
        //regUserSeq 랑 delDate로 조건을 건다.(모든 게시물)
//        List<BoardArticleEntity> articleList = articleDAO.findAllByRegUserSeqAndUuidAndDelDateIsNull(memberVO.getSeq(), MenuEntity.getSeq());
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
           @ModelAttribute @PathVariable(name = "uuid") String uuid, Model model) throws Exception {
        //해당 게시판의 Entity
        model.addAttribute("articleList", articleService.selectArticleList(memberVO, uuid));
        return "/board/list";
    }

    @RequestMapping("/{uuid}/write")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD)
    public String write(@AuthenticationPrincipal MemberVO memberVO,
            @ModelAttribute @PathVariable(name = "uuid") String uuid
            , Model model
            , BoardArticleEntity articleEntity) throws Exception {
        if (articleEntity.getSeq() != null) {
            model.addAttribute("article", articleService.selectArticle(articleEntity, memberVO));
        }
        return "/board/write";
    }

    @RequestMapping("/{uuid}/insert")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD)
    public String insert(@AuthenticationPrincipal MemberVO memberVO,
            @PathVariable(name = "uuid") String uuid
            , BoardArticleEntity articleEntity) throws Exception {

        articleService.insertArticle(articleEntity, memberVO, uuid);

        return "redirect:/board/" + uuid + "/list";
    }

    @RequestMapping("/{uuid}/update")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD)
    public String update(@AuthenticationPrincipal MemberVO memberVO,
             @PathVariable(name = "uuid") String uuid
            , BoardArticleEntity articleEntity) throws Exception {

        articleService.updateArticle(articleEntity, memberVO);

        return "redirect:/board/" + uuid + "/list";
    }

    @RequestMapping("/{uuid}/read")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD)
    public String read(@AuthenticationPrincipal MemberVO memberVO,
           @ModelAttribute @PathVariable(name = "uuid") String uuid,
           Model model) {
        return "/board/write";
    }
    
}
