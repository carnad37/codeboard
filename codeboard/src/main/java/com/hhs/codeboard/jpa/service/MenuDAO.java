package com.hhs.codeboard.jpa.service;

import java.util.List;
import java.util.Optional;
import com.hhs.codeboard.jpa.entity.menu.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuDAO extends JpaRepository<MenuEntity, Long> {

    Optional<MenuEntity> findByUuidAndRegUserSeqAndDelDateIsNull(String uuid, Integer regUserSeq);

    Optional<MenuEntity> findBySeqAndRegUserSeqAndDelDateIsNull(Integer seq, Integer regUserSeq);

    List<MenuEntity> findAllByRegUserSeqAndDelDateIsNull(Integer regUserSeq);

    List<MenuEntity> findAllByRegUserSeqAndMenuTypeAndDelDateIsNull(Integer regUserSeq, String menuType);

}
