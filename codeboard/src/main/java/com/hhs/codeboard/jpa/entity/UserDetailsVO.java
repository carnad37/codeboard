package com.hhs.codeboard.jpa.entity;

import java.util.Collection;
import java.util.List;

import com.hhs.codeboard.enumeration.UserTypeEnum;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsVO implements UserDetails{

	private static final long serialVersionUID = 3262283502601992796L;

	private List<GrantedAuthority> authorities;
	private MemberVO memberVO;

	public UserDetailsVO() {
		super();
		this.memberVO = new MemberVO();
	}

	public UserDetailsVO(MemberVO memberVO) {
		super();
		this.memberVO = memberVO;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}
	
	@Override
	public String getPassword() {
		return this.memberVO.getPassword();
	}

	@Override
	public String getUsername() {
		return this.memberVO.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.memberVO.getDelDate() == null;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.memberVO.getDelDate() == null;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.memberVO.getDelDate() == null;
	}

	@Override
	public boolean isEnabled() {
		return !UserTypeEnum.WAIT.getTypeCode().equals(memberVO.getUserType());
	}
	
	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public MemberVO getMemberVO() {
		return this.memberVO;
	}

	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}
	
}
