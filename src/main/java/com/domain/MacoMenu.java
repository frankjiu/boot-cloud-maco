package com.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.utils.TreeNode;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单实体类
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */

@Data
@Entity
@Table(name = "MACO_MENU")
@EqualsAndHashCode(callSuper=true)
public class MacoMenu extends TreeNode implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id  
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "ID", nullable = false, unique = true)
	private String id;

	@Column(name = "MENU_NAME")
	private String menuName;

	@Column(name = "MENU_URL")
	private String menuUrl;
	
	@Column(name = "PID")
	private String pid;
	
	@Column(name = "MENU_LEVEL")
	private Integer menuLevel;
	
	@Column(name = "INDEX_ORDER")
	private Integer indexOrder;
	
	//@JsonFormat(pattern="yyyy-MM-dd  HH:mm:ss",timezone="GMT+8")
	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "UPDATE_TIME")
	private Date updateTime;
	
}
