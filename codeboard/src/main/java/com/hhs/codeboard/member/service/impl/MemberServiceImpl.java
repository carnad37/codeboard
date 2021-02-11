package com.hhs.codeboard.member.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hhs.codeboard.jpa.entity.MemberVO;
import com.hhs.codeboard.member.service.MemberService;

public class MemberServiceImpl implements MemberService {

	@Res
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer save(MemberVO memberVO) {
		// TODO Auto-generated method stub
		return null;
	}

}
