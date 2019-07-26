package com.utils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class ResultVo {
	
	
	@Id  
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	private Integer id;
	
	@Transient
	private int count = 0;
	
	@Transient
	private Object data = null;
	
	@Transient
	private boolean flag = false;
	
	@Transient
	private String msg = null;
	
	/*@Transient
	private String code;
	
	@Transient
	private String status = null;
	
	@Transient
	private int row = 0;*/
	
}
