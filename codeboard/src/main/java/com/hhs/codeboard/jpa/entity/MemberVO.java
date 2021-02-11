package com.hhs.codeboard.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.hhs.codeboard.common.service.DefaultDateVO;

@Entity
public class MemberVO extends DefaultDateVO {

	@Id
	private String memberId;
	
	@Column
	private String password;
	
	@Column
	private String nickName;
	
	@Column
	private String email;
	
	@Column
	private String userType;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
}
