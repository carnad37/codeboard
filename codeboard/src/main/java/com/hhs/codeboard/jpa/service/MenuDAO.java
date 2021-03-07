package com.hhs.codeboard.jpa.service;

import java.util.Optional;
import com.hhs.codeboard.jpa.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuDAO extends JpaRepository<MenuEntity, Long> {

    Optional<MenuEntity> findByUuid(String uuid);

}
