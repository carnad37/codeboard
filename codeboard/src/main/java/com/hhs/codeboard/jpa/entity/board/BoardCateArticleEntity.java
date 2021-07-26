//package com.hhs.codeboard.jpa.entity.board;
//
//import java.io.Serializable;
//
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import com.hhs.codeboard.jpa.entity.board.BoardCateItemEntity;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//@Entity
//@Table(name="connect_category_article")
//public class BoardCateArticleEntity implements Serializable{
//
//    private static final long serialVersionUID = -701957800132578608L;
//
//    @Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Integer seq;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "cateItemSeq")
//    private BoardCateItemEntity category;
//
//
//}