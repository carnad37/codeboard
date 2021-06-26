package com.hhs.codeboard.web.service.member;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.hhs.codeboard.jpa.entity.member.MemberEntity;

public interface MemberService extends UserDetailsService {
	String insertUser(MemberEntity memberVO);
}
