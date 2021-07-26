//package com.hhs.codeboard.jpa.entity.board;
//
//import javax.persistence.*;
//
//import com.hhs.codeboard.jpa.entity.common.DefaultEntity;
//
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//@Entity
//@Table(name="code_category_item")
//@SequenceGenerator(name = "seqGenerator", sequenceName = "seqCategoryItem")
//public class BoardCateItemEntity extends DefaultEntity{
//
//    private static final long serialVersionUID = 4507117424141720882L;
//
//    @Column
//    private String title;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="cateSeq")
//    private BoardCategoryEntity boardCategory;
//
//}
