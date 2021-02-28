package com.hhs.codeboard.common.service;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DefaultDateVO {

	/**
	 * entity정보
	 */
	@Column
	private LocalDateTime regDate;
	
	@Column
	private LocalDateTime modDate;
	
	@Column
	private LocalDateTime delDate;

	public LocalDateTime getRegDate() {
		return regDate;
	}
	public void setRegDate(LocalDateTime regDate) {
		this.regDate = regDate;
	}
	public LocalDateTime getModDate() {
		return modDate;
	}
	public void setModDate(LocalDateTime modDate) {
		this.modDate = modDate;
	}
	public LocalDateTime getDelDate() {
		return delDate;
	}
	public void setDelDate(LocalDateTime delDate) {
		this.delDate = delDate;
	}	
	
}
