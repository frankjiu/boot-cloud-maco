package com.service;

import java.util.List;

import com.domain.MacoArticle;
import com.domain.MacoArticleHelp;

/**
 * 服务层接口
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */
public interface MacoArticleService {
	
	MacoArticle getOne(String id);
	
	List<MacoArticle> findByTitleOrContent(String title, String content);
	
	List<MacoArticleHelp> findTree(MacoArticleHelp query);
	
	MacoArticle save(MacoArticle macoArticle);
	
	void delete(String id);
	
	void update(MacoArticle newMacoArticle);

	
}
