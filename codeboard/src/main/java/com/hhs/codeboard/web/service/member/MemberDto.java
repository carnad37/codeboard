package com.hhs.codeboard.web.service.member;

import java.util.*;

import com.hhs.codeboard.enumeration.UserTypeEnum;
import com.hhs.codeboard.jpa.entity.member.entity.MemberEntity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class MemberDto extends User{

	public MemberDto(String username, String password, Collection<GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	public MemberDto(MemberEntity memberVO, Collection<GrantedAuthority> authorities) {
		super(memberVO.getEmail(), memberVO.getPassword(), !UserTypeEnum.WAIT.getTypeCode().equals(memberVO.getUserType()),
			memberVO.getDelDate() == null, memberVO.getDelDate() == null, memberVO.getDelDate() == null, authorities);
		this.seq = memberVO.getSeq();
		this.nickName = memberVO.getNickName();
		this.delFlag = memberVO.getDelDate() == null;
	}

	public MemberDto(String username, String password, boolean enabled,
					 boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	private static final long serialVersionUID = 3262283502601992796L;

	private Integer seq;
	private String nickName;
	private Boolean delFlag;

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return super.getAuthorities();
	}
	
	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public String getUsername() {
		return super.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.delFlag;
	}

	@Override
	public boolean isAccountNonLocked() {
		return super.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.delFlag;
	}

	@Override
	public boolean isEnabled() {
		return this.delFlag;
	}

	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
