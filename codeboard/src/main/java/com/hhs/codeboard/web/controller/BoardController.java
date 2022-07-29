package com.hhs.codeboard.web.controller;

import com.hhs.codeboard.config.anno.AspectMenuActive;
import com.hhs.codeboard.config.common.LoggerController;
import com.hhs.codeboard.enumeration.MenuSeqEnum;
import com.hhs.codeboard.enumeration.MenuTypeEnum;
import com.hhs.codeboard.jpa.entity.board.dto.BoardArticleDto;
import com.hhs.codeboard.jpa.entity.board.entity.BoardArticleEntity;
import com.hhs.codeboard.jpa.entity.menu.entity.MenuEntity;
import com.hhs.codeboard.util.common.SessionUtil;
import com.hhs.codeboard.util.pagination.BoardPagination;
import com.hhs.codeboard.web.service.board.BoardArticleService;
import com.hhs.codeboard.web.service.common.SearchDto;
import com.hhs.codeboard.web.service.member.MemberDto;

import com.hhs.codeboard.web.service.menu.MenuService;
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

    private final BoardArticleService articleService;
    private final MenuService menuService;

    /**
     * BoardController의 경우 uuid는 @ModelAttribute로 담겨서 전달된다.
     * 해당 게시판은 menu랑 공유된다.
     * 해당 게시판(메뉴)는 메뉴위치 변경 UI에서 삭제가 되지 않음.
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
     * @param memberDto
     * @param MenuEntity
     * @param model
     * @return
     */
    @RequestMapping("/list")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD_CONFIG, menuTitle = "게시판 목록")
    public String managerList(@AuthenticationPrincipal MemberDto memberDto
              , @ModelAttribute SearchDto searchVO
              , @ModelAttribute MenuEntity MenuEntity, Model model) {
        //regUserSeq 랑 delDate로 조건을 건다.(모든 게시물)
//        List<BoardArticleEntity> articleList = articleDAO.findAllByRegUserSeqAndUuidAndDelDateIsNull(memberVO.getSeq(), MenuEntity.getSeq());
//        model.addAttribute("articleList", articleList);
        model.addAttribute("boardList", menuService.selectMenuList(memberDto.getSeq(), MenuTypeEnum.BOARD));
        return "board/mgr/list";
    }

    /**
     * 게시판 내역 상세
     * @return
     */
    @RequestMapping("/read")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD_CONFIG, menuTitle = "게시판 관리")
    public String managerRead() {
        return "/board/mgr/read";
    }

    /**
     * 게시판 입력 페이지
     * @param memberDto
     * @param model
     * @param menuEntity
     * @return
     * @throws Exception
     */
    @RequestMapping("/write")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD_CONFIG, menuTitle = "게시판 관리")
    public String managerWrite(@AuthenticationPrincipal MemberDto memberDto
            , Model model, MenuEntity menuEntity
    ) throws Exception {
        if (menuEntity.getSeq() > 0) {
            model.addAttribute("board", menuService.selectMenu(memberDto.getSeq(), menuEntity.getSeq()));
        }
        return "board/mgr/write";
    }

    @RequestMapping("/delete")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD_CONFIG, menuTitle = "게시판 관리")
    public String managerDelete(@AuthenticationPrincipal MemberDto memberDto
            , HttpServletRequest request
            , MenuEntity menuEntity
    ) throws Exception {
        menuService.deleteMenu(menuEntity, memberDto, MenuTypeEnum.BOARD);
        SessionUtil.setSession(request, "menuList", menuService.initMenuList(memberDto, request));
        return "redirect:/board/list";
    }

    /**
     * 게시판 입력
     * @param memberDto
     * @param menuEntity
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/insert")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD_CONFIG)
    public String managerInsert(@AuthenticationPrincipal MemberDto memberDto
            , @ModelAttribute MenuEntity menuEntity
            , HttpServletRequest request
    ) throws Exception {
        menuEntity.setParentSeq(MenuSeqEnum.ROOT_MENU.getMenuSeq());
        menuService.insertMenu(menuEntity, memberDto, MenuTypeEnum.BOARD);
        SessionUtil.setSession(request, "menuList", menuService.initMenuList(memberDto, request));
        return "redirect:/board/list";
    }

    /**
     * 게시판 수정
     * @param memberDto
     * @param searchVO
     * @param request
     * @param menuEntity
     * @return
     * @throws Exception
     */
    @RequestMapping("/update")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD_CONFIG)
    public String managerUpdate(@AuthenticationPrincipal MemberDto memberDto
            , @ModelAttribute SearchDto searchVO
            , HttpServletRequest request
            , MenuEntity menuEntity
    ) throws Exception {
        menuService.updateMenu(menuEntity, memberDto, MenuTypeEnum.BOARD);
        SessionUtil.setSession(request, "menuList", menuService.initMenuList(memberDto, request));
        return "redirect:/board/list";
    }

    /**
     * 게시물 리스트
     * @param memberDto
     * @param uuid
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/{uuid}/list")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD, menuTitle = "게시물 관리")
    @Transactional
    public String list(@AuthenticationPrincipal MemberDto memberDto
           , @ModelAttribute @PathVariable(name = "uuid") String uuid
           , @ModelAttribute SearchDto searchVO
           , Model model) throws Exception {
        //해당 게시판의 Entity
        BoardPagination pagination = new BoardPagination(10, 10, searchVO.getPageIndex());
        model.addAttribute("articleList", articleService.selectArticleList(memberDto, uuid, searchVO, pagination));
        model.addAttribute("pagination", pagination);
        return "board/article/list";
    }

    /**
     * 게시물 입력 페이지
     * @param memberDto
     * @param uuid
     * @param model
     * @param articleEntity
     * @return
     * @throws Exception
     */
    @RequestMapping("/{uuid}/write")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD, menuTitle = "게시물 관리")
    public String write(@AuthenticationPrincipal MemberDto memberDto,
            @ModelAttribute @PathVariable(name = "uuid") String uuid
            , @ModelAttribute SearchDto searchVO
            , Model model
            , @ModelAttribute BoardArticleDto articleDto) throws Exception {
        if (articleDto.getSeq() > 0) {
            model.addAttribute("article", articleService.selectArticle(articleDto, memberDto));
        }
        return "board/article/write";
    }

    /**
     * 게시물 입력
     * @param memberDto
     * @param uuid
     * @param articleEntity
     * @return
     * @throws Exception
     */
    @RequestMapping("/{uuid}/insert")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD)
    public String insert(@AuthenticationPrincipal MemberDto memberDto,
            @PathVariable(name = "uuid") String uuid
            , BoardArticleDto articleDto) throws Exception {
        articleService.insertArticle(articleDto, memberDto, uuid);

        return "redirect:/board/" + uuid + "/list";
    }

    /**
     * 게시물 수정
     * @param memberDto
     * @param uuid
     * @param articleDto
     * @return
     * @throws Exception
     */
    @RequestMapping("/{uuid}/update")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD)
    public String update(@AuthenticationPrincipal MemberDto memberDto,
             @PathVariable(name = "uuid") String uuid
            , BoardArticleDto articleDto) throws Exception {

        articleService.updateArticle(articleDto, memberDto);

        return "redirect:/board/" + uuid + "/list";
    }

    /**
     * 게시물 읽기
     * @param memberDto
     * @param uuid
     * @param model
     * @param articleDto
     * @return
     * @throws Exception
     */
    @RequestMapping("/{uuid}/read")
    @AspectMenuActive(menuType = MenuTypeEnum.BOARD, menuTitle = "게시물 상세")
    public String read(@AuthenticationPrincipal MemberDto memberDto
           , @ModelAttribute @PathVariable(name = "uuid") String uuid
           , @ModelAttribute SearchDto searchVO
           , Model model
           , BoardArticleDto articleDto) throws Exception {

        model.addAttribute("article", articleService.selectArticle(articleDto, memberDto));

        return "board/article/read";
    }
    
}
