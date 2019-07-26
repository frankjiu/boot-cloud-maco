package com.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * 文章实体辅助类
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */

@Data
@Entity
public class MacoArticleHelp implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id  
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	private String id;

	private String pid;
	
	private String menuName;

	private String indexOrder;
	
	private String searchWord;
	
	private String menuDbStr;
	
	private String keyword;
	
	private String andKeyword;
	
	private String orKeyword;

	private String title;
	
	private String content;
	
	private String author;

	private Integer times;

	private Date createTime;

	private Date updateTime;

}
