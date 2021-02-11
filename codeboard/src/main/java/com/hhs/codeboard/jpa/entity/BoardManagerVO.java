package com.hhs.codeboard.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.hhs.codeboard.common.service.DefaultVO;

@Entity
public class BoardManagerVO extends DefaultVO{

	@Column
	private String title;
	
	/**
	 * 	Enum값으로 대체할 예정
	 */
	@Column
	private String type;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
