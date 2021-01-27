package com.hhs.codeboard.menu.service;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.hhs.codeboard.common.service.DefaultVO;

@Entity
@Table(name="code_board_menu")
public class MenuVO extends DefaultVO{

	@Column
	private String title;
	
	@Column
	private Integer parentSeq;
	
	@Column
	private String type;

	@Column
	private Integer BoardSeq;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getParentSeq() {
		return parentSeq;
	}

	public void setParentSeq(Integer parentSeq) {
		this.parentSeq = parentSeq;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getBoardSeq() {
		return BoardSeq;
	}

	public void setBoardSeq(Integer boardSeq) {
		BoardSeq = boardSeq;
	}
	
}
