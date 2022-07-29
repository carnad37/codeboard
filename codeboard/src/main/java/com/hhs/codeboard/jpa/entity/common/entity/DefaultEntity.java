package com.hhs.codeboard.jpa.entity.common.entity;

import javax.persistence.*;


import lombok.Data;

@Data
@MappedSuperclass
public abstract class DefaultEntity extends DefaultDateEntity {

	private static final long serialVersionUID = 3989969904364677147L;

	@Id
	@Column(length = 15)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGenerator")
	private Integer seq;

	@Column
	private Integer regUserSeq;

	@Column
	private Integer modUserSeq;

}
