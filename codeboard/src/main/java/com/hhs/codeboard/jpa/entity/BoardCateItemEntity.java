package com.hhs.codeboard.jpa.entity;

import javax.persistence.*;

import com.hhs.codeboard.common.service.DefaultEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="code_category_item")
public class BoardCateItemEntity extends DefaultEntity{

    private static final long serialVersionUID = 4507117424141720882L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer seq;

    @Column
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cateSeq")
    private BoardCategoryEntity boardCategory;


}
