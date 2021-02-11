package com.hhs.codeboard.jpa.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hhs.codeboard.jpa.entity.BoardManagerVO;

@Repository
public interface BoardDAO extends JpaRepository<BoardManagerVO, Long> {

}
