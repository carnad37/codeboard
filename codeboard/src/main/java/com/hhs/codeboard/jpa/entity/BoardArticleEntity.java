package com.hhs.codeboard.jpa.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.hhs.codeboard.common.service.DefaultEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="code_board_article")
public class BoardArticleEntity extends DefaultEntity{

    private static final long serialVersionUID = 536546113468443744L;
    
    @Column
    private String title;

    @Column
    private String content;

    @Column(name="display_f")
    private String displayF;
    
    @Column
    private Integer boardSeq;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="articleSeq")
    private Collection<BoardCateArticleEntity> categorys;

}
