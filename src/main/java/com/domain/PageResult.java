package com.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.solr.domain.SolrCompany;

import lombok.Data;

@Data
@Entity
public class PageResult implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id  
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	private String id;
	
	@Transient
	private Integer curPage; // 当前页
	
	@Transient
	private Integer pageCount; // 总页数
	
	@Transient
	private Long recordCount; // 数据总条数
	
	@Transient
	private List<SolrCompany> companyList = new ArrayList<SolrCompany>(); // 结果集
	
}