package com.hhs.codeboard.jpa.entity;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.hhs.codeboard.common.service.DefaultEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="code_category")
public class BoardCategoryEntity extends DefaultEntity {

    private static final long serialVersionUID = -6177068167629590168L;

    @Column
    private String title;

    @Column
    private String useF;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="boardSeq")
    private BoardManagerEntity boardManager;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="categorySeq")
    private Collection<BoardCateArticleEntity> categorys;
   
    
}
