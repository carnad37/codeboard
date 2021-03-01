package com.hhs.codeboard.jpa.service;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hhs.codeboard.jpa.entity.MemberEntity;

public interface MemberDAO extends JpaRepository<MemberEntity, Long> {
	Optional<MemberEntity> findByEmail(String email);
}
