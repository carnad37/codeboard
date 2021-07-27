package com.hhs.codeboard.web.service.category;

import com.hhs.codeboard.jpa.entity.board.BoardCategoryEntity;
import com.hhs.codeboard.jpa.entity.menu.MenuEntity;
import com.hhs.codeboard.jpa.service.CategoryDAO;
import com.hhs.codeboard.jpa.service.MemberDAO;
import com.hhs.codeboard.jpa.service.MenuDAO;
import com.hhs.codeboard.web.service.member.MemberVO;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CategoryService {

    private final CategoryDAO categoryDAO;
    private final MemberDAO memberDAO;
    private final MenuDAO menuDAO;

    @Autowired
    public CategoryService(CategoryDAO categoryDAO, MemberDAO memberDAO, MenuDAO menuDAO) {
        this.categoryDAO = categoryDAO;
        this.memberDAO = memberDAO;
        this.menuDAO = menuDAO;
    }

    public int insertCategory(BoardCategoryEntity cateVO, MemberVO memberVO) {
        MenuEntity targetBoard = menuDAO.findBySeqAndRegUserSeqAndDelDateIsNull(cateVO.getBoardSeq(), memberVO.getSeq())
                .orElseThrow(()-> new ServiceException("잘못된 접근입니다."));

        BoardCategoryEntity insertVO = new BoardCategoryEntity();
        insertVO.setBoardSeq(targetBoard.getSeq());
        insertVO.setTitle(cateVO.getTitle());
        insertVO.setRegDate(LocalDateTime.now());
        insertVO.setRegUserSeq(memberVO.getSeq());

        return categoryDAO.save(insertVO);
    }

}
