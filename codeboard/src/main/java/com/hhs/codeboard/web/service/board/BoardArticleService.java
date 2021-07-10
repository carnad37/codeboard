package com.hhs.codeboard.web.service.board;

import com.hhs.codeboard.jpa.entity.board.BoardArticleEntity;
import com.hhs.codeboard.jpa.entity.menu.MenuEntity;
import com.hhs.codeboard.jpa.service.ArticleDAO;
import com.hhs.codeboard.jpa.service.MenuDAO;
import com.hhs.codeboard.web.service.member.MemberVO;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class BoardArticleService {

    private final ArticleDAO articleDAO;
    private final MenuDAO menuDAO;

    @Autowired
    public BoardArticleService(ArticleDAO articleDAO, MenuDAO menuDAO) {
        this.articleDAO = articleDAO;
        this.menuDAO = menuDAO;
    }

    public List<BoardArticleEntity> selectArticleList(MemberVO memberVO, String uuid) throws Exception {
        MenuEntity menu = menuDAO.findByUuidAndRegUserSeqAndDelDateIsNull(uuid, memberVO.getSeq()).orElse(new MenuEntity());
        return menu.getArticleList().stream().collect(Collectors.toList());
    }

    public BoardArticleEntity selectArticle(BoardArticleEntity articleEntity, MemberVO memberVO) throws Exception{
        //수정페이지일경우 seq값이 주어짐. 해당 글이 해당유저의 글인지 확인.
        BoardArticleEntity targetEntity = articleDAO.findBySeqAndRegUserSeqAndDelDateIsNull(articleEntity.getSeq(), memberVO.getSeq())
                .orElseThrow(() -> new Exception("잘못된 접근입니다"));
        return targetEntity;
    }

    public void insertArticle(BoardArticleEntity articleEntity, MemberVO memberVO, String uuid) throws Exception {
        //회원에게 타겟 메뉴가 있는지 확인
        MenuEntity targetMenu = menuDAO.findByUuidAndRegUserSeqAndDelDateIsNull(uuid, memberVO.getSeq())
                .orElseThrow(() -> new ServiceException("잘못된 요청입니다."));

        //게시글 정보 입력
        BoardArticleEntity insertEntity = new BoardArticleEntity();
        insertEntity.setBoardSeq(targetMenu.getSeq());
        insertEntity.setRegUserSeq(memberVO.getSeq());
        insertEntity.setRegDate(LocalDateTime.now());

        insertEntity.setTitle(articleEntity.getTitle());
        insertEntity.setContent(articleEntity.getContent());
        insertEntity.setDisplayF(articleEntity.getDisplayF() == null ? "Y" : articleEntity.getDisplayF());
        insertEntity.setSummary(articleEntity.getSummary());

        articleDAO.save(insertEntity);
    }

    public void updateArticle(BoardArticleEntity articleEntity, MemberVO memberVO) throws Exception {

        //본인 글인지 체크할 필요가 있음. update의 where조건으로 seq와 userSeq둘다 둘 필요가 있음.
        BoardArticleEntity targetEntity = articleDAO.findBySeqAndRegUserSeqAndDelDateIsNull(articleEntity.getSeq(), memberVO.getSeq())
                .orElseThrow(() -> new Exception("잘못된 접근입니다"));
        //내용만 변경해준다.
        targetEntity.setTitle(articleEntity.getTitle());
        targetEntity.setSummary(articleEntity.getSummary());
        targetEntity.setContent(articleEntity.getContent());
        targetEntity.setDisplayF(articleEntity.getDisplayF());

        targetEntity.setModUserSeq(memberVO.getSeq());
        targetEntity.setModDate(LocalDateTime.now());
        articleDAO.save(targetEntity);

    }



}
