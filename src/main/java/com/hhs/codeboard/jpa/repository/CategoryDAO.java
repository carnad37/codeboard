package com.hhs.codeboard.jpa.repository;

import com.hhs.codeboard.jpa.entity.board.entity.BoardCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryDAO extends JpaRepository<BoardCategoryEntity, Long> {

    List<BoardCategoryEntity> findAllByBoardSeq(int boardSeq);

    Optional<BoardCategoryEntity> findBySeqAndDelDateIsNotNull(int seq);

}
