package com.hhs.codeboard.jpa.entity.board;
import java.util.Collection;

import javax.persistence.*;

import com.hhs.codeboard.jpa.entity.common.DefaultEntity;

import com.hhs.codeboard.jpa.entity.menu.MenuEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="code_category")
public class BoardCategoryEntity extends DefaultEntity {

    private static final long serialVersionUID = -6177068167629590168L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer seq;

    @Column
    private String title;

    @Column
    private String useF;

    @Column
    private Integer boardSeq;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="menuSeq")
//    private MenuEntity menu;
//
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name="categorySeq")
//    private Collection<BoardCateArticleEntity> categorys;
    
}
