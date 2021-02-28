package com.hhs.codeboard.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hhs.codeboard.common.service.DefaultDateVO;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "member")
public class MemberVO extends DefaultDateVO {

	private static final long serialVersionUID = -3781361636538961523L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String seq;
	
	@Column
	private String email;

	@Column
	private String password;
	
	@Column
	private String nickName;
	
	@Column
	private String userType;

	@Column
	private Integer regUserSeq;

}
