package com.hhs.codeboard.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.hhs.codeboard.jpa.entity.MemberVO;
import com.hhs.codeboard.jpa.service.MemberDAO;
import com.hhs.codeboard.member.service.MemberService;

public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO memberDAO;

	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		Optional<MemberVO> memberEntityWrapper = memberDAO.findByMemberId(memberId);
		MemberVO memberEntity = memberEntityWrapper.orElse(null);

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));

		return new User(memberEntity.getMemberId(), memberEntity.getPassword(), authorities);
	}

	@Override
	public String save(MemberVO memberVO) {
        memberVO.setRegDate(LocalDateTime.now());

        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberVO.setPassword(passwordEncoder.encode(memberVO.getPassword()));
        return memberDAO.save(memberVO).getMemberId();
	}

}
