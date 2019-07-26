package com.service;

import java.util.List;

import com.domain.MacoUserRole;

/**
 * 服务层接口
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */
public interface MacoUserRoleService {

	MacoUserRole getOne(String id);

	List<MacoUserRole> findByUserId(String id);

	MacoUserRole save(MacoUserRole macoUserRole);

	void delete(String id);

	void update(MacoUserRole newMacoUserRole);

}
