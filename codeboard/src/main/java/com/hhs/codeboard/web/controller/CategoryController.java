package com.hhs.codeboard.web.controller;


import com.hhs.codeboard.jpa.entity.board.BoardCategoryEntity;
import com.hhs.codeboard.jpa.service.CategoryDAO;
import com.hhs.codeboard.web.service.member.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {


    @RequestMapping("/getCategory")
    public ResponseEntity<List<BoardCategoryEntity>> getCategory(
            @AuthenticationPrincipal MemberVO memberVO
            , @RequestParam Integer boardSeq
        ) {

        BoardCategoryEntity category = new BoardCategoryEntity();


        return ResponseEntity.ok(null);

    }


}
