package com.hhs.codeboard.common.service;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.hhs.codeboard.jpa.entity.MemberVO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class DefaultVO extends DefaultDateVO {

	private static final long serialVersionUID = 3989969904364677147L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer seq;

	@ManyToOne
	@JoinColumn(name="regUserSeq")
	private MemberVO regUser;

	@ManyToOne
	@JoinColumn(name="modUserSeq")
	private MemberVO modUser;

}
