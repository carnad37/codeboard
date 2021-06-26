package com.hhs.codeboard.jpa.entity.menu;

import javax.persistence.*;

import com.hhs.codeboard.jpa.entity.common.DefaultEntity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "menu")
@SequenceGenerator(name = "seqGenerator", sequenceName = "seqMenu", allocationSize = 1, initialValue = 1)
public class MenuEntity extends DefaultEntity {

    private static final long serialVersionUID = 6217465463857529869L;

    @Column
    private String title;

    @Column
    private Integer menuOrder;

    @Column
    private String menuType;

    @Column 
    private Integer boardSeq;

    @Column 
    private Integer parentSeq;

    @Column
    private String menuUrl;

    @Column(unique = true)
    private String uuid;

}
