package com.hhs.codeboard.web.controller;


import com.hhs.codeboard.jpa.entity.board.entity.BoardCategoryEntity;
import com.hhs.codeboard.web.service.member.MemberDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {


    @RequestMapping("/getCategory")
    public ResponseEntity<List<BoardCategoryEntity>> getCategory(
            @AuthenticationPrincipal MemberDto memberDto
            , @RequestParam Integer boardSeq
        ) {

        BoardCategoryEntity category = new BoardCategoryEntity();


        return ResponseEntity.ok(null);

    }


}
