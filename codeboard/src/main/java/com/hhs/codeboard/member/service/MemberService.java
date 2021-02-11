package com.hhs.codeboard.member.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.hhs.codeboard.jpa.entity.MemberVO;

public interface MemberService extends UserDetailsService {
	Integer save(MemberVO memberVO);
}
