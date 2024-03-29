package com.hhs.codeboard.jpa.entity.board;

import javax.persistence.*;

import com.hhs.codeboard.config.common.CommonStaticProperty;
import com.hhs.codeboard.jpa.entity.board.entity.BoardCategoryEntity;
import com.hhs.codeboard.jpa.entity.common.entity.DefaultEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="code_category_item")
@TableGenerator(name = CommonStaticProperty.SEQUENCE_TABLE_GENERATOR, table = CommonStaticProperty.SEQUENCE_TABLE_NAME)
public class BoardCateItemEntity extends DefaultEntity{

    private static final long serialVersionUID = 4507117424141720882L;

    @Column
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cateSeq")
    private BoardCategoryEntity boardCategory;

}
