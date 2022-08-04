package com.hhs.codeboard.jpa.entity.common.dto;

import lombok.Data;

import javax.persistence.Column;


@Data
public class DefaultDto extends DefaultDateDto{

    private Integer seq;

    private Integer regUserSeq;

    private Integer modUserSeq;

}
