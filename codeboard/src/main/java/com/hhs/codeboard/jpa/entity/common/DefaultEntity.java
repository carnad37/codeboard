package com.hhs.codeboard.jpa.entity.common;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class DefaultEntity extends DefaultDateEntity {

	private static final long serialVersionUID = 3989969904364677147L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seqGenerator")
	private Integer seq;

	@Column
	private Integer regUserSeq;

	@Column
	private Integer modUserSeq;

}
