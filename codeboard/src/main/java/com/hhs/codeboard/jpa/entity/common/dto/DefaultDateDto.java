package com.hhs.codeboard.jpa.entity.common.dto;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
public class DefaultDateDto {

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    private LocalDateTime delDate;

}
