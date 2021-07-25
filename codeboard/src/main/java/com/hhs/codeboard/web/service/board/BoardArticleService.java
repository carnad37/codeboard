package com.hhs.codeboard.web.service.board;

import com.hhs.codeboard.enumeration.MenuTypeEnum;
import com.hhs.codeboard.jpa.entity.board.BoardArticleEntity;
import com.hhs.codeboard.jpa.entity.menu.MenuEntity;
import com.hhs.codeboard.jpa.service.ArticleDAO;
import com.hhs.codeboard.jpa.service.MenuDAO;
import com.hhs.codeboard.util.pagination.BoardPagination;
import com.hhs.codeboard.web.service.common.SearchVO;
import com.hhs.codeboard.web.service.member.MemberVO;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.awt.*;
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

    public List<BoardArticleEntity> selectArticleList(MemberVO memberVO, String uuid, SearchVO searchVO, BoardPagination pagination) throws Exception {
        //init
        int perPage = 15;
        Sort curSort = Sort.by(Sort.Direction.DESC, "seq");

        //validate
        //해당 boardSeq가 로그인중인 유저에게 권한이 있는 게시판인지 확인
        MenuEntity targetBoard = menuDAO.findByUuidAndRegUserSeqAndMenuTypeAndDelDateIsNull(uuid, memberVO.getSeq(), MenuTypeEnum.BOARD.getMenuType())
            .orElseThrow(() -> new ServiceException("접근권한이 없습니다."));

        //페이지 설정
        int totalCount = 0;
        List<BoardArticleEntity> resultList = null;

        if (StringUtils.hasText(searchVO.getSearchKeyword())) {
            if (StringUtils.hasText(searchVO.getSearchKeyword())) {
                if ("title".equals(searchVO.getSearchCondition())) {
                    totalCount = articleDAO.countAllByBoardSeqAndTitleContainsAndDelDateIsNull(targetBoard.getSeq(), searchVO.getSearchKeyword());
                } else if ("content".equals(searchVO.getSearchCondition())) {
                    totalCount = articleDAO.countAllByBoardSeqAndContentContainsAndDelDateIsNull(targetBoard.getSeq(), searchVO.getSearchKeyword());
                } else {
                    totalCount = articleDAO.countAllByTitleContainsOrContentContainsAndBoardSeqAndDelDateIsNull(searchVO.getSearchKeyword(), searchVO.getSearchKeyword(), targetBoard.getSeq());
                }
            }

            pagination.init(totalCount);

            if ("title".equals(searchVO.getSearchCondition())) {
                resultList = articleDAO.findAllByBoardSeqAndTitleContainsAndDelDateIsNull(
                        targetBoard.getSeq(), searchVO.getSearchKeyword(), pagination.getPageable(curSort));
            } else if ("content".equals(searchVO.getSearchCondition())) {
                resultList = articleDAO.findAllByBoardSeqAndContentContainsAndDelDateIsNull(
                        targetBoard.getSeq(), searchVO.getSearchKeyword(), pagination.getPageable(curSort));
            } else {
                resultList = articleDAO.findAllByTitleContainsOrContentContainsAndBoardSeqAndDelDateIsNull(
                        searchVO.getSearchKeyword(), searchVO.getSearchKeyword(), targetBoard.getSeq()
                        , pagination.getPageable(curSort));
            }
        } else {
            pagination.init(articleDAO.countAllByBoardSeqAndDelDateIsNull(targetBoard.getSeq()));
            resultList = articleDAO.findAllByBoardSeqAndDelDateIsNull(targetBoard.getSeq(), pagination.getPageable(curSort));
        }

        return  resultList;
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
