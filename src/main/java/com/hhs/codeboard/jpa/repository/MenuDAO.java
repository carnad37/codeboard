package com.hhs.codeboard.jpa.repository;

import java.util.List;
import java.util.Optional;
import com.hhs.codeboard.jpa.entity.menu.entity.MenuEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuDAO extends JpaRepository<MenuEntity, Long> {

    Optional<MenuEntity> findByUuidAndRegUserSeqAndDelDateIsNull(String uuid, Integer regUserSeq);

    Optional<MenuEntity> findBySeqAndRegUserSeqAndDelDateIsNull(Integer seq, Integer regUserSeq);

    Optional<MenuEntity> findByUuidAndRegUserSeqAndMenuTypeAndDelDateIsNull(String uuid, Integer regUserSeq, String menuType);

    List<MenuEntity> findAllByRegUserSeqAndDelDateIsNull(Integer regUserSeq, Sort sort);

    List<MenuEntity> findAllByRegUserSeqAndDelDateIsNull(Integer regUserSeq);

    List<MenuEntity> findAllByRegUserSeqAndMenuTypeAndDelDateIsNull(Integer regUserSeq, String menuType);

    List<MenuEntity> findAllByParentSeqAndDelDateIsNull(Integer parentSeq);

}
