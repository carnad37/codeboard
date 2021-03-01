package com.hhs.codeboard.jpa.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.hhs.codeboard.jpa.entity.BoardManagerEntity;

@Repository
public interface BoardDAO extends JpaRepository<BoardManagerEntity, Long> {
    List<BoardManagerEntity> findAllByRegUserSeq(Integer regUserSeq);
}
