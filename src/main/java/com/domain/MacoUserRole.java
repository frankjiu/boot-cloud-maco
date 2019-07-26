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
 * 用户角色实体类
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */

@Data
@Entity
@Table(name = "MACO_USER_ROLE")
public class MacoUserRole implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id  
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "ID", nullable = false, unique = true)
	private String id;

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "ROLE_ID")
	private String roleId;

	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "UPDATE_TIME")
	private Date updateTime;

}
