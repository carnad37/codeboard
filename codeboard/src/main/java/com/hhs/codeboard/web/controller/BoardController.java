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

import com.hhs.codeboard.web.service.menu.MenuService;
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
    private final MenuService menuService;

    /**
     * BoardController의 경우 uuid는 @ModelAttribute로 담겨서 전달된다.
     */

    @Autowired
    public BoardController(BoardArticleService articleService, MenuService menuService) {
        this.articleService = articleService;
        this.menuService = menuService;
    }

    /**
     * 게시판 설정
     * @return
     */
    @RequestMapping("/config")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD_CONFIG)
    public String config() {
        //게시판 추가가 가능해야함.
        return "/board/config";
    }

    /**
     * 전체 게시판 목록
     * @param memberVO
     * @param MenuEntity
     * @param model
     * @return
     */
    @RequestMapping("/list")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD_CONFIG)
    public String managerList(@AuthenticationPrincipal MemberVO memberVO,
              @ModelAttribute MenuEntity MenuEntity, Model model) {
        //regUserSeq 랑 delDate로 조건을 건다.(모든 게시물)
//        List<BoardArticleEntity> articleList = articleDAO.findAllByRegUserSeqAndUuidAndDelDateIsNull(memberVO.getSeq(), MenuEntity.getSeq());
//        model.addAttribute("articleList", articleList);
        model.addAttribute("boardList", menuService.selectMenuList(memberVO.getSeq(), MenuTypeEnum.BOARD));
        return "/board/mgr/list";
    }

    /**
     * 게시판 내역 상세
     * @return
     */
    @RequestMapping("/read")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD_CONFIG)
    public String managerRead() {
        return "/board/mgr/read";
    }

    /**
     * 게시판 입력 페이지
     * @param memberVO
     * @param model
     * @param menuEntity
     * @return
     * @throws Exception
     */
    @RequestMapping("/write")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD_CONFIG)
    public String managerWrite(@AuthenticationPrincipal MemberVO memberVO,
            Model model, MenuEntity menuEntity
    ) throws Exception {
        if (menuEntity.getSeq() != null) {
            model.addAttribute("board", menuService.selectMenu(memberVO.getSeq(), menuEntity.getSeq()));
        }
        return "/board/mgr/write";
    }

    /**
     * 게시판 입력
     * @param memberVO
     * @param model
     * @param menuEntity
     * @return
     * @throws Exception
     */
    @RequestMapping("/insert")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD_CONFIG)
    public String managerInsert(@AuthenticationPrincipal MemberVO memberVO,
            Model model, MenuEntity menuEntity
    ) throws Exception {
        menuService.insertMenu(menuEntity, memberVO, MenuTypeEnum.BOARD);
        return "redirect:/board/list";
    }

    /**
     * 게시판 수정
     * @param memberVO
     * @param model
     * @param menuEntity
     * @return
     * @throws Exception
     */
    @RequestMapping("/update")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD_CONFIG)
    public String managerUpdate(@AuthenticationPrincipal MemberVO memberVO,
            Model model, MenuEntity menuEntity
    ) throws Exception {
        menuService.updateMenu(menuEntity, memberVO);
        return "redirect:/board/list";
    }

    /**
     * 게시물 리스트
     * @param memberVO
     * @param uuid
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/{uuid}/list")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD)
    @Transactional
    public String list(@AuthenticationPrincipal MemberVO memberVO,
           @ModelAttribute @PathVariable(name = "uuid") String uuid, Model model) throws Exception {
        //해당 게시판의 Entity
        model.addAttribute("articleList", articleService.selectArticleList(memberVO, uuid));
        return "/board/article/list";
    }

    /**
     * 게시물 입력 페이지
     * @param memberVO
     * @param uuid
     * @param model
     * @param articleEntity
     * @return
     * @throws Exception
     */
    @RequestMapping("/{uuid}/write")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD)
    public String write(@AuthenticationPrincipal MemberVO memberVO,
            @ModelAttribute @PathVariable(name = "uuid") String uuid
            , Model model
            , @ModelAttribute BoardArticleEntity articleEntity) throws Exception {
        if (articleEntity.getSeq() != null) {
            model.addAttribute("article", articleService.selectArticle(articleEntity, memberVO));
        }
        return "/board/article/write";
    }

    /**
     * 게시물 입력
     * @param memberVO
     * @param uuid
     * @param articleEntity
     * @return
     * @throws Exception
     */
    @RequestMapping("/{uuid}/insert")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD)
    public String insert(@AuthenticationPrincipal MemberVO memberVO,
            @PathVariable(name = "uuid") String uuid
            , BoardArticleEntity articleEntity) throws Exception {

        articleService.insertArticle(articleEntity, memberVO, uuid);

        return "redirect:/board/" + uuid + "/list";
    }

    /**
     * 게시물 수정
     * @param memberVO
     * @param uuid
     * @param articleEntity
     * @return
     * @throws Exception
     */
    @RequestMapping("/{uuid}/update")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD)
    public String update(@AuthenticationPrincipal MemberVO memberVO,
             @PathVariable(name = "uuid") String uuid
            , BoardArticleEntity articleEntity) throws Exception {

        articleService.updateArticle(articleEntity, memberVO);

        return "redirect:/board/" + uuid + "/list";
    }

    /**
     * 게시물 읽기
     * @param memberVO
     * @param uuid
     * @param model
     * @param articleEntity
     * @return
     * @throws Exception
     */
    @RequestMapping("/{uuid}/read")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD)
    public String read(@AuthenticationPrincipal MemberVO memberVO,
           @ModelAttribute @PathVariable(name = "uuid") String uuid,
           Model model,
           BoardArticleEntity articleEntity) throws Exception {

        model.addAttribute("article", articleService.selectArticle(articleEntity, memberVO));

        return "/board/article/read";
    }
    
}
