package com.hhs.codeboard.jpa.entity;

import javax.persistence.*;

import com.hhs.codeboard.common.service.DefaultEntity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "menu")
@SequenceGenerator(name = "seqMenuGenerator", sequenceName = "seqMenu", allocationSize = 1, initialValue = 1)
public class MenuEntity implements Serializable {

    private static final long serialVersionUID = 6217465463857529869L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqMenuGenerator")
    private Integer menuSeq;

    @Column
    private Integer regUserSeq;

    @Column
    private LocalDateTime regDate;

    @Column
    private LocalDateTime modDate;

    @Column
    private LocalDateTime delDate;

    @Column
    private Integer modUserSeq;

    @Column
    private String title;

    @Column
    private Integer order;

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
