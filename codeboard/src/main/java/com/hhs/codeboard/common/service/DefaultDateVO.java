package com.hhs.codeboard.common.service;

import java.time.LocalDateTime;

import javax.persistence.Column;

public class DefaultDateVO {

	/**
	 * user 정보
	 */
	@Column
	private Integer regUserSeq;
	
	@Column
	private String regUserName;
	
	@Column
	private Integer modUserSeq;
	
	@Column
	private String modUserName;
	
	/**
	 * entity정보
	 */
	@Column
	private LocalDateTime regDate;
	
	@Column
	private LocalDateTime modDate;
	
	@Column
	private LocalDateTime delDate;
	
	public Integer getRegUserSeq() {
		return regUserSeq;
	}
	public void setRegUserSeq(Integer regUserSeq) {
		this.regUserSeq = regUserSeq;
	}
	public String getRegUserName() {
		return regUserName;
	}
	public void setRegUserName(String regUserName) {
		this.regUserName = regUserName;
	}
	public Integer getModUserSeq() {
		return modUserSeq;
	}
	public void setModUserSeq(Integer modUserSeq) {
		this.modUserSeq = modUserSeq;
	}
	public String getModUserName() {
		return modUserName;
	}
	public void setModUserName(String modUserName) {
		this.modUserName = modUserName;
	}
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
