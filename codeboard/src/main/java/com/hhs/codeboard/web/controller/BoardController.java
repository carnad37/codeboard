package com.hhs.codeboard.web.controller;

import java.util.List;

import com.hhs.codeboard.jpa.entity.board.BoardArticleEntity;
import com.hhs.codeboard.util.common.SessionUtil;
import com.hhs.codeboard.web.service.member.MemberVO;
import com.hhs.codeboard.jpa.service.ArticleDAO;
import com.hhs.codeboard.jpa.service.BoardDAO;

import com.mysql.cj.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/board")
@Controller
public class BoardController {
    
    @Autowired
    BoardDAO boardDAO;

    @Autowired
    ArticleDAO articleDAO;

    @RequestMapping("/config")
    public String config() {
        //게시판 추가가 가능해야함.
        
        return "/board/config";
    }

    @RequestMapping("/list")
    public String commonList(@AuthenticationPrincipal MemberVO memberVO, Model model, HttpServletRequest request) {
        //regUserSeq 랑 delDate로 조건을 건다.(모든 게시물)
        List<BoardArticleEntity> articleList = articleDAO.findAllByRegUserSeqAndDelDateIsNull(memberVO.getSeq());
        model.addAttribute("articleList", articleList);
        return "/board/list";
    }

    @RequestMapping("/{boardSeq}/list")
    public String list(@PathVariable(name = "boardSeq") String boardSeq
            , Model model) {
        // Optional<BoardManagerEntity> boardManager = boardDAO.findBySeq(Integer.parseInt(boardSeq));
        // model.addAttribute("articleList", articleList);
        return "/board/list";
    }

    @RequestMapping("/read")
    public String read() {
        
        return "/board/read";
    }

    @RequestMapping("/write")
    public String write() {
        
        return "/board/write";
    }

    
}
