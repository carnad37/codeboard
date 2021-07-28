package com.hhs.codeboard.jpa.entity.board;

import java.util.Collection;

import javax.persistence.*;

import com.hhs.codeboard.jpa.entity.common.DefaultEntity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
@Entity
@Table(name="code_board_article")
//@SequenceGenerator(name = "seqGenerator", sequenceName = "seqBoardArticle")
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
