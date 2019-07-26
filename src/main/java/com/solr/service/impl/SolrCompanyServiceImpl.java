package com.solr.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.domain.MacoArticle;
import com.domain.PageResult;
import com.repository.MacoArticleRepository;
import com.solr.domain.SolrCompany;
import com.solr.repository.SolrCompanyRepository;
import com.solr.service.SolrCompanyService;

@Service
public class SolrCompanyServiceImpl implements SolrCompanyService {
	
	@Autowired
	private MacoArticleRepository macoArticleRepository;
	
	@Autowired
	private SolrCompanyRepository solrCompanyRepository;
	
	@Autowired
	private SolrClient solrClient;
	
	//final String solrUrl = "http://192.168.1.70:8983/solr/new_core";
	//HttpSolrClient solrServer = new HttpSolrClient.Builder(solrUrl).withConnectionTimeout(10000).withSocketTimeout(60000).build();

	/**
	 * 导入数据到索引库
	 */
	@Transactional
	public SolrCompany importAll() {
		SolrCompany result = null;
		try {
			// 从数据库获取文章列表
			List<MacoArticle> list = macoArticleRepository.findAll();
			// 循环将列表数据添加到索引库文档中
			for (MacoArticle macoArticle : list) {
				// 创建一个solr的索引库文档
				SolrInputDocument document = new SolrInputDocument();
				document.addField("id", macoArticle.getId());
				document.addField("name", macoArticle.getTitle());
				document.addField("c_guard_machine", macoArticle.getContent());
				solrClient.add(document);
				solrClient.commit();
			}
			
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 从索引库查询数据
	 */
	@Override
	public void findAll() {
		try {
			// 创建搜索对象
			SolrQuery query = new SolrQuery();
			// 设置搜索条件
			query.setQuery("*:*");
			// 发起搜索请求
			QueryResponse response;
			response = solrClient.query(query);
			// 处理搜索结果
			SolrDocumentList results = response.getResults();
			
			// 遍历搜索结果
			System.out.println("搜索到的结果总数:" + results.getNumFound());
			for (SolrDocument solrDocument : results) {
				System.out.println("----------------------------------------------------");
				System.out.println("id：" + solrDocument.get("id"));
				System.out.println("name" + solrDocument.get("name"));
				System.out.println("c_guard_machine" + solrDocument.get("c_guard_machine"));
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 复杂搜索
	 */
	@Override
	public PageResult pageQuery(String queryString, String name, String c_guard_machine, String sort, Integer page) {
		// 封装分页对象
		PageResult pageResult = new PageResult();
		try {
			// 初始化数据(由前端传递)
			sort = "2";
			// 创建SolrQuery对象
			SolrQuery solrQuery = new SolrQuery();
			// 设置查询关键词
			if (StringUtils.isNotBlank(queryString)) {
				solrQuery.setQuery(queryString);
			} else {
				solrQuery.setQuery("*:*");
			}
			// 设置默认域
			solrQuery.set("df", "name");
			// 设置名称
			if (StringUtils.isNotBlank(name)) {
				name = "name:" + name;
			}
			if (StringUtils.isNotBlank(c_guard_machine)) {
				c_guard_machine = "c_guard_machine:" + c_guard_machine;
			}
			// 设置类名过滤条件
			//solrQuery.setFilterQueries(name, c_guard_machine);
			// 排序，如果是1，正序排序，如果是其他，则倒序排序
			if ("1".equals(sort)) {
				solrQuery.setSort("id", ORDER.asc);
			} else {
				solrQuery.setSort("id", ORDER.desc);
			}
			// 设置分页
			if (page == null) {
				page = 1;
			}
			solrQuery.setStart((page - 1) * 10);
			solrQuery.setRows(10);
			// 设置高亮
			solrQuery.setHighlight(true);
			solrQuery.addHighlightField("name");
			solrQuery.setHighlightSimplePre("<font color='red'>");
			solrQuery.setHighlightSimplePost("</font>");
			// 查询数据
			QueryResponse response = solrClient.query(solrQuery);
			// 获取查询总数
			SolrDocumentList results = response.getResults();
			long total = results.getNumFound();
			// 获取高亮数据
			Map<String, Map<String, List<String>>> map = response.getHighlighting();
			// 解析结果集，存放到CompanyField 中
			List<SolrCompany> companyList = new ArrayList<>();
			for (SolrDocument solrDocument : results) {
				SolrCompany company = new SolrCompany();
				// id
				company.setId(solrDocument.get("id").toString());
				// 名称,设置高亮
				List<String> list = map.get(solrDocument.get("id")).get("name");
				if (list != null && list.size() > 0) {
					company.setName(list.get(0));
				} else {
					company.setName(solrDocument.get("name").toString());
				}
				companyList.add(company);
			}
			
			pageResult.setCurPage(page);
			pageResult.setRecordCount(total);
			pageResult.setCompanyList(companyList);
			// 总页数计算公式(total+rows-1)/rows
			pageResult.setPageCount((int) (total + 20 - 1) / 20);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pageResult;
	}

	@Override
	public List<SolrCompany> findByName(String name) {
		List<SolrCompany> list = solrCompanyRepository.findByName(name);
		return list;
	}
	
	
}
