package com.hhs.codeboard.member.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.hhs.codeboard.jpa.entity.MemberEntity;

public interface MemberService extends UserDetailsService {
	String insertUser(MemberEntity memberVO);
}
