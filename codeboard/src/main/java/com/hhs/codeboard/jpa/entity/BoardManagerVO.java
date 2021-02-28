package com.hhs.codeboard.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.hhs.codeboard.common.service.DefaultVO;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="code_board_manager")
public class BoardManagerVO extends DefaultVO{

	private static final long serialVersionUID = 5738213743471877845L;

	@Column
	private String title;
	
	/**
	 * 	Enum값으로 대체할 예정
	 */
	@Column
	private String type;

	
}
