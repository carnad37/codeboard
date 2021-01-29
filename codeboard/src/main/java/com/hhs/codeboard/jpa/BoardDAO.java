package com.hhs.codeboard.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hhs.codeboard.board.service.BoardManagerVO;

@Repository
public interface BoardDAO extends JpaRepository<BoardManagerVO, Long> {

}
