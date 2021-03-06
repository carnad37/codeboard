package com.hhs.codeboard.jpa.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.hhs.codeboard.jpa.entity.board.BoardArticleEntity;

@Repository
public interface ArticleDAO extends JpaRepository<BoardArticleEntity, Long> {

    List<BoardArticleEntity> findAllByRegUserSeqAndDelDateIsNull(Integer regUserSeq);

    Optional<BoardArticleEntity> findBySeqAndRegUserSeqAndDelDateIsNull(Integer seq, Integer regUserSeq);


}
