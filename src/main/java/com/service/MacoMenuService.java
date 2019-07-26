package com.service;

import java.util.List;

import com.domain.MacoMenu;

/**
 * 服务层接口
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */
public interface MacoMenuService {
	
	MacoMenu getOne(String id);
	
	List<MacoMenu> getByAuth(MacoMenu macoMenu, String[] authArr);
	
	List<MacoMenu> findTree(String[] authArr);
	
	MacoMenu save(MacoMenu macoMenu);
	
	void delete(String id);
	
	void update(MacoMenu newMacoMenu);
	
}
