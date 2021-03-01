package com.hhs.codeboard.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.hhs.codeboard.enumeration.UserTypeEnum;
import com.hhs.codeboard.jpa.entity.MemberEntity;
import com.hhs.codeboard.jpa.entity.MemberVO;
import com.hhs.codeboard.jpa.service.MemberDAO;
import com.hhs.codeboard.member.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO memberDAO;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		Optional<MemberEntity> memberEntityWrapper = memberDAO.findByEmail(memberId);
		MemberEntity memberVO = memberEntityWrapper.orElseThrow(() -> new UsernameNotFoundException("가입정보가 없습니다"));
		//권한 리스트
		List<GrantedAuthority> authorities = new ArrayList<>();

		if (UserTypeEnum.ADMIN.matchTypeCode(memberVO.getUserType())) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
		} else if (UserTypeEnum.NORMAL.matchTypeCode(memberVO.getUserType())) {
			authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
		} else if (UserTypeEnum.WAIT.matchTypeCode(memberVO.getUserType())) {
			throw new UsernameNotFoundException("가입승인 대기중입니다");
		}
		
		return new MemberVO(memberVO, authorities);
	}

	@Override
	public String insertUser(MemberEntity memberVO) {
        memberVO.setRegDate(LocalDateTime.now());
		memberVO.setUserType(UserTypeEnum.NORMAL.getTypeCode());
        // 비밀번호 암호화
        memberVO.setPassword(passwordEncoder.encode(memberVO.getPassword()));
        return memberDAO.save(memberVO).getEmail();
	}

}
