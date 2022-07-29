package com.hhs.codeboard.jpa.entity.board.entity;

import javax.persistence.*;

import com.hhs.codeboard.config.common.StaticProperty;
import com.hhs.codeboard.jpa.entity.common.entity.DefaultEntity;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@Entity
@Table(name="code_board_article")
@TableGenerator(name = "tableGenerator", table = StaticProperty.SEQUENCE_TABLE_NAME)
public class BoardArticleEntity extends DefaultEntity{

    private static final long serialVersionUID = 536546113468443744L;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String summary;

    @Column(name="display_f")
    private String displayF;
    
    @Column
    private Integer boardSeq;

    @Column
    @Nullable
    private Integer categorySeq;

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name="articleSeq")
//    private Collection<BoardCateArticleEntity> categorys;

}
