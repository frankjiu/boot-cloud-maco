package com.solr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.PageResult;
import com.solr.domain.SolrCompany;
import com.solr.service.SolrCompanyService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/manage")
public class SolrCompanyController {
	
	@Autowired
	private SolrCompanyService companyService;
	
	@Autowired
	private SolrCompanyService solrCompanyService;
	
	@RequestMapping("/importAll")
	@ResponseBody
	public String importAll(){
		JSONObject js = new JSONObject();
		try {
			SolrCompany result = companyService.importAll();
			js.put("data", result);
			js.put("flag", true);
			js.put("msg", "success");
		} catch (Exception e) {
			js.put("flag", false);
			js.put("msg", "failure");
			e.printStackTrace();
		}
		return js.toString();
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public String findAll(){
		JSONObject js = new JSONObject();
		try {
			companyService.findAll();
			js.put("flag", true);
			js.put("msg", "success");
		} catch (Exception e) {
			js.put("flag", false);
			js.put("msg", "failure");
			e.printStackTrace();
		}
		return js.toString();
	}
	
	@RequestMapping("/pageQuery")
	@ResponseBody
	public String pageQuery(){
		JSONObject js = new JSONObject();
		try {
			PageResult PageResult = companyService.pageQuery(null, "", "", null, null);
			js.put("data", PageResult);
			js.put("flag", true);
			js.put("msg", "success");
		} catch (Exception e) {
			js.put("flag", false);
			js.put("msg", "failure");
			e.printStackTrace();
		}
		return js.toString();
	}
	
	/**
	 * 这里不同于以上原始方式,使用的是JPA
	 * @param companyName
	 * @return
	 */
	public List<SolrCompany> getCompanyByName(String name) {
		return solrCompanyService.findByName(name);
	}
	
	
}
