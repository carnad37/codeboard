package com.hhs.codeboard.common.service;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * @author hhs
 *
 */
@Entity
public class DefaultVO extends DefaultDateVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer seq;
	
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
}
