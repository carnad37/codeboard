package com.hhs.codeboard.web.service.common;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.io.Serializable;


@Getter
@Setter
public class SearchVO implements Serializable {

    private static final long serialVersionUID = 6414354082484006148L;

    private Integer pageIndex = 1;

    private String searchKeyword;

    private String searchCondition;

}
