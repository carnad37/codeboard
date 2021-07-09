package com.hhs.codeboard.web.service.member;

import java.util.*;

import com.hhs.codeboard.enumeration.UserTypeEnum;
import com.hhs.codeboard.jpa.entity.member.MemberEntity;
import com.hhs.codeboard.jpa.entity.menu.MenuEntity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class MemberVO extends User {

	public  MemberVO(String username, String password, Collection<GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	public  MemberVO(MemberEntity memberVO, Collection<GrantedAuthority> authorities) {
		super(memberVO.getEmail(), memberVO.getPassword(), !UserTypeEnum.WAIT.getTypeCode().equals(memberVO.getUserType()),
			memberVO.getDelDate() == null, memberVO.getDelDate() == null, memberVO.getDelDate() == null, authorities);
		this.seq = memberVO.getSeq();
		memberVO.getMenuList().forEach(
			(MenuEntity entity) -> menuList.add(new MenuEntity())
		);
	}

	public  MemberVO(String username, String password, boolean enabled,
	 boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	private static final long serialVersionUID = 3262283502601992796L;

	private Integer seq;
	private List<MenuEntity> menuList = new ArrayList<>();
	private Map<String, MenuEntity> menuMap = new HashMap<>();

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
		return super.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return super.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return super.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return isEnabled();
	}

	public List<MenuEntity> getMenuList() {
		return this.menuList;
	}

	public void setMenuList(List<MenuEntity> menuList) {
		this.menuList = menuList;
	}

	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

}
