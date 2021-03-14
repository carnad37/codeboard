package com.hhs.codeboard.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hhs.codeboard.common.service.DefaultEntity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "menu")
public class MenuEntity extends DefaultEntity{

    private static final long serialVersionUID = 6217465463857529869L;

    @Column
    private String title;

    @Column
    private Integer order;

    @Column
    private String menuType;

    @Column 
    private Integer boardSeq;

    @ManyToOne
    @JoinColumn(name="boardSeq")
    private BoardManagerEntity boardManager;

    @Column
    private String menuUrl;

    @Column(unique = true)
    private String uuid;
    

}
