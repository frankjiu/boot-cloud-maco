package com.service;

import java.util.List;

import com.domain.MacoRole;

/**
 * 服务层接口
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */
public interface MacoRoleService {
	
	MacoRole getOne(String id);
	
	List<MacoRole> findTree();
	
	MacoRole save(MacoRole macoRole);
	
	void delete(String id);
	
	void update(MacoRole newMacoRole);
	
}
