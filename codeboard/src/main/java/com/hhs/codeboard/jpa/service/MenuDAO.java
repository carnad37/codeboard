package com.hhs.codeboard.jpa.service;

import java.util.List;
import java.util.Optional;
import com.hhs.codeboard.jpa.entity.menu.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuDAO extends JpaRepository<MenuEntity, Long> {

    Optional<MenuEntity> findByUuidAndRegUserSeq(String uuid, Integer regUserSeq);

    List<MenuEntity> findAllByRegUserSeq(Integer regUserSeq);

}
