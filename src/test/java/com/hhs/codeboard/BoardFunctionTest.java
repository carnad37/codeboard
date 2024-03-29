package com.hhs.codeboard;

import com.hhs.codeboard.jpa.entity.board.entity.BoardArticleEntity;
import com.hhs.codeboard.jpa.entity.menu.entity.MenuEntity;
import com.hhs.codeboard.jpa.repository.MenuDAO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@SpringBootTest
public class BoardFunctionTest {

    Logger logger = LoggerFactory.getLogger(BoardFunctionTest.class);

    @Autowired
    MenuDAO menuDAO;

    @Test
    @Transactional
    void getArticleTest() throws Exception {
        String uuid = "uuid";
        Integer regUserSeq = 1;

        //페이지 처리및 검색어, 검색조건의 경우 분리조건처리


        Optional<MenuEntity> menu = menuDAO.findByUuidAndRegUserSeqAndDelDateIsNull(uuid, regUserSeq);
        MenuEntity result = menu.get();

        assertNotNull(result);
        logger.info("menu title : " + result.getTitle());

        Collection<BoardArticleEntity> articleList = result.getArticleList();
        assertNotEquals(articleList.size(), 0);
        articleList.forEach(
            (unitEntity) -> logger.info("article title : " + unitEntity.getTitle())
        );

    }




}
