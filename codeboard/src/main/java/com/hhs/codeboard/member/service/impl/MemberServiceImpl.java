package com.hhs.codeboard.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.hhs.codeboard.jpa.entity.MemberVO;
import com.hhs.codeboard.jpa.service.MemberDAO;
import com.hhs.codeboard.member.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO memberDAO;

	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		Optional<MemberVO> memberEntityWrapper = memberDAO.findByMemberId(memberId);
		MemberVO memberEntity = memberEntityWrapper.orElseThrow(() -> new UsernameNotFoundException("not found data"));
		//권한 리스트
		List<GrantedAuthority> authorities = new ArrayList<>();

		if ("A".equals(memberEntity.getUserType())) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
		} else if ("N".equals(memberEntity.getUserType())) {
			authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
		}

		return new User(memberEntity.getMemberId(), memberEntity.getPassword(), authorities);
	}

	@Override
	public String insertUser(MemberVO memberVO) {
        memberVO.setRegDate(LocalDateTime.now());

        // 비밀번호 암호화
		// PK값은 memberId로 쓰기때문에, return 값은 String(memberId)로 한다.
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberVO.setPassword(passwordEncoder.encode(memberVO.getPassword()));
        return memberDAO.save(memberVO).getMemberId();
	}

}
