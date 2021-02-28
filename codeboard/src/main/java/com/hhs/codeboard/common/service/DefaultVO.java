package com.hhs.codeboard.common.service;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.hhs.codeboard.jpa.entity.MemberVO;

@MappedSuperclass
public abstract class DefaultVO extends DefaultDateVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer seq;

	@ManyToOne
	@JoinColumn(name="regUserSeq")
	private MemberVO regUser;

	@ManyToOne
	@JoinColumn(name="modUserSeq")
	private MemberVO modUser;

	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public MemberVO getRegUser() {
		return this.regUser;
	}

	public void setRegUser(MemberVO regUser) {
		this.regUser = regUser;
	}

	public MemberVO getModUser() {
		return this.modUser;
	}

	public void setModUser(MemberVO modUser) {
		this.modUser = modUser;
	}

	
}
