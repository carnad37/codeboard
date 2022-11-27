package com.hhs.codeboard.jpa.entity.board.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class BoardCategoryDto {

    private Integer seq;

    private String title;

    private String useF;

    private Integer boardSeq;

}
