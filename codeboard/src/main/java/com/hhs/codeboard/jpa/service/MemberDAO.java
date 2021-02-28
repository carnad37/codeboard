package com.hhs.codeboard.jpa.service;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hhs.codeboard.jpa.entity.MemberVO;

public interface MemberDAO extends JpaRepository<MemberVO, Long> {
	Optional<MemberVO> findByEmail(String email);
}
