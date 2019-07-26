package com.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * 文章实体类
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */

@Data
@Entity
@Table(name = "MACO_ARTICLE")
public class MacoArticle implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id  
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "ID", nullable = false, unique = true)
	private String id;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "KEYWORD")
	private String keyword;

	@Column(name = "CONTENT")
	private String content;
	
	@Column(name = "PID")
	private String pid;

	@Column(name = "AUTHOR")
	private String author;

	@Column(name = "TIMES")
	private Integer times;

	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "UPDATE_TIME")
	private Date updateTime;

}
