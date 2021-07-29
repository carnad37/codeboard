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

    /*
        TODO :: 차후 jpql로 대체함
     */

    List<BoardArticleEntity> findAllByRegUserSeqAndDelDateIsNull(Integer regUserSeq);
    Optional<BoardArticleEntity> findBySeqAndRegUserSeqAndDelDateIsNull(Integer seq, Integer regUserSeq);

    //카운트 함수
    Integer countAllByBoardSeqAndDelDateIsNull(Integer boardSeq);
    Integer countAllByBoardSeqAndTitleContainsAndDelDateIsNull(Integer boardSeq, String title);
    Integer countAllByBoardSeqAndContentContainsAndDelDateIsNull(Integer boardSeq, String content);
    Integer countAllByTitleContainsOrContentContainsAndBoardSeqAndDelDateIsNull(String title, String content, Integer BoardSeq);

//    @Query(value = "select b FROM BoardArticleEntity b WHERE ")
    //리스트 함수
    List<BoardArticleEntity> findAllByBoardSeqAndDelDateIsNull(Integer boardSeq, Pageable pageable);
    List<BoardArticleEntity> findAllByBoardSeqAndTitleContainsAndDelDateIsNull(Integer boardSeq, String title, Pageable pageable);
    List<BoardArticleEntity> findAllByBoardSeqAndContentContainsAndDelDateIsNull(Integer boardSeq, String content, Pageable pageable);
    List<BoardArticleEntity> findAllByTitleContainsOrContentContainsAndBoardSeqAndDelDateIsNull(String title, String content, Integer BoardSeq,Pageable pageable);

    //카테고리 값으로 검색
    List<BoardArticleEntity> findAllByCategorySeq(Integer categorySeq);

}
