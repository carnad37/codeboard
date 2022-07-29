package com.hhs.codeboard.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hhs.codeboard.jpa.entity.member.entity.MemberEntity;

public interface MemberDAO extends JpaRepository<MemberEntity, Long> {
	Optional<MemberEntity> findByEmail(String email);
}
