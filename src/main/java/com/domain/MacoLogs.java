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
 * 日志实体类
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */

@Data
@Entity
@Table(name = "MACO_LOGS")
public class MacoLogs implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id  
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "ID", nullable = false, unique = true)
	private String id;

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "IP_ADDRESS")
	private String ipAddress;

	@Column(name = "OP_MODULE")
	private String opModule;

	@Column(name = "INTRODUCE")
	private String introduce;

	@Column(name = "CREATE_TIME")
	private Date createTime;
	
	@Transient
	private Date createTimeBefore;
	
	@Transient
	private Date createTimeAfter;

}
