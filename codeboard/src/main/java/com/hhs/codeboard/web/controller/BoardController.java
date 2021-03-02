package com.hhs.codeboard.web.controller;

import java.util.ArrayList;
import java.util.List;

import com.hhs.codeboard.jpa.entity.BoardArticleEntity;
import com.hhs.codeboard.jpa.entity.BoardManagerEntity;
import com.hhs.codeboard.jpa.service.BoardDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/board")
@Controller
public class BoardController {
    
    @Autowired
    BoardDAO boardDAO;

    @RequestMapping("/config")
    public String config() {
        //게시판 추가가 가능해야함.
        return "/board/config";
    }

    @RequestMapping("/{boardSeq}/list")
    public String list(@PathVariable(name = "boardSeq") String boardSeq
            , Model model) {
        BoardManagerEntity boardManager = boardDAO.findBySeq(Integer.parseInt(boardSeq));
        List<BoardArticleEntity> articleList = new ArrayList<>(boardManager.getArticleList());
        model.addAttribute("articleList", articleList);
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
