package com.hhs.codeboard.jpa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.hhs.codeboard.jpa.entity.board.BoardArticleEntity;

@Repository
public interface ArticleDAO extends JpaRepository<BoardArticleEntity, Long> {

    List<BoardArticleEntity> findAllByRegUserSeqAndDelDateIsNull(Integer regUserSeq);

    Optional<BoardArticleEntity> findBySeqAndRegUserSeqAndDelDateIsNull(Integer seq, Integer regUserSeq);

//    @Query(value = "select b FROM BoardArticleEntity b WHERE ")
//    Page<BoardArticleEntity> selectArticleList(@Param("searchVO") BoardArticleEntity searchVO, Pageable pageable);

}
