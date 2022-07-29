package com.hhs.codeboard.jpa.entity.board.dto;

import com.hhs.codeboard.jpa.entity.common.dto.DefaultDto;
import lombok.Data;


@Data
public class BoardArticleDto extends DefaultDto {

    private String title;

    private String content;

    private String summary;

    private String displayF;

    private Integer boardSeq;

    private Integer categorySeq;

}
