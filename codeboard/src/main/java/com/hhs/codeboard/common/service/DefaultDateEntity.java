package com.hhs.codeboard.common.service;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class DefaultDateEntity implements Serializable {

	private static final long serialVersionUID = -7080461491952563362L;

	/**
	 * entity정보
	 */
	@Column
	private LocalDateTime regDate;
	
	@Column
	private LocalDateTime modDate;
	
	@Column
	private LocalDateTime delDate;

}
