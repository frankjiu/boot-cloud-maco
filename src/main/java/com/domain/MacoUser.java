package com.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * 用户实体类
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */

@Data
@Entity
@Table(name = "MACO_USER")
public class MacoUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "ID", nullable = false, unique = true)
	private String id;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "LOGIN_NAME")
	private String loginName;

	@Column(name = "PASS_WORD")
	private String passWord;

	@Column(name = "MOBILE_PHONE")
	private String mobilePhone;

	@Column(name = "WECHAT")
	private String wechat;

	@Column(name = "EMAIL")
	private String email;

	@Transient
	private String code;
	
	@Transient
	private String roleId;

	@Column(name = "IS_FREEZE")
	private Integer isFreeze;

	@Column(name = "LOGIN_TIME")
	private Date loginTime;

	@Column(name = "LOGOUT_TIME")
	private Date logoutTime;

	@Column(name = "CREATE_TIME")
	// @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	// @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	@Transient
	private Date createTimeBefore;
	
	@Transient
	private Date createTimeAfter;

	@Column(name = "UPDATE_TIME")
	private Date updateTime;

	// 仅用于存值
	/*@Transient
	private String role_name;*/
	
	@Transient
	private String freezeDesc;
	
}
