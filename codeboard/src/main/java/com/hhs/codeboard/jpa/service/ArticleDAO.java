package com.hhs.codeboard.jpa.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.hhs.codeboard.jpa.entity.BoardArticleEntity;

@Repository
public interface ArticleDAO extends JpaRepository<BoardArticleEntity, Long> {
    List<BoardArticleEntity> findAllByRegUserSeqAndDelDateIsNotNull(Integer regUserSeq);

    List<BoardArticleEntity> findAllByRegUserSeqAndBoardSeqAndDelDateIsNotNull(Integer regUserSeq, Integer boardSeq);
}
