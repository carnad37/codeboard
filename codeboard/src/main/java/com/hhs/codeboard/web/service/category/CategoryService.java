package com.hhs.codeboard.web.service.category;

import com.hhs.codeboard.jpa.entity.board.BoardArticleEntity;
import com.hhs.codeboard.jpa.entity.board.BoardCategoryEntity;
import com.hhs.codeboard.jpa.entity.menu.MenuEntity;
import com.hhs.codeboard.jpa.service.ArticleDAO;
import com.hhs.codeboard.jpa.service.CategoryDAO;
import com.hhs.codeboard.jpa.service.MemberDAO;
import com.hhs.codeboard.jpa.service.MenuDAO;
import com.hhs.codeboard.web.service.member.MemberVO;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryDAO categoryDAO;
    private final MemberDAO memberDAO;
    private final MenuDAO menuDAO;
    private final ArticleDAO articleDAO;

    @Autowired
    public CategoryService(CategoryDAO categoryDAO, MemberDAO memberDAO, MenuDAO menuDAO, ArticleDAO articleDAO) {
        this.categoryDAO = categoryDAO;
        this.memberDAO = memberDAO;
        this.articleDAO = articleDAO;
        this.menuDAO = menuDAO;
    }

    public int insertCategory(BoardCategoryEntity cateVO, MemberVO memberVO) {
        /*
            먼저 해당 게시판에대해 유저의 권한을 확인한다.
         */
        MenuEntity targetBoard = menuDAO.findBySeqAndRegUserSeqAndDelDateIsNull(cateVO.getBoardSeq(), memberVO.getSeq())
                .orElseThrow(()-> new ServiceException("잘못된 접근입니다."));

        BoardCategoryEntity insertVO = new BoardCategoryEntity();
        insertVO.setBoardSeq(targetBoard.getSeq());
        insertVO.setTitle(cateVO.getTitle());
        insertVO.setRegDate(LocalDateTime.now());
        insertVO.setRegUserSeq(memberVO.getSeq());

        categoryDAO.save(insertVO);
        return 1;
    }

    public int deleteCategory(BoardCategoryEntity cateVO, MemberVO memberVO) {
        /*
            카테고리의 삭제. 해당 카테고리를 삭제하며, delDate를 업데이트한다.
            해당 카테고리 번호를 가진 boardArticle역시 모두 null로 초기화한다.
         */
        MenuEntity targetBoard = menuDAO.findBySeqAndRegUserSeqAndDelDateIsNull(cateVO.getBoardSeq(), memberVO.getSeq())
                .orElseThrow(()-> new ServiceException("잘못된 접근입니다."));

        Integer delCateSeq = cateVO.getSeq();

        BoardCategoryEntity deleteEntity = categoryDAO.findBySeqAndDelDateIsNotNull(delCateSeq)
                .orElseThrow(() -> new ServiceException("잘못된 접근입니다."));

        deleteEntity.setDelDate(LocalDateTime.now());
        categoryDAO.save(deleteEntity);

        //해당 카테고리를 가진 boardArticle을 일괄 null 업데이트할 필요가 있다.
        List<BoardArticleEntity> articleList = articleDAO.findAllByCategorySeq(delCateSeq);
        articleList.forEach(entity->entity.setCategorySeq(null));
        articleDAO.saveAll(articleList);

        return articleList.size();
    }

}
