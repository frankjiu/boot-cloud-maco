package com.service;

import java.util.List;

import com.domain.MacoMedia;

/**
 * 服务层接口
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */
public interface MacoMediaService {
	
	MacoMedia getOne(String id);
	
	List<MacoMedia> findByTitle(String title);
	
	MacoMedia save(MacoMedia macoMedia);
	
	void delete(String id);
	
	void update(MacoMedia newMacoMedia);
	
}
