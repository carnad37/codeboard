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

@Entity
@Getter
@Setter
@Table(name="code_board_manager")
public class BoardManagerEntity extends DefaultEntity{

	private static final long serialVersionUID = 5738213743471877845L;

	/*
	 * 유저별로 게시판이 생성됨.
	 * 생성한 유저 정보가 곧 게시판 주인. (regUser)
	 * 메뉴분기를 따로안두고(메뉴 => 게시판), 보드 매니저랑 카테고리만 연동.
	 * cate <-> board table에서 얻은 cateList 필요.
	 * cateList로 boardArticle을 검색.
	 */

	@Column
	private String title;
	
	/** 블로그 탭으로 사용여부 */
	@Column(name="blog_f")
	private String blogF;

	/** 사용여부 */
	@Column(name="use_f")
	private String useF;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="boardSeq")
    private Collection<BoardArticleEntity> articleList;
	
}
