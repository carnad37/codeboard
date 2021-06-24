package com.hhs.codeboard.jpa.entity;

import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import com.hhs.codeboard.common.service.DefaultDateEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "member")
@SequenceGenerator(name="seqMemberGenerator", sequenceName = "seqMember", initialValue = 1, allocationSize = 1)
public class MemberEntity extends DefaultDateEntity {

	private static final long serialVersionUID = -3781361636538961523L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seqMemberGenerator")
	private Integer seq;
	
	@Column
	private String email;

	@Column
	private String password;
	
	@Column
	private String nickName;
	
	@Column
	private String userType;

	@Column
	private Integer modUserSeq;

//	@OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name="regUserSeq")
//	private Collection<MenuEntity> menuList;

}