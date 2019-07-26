package com.solr.repository;

import java.util.List;

import org.springframework.data.solr.repository.SolrCrudRepository;

import com.solr.domain.SolrCompany;

public interface SolrCompanyRepository extends SolrCrudRepository<SolrCompany, String> {
	
	List<SolrCompany> findByName(String name);
	
}
