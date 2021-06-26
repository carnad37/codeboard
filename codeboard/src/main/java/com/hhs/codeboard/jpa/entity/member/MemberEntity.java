package com.hhs.codeboard.jpa.entity.member;

import java.util.Collection;

import javax.persistence.*;

import com.hhs.codeboard.jpa.entity.common.DefaultDateEntity;
import com.hhs.codeboard.jpa.entity.menu.MenuEntity;
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

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="regUserSeq")
	private Collection<MenuEntity> menuList;

}