package com.solr.domain;

import java.io.Serializable;

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
/*@Data
@Entity
@SolrDocument(solrCoreName = "new_core")
// 实现序列化接口便于网络传输
public class SolrCompany implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Field("ID")
	private String id;
	@Field("NAME")
	private String name;
	@Field("C_GUARD_MACHINE")
	private String cGuardMachine;
}*/

@Data
@Entity
@Table(name = "COMPANY")
public class SolrCompany implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id  
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "ID", nullable = false, unique = true)
	private String id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "C_GUARD_MACHINE")
	private String cGuardMachine;

}
