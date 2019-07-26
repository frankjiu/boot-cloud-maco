package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.domain.MacoRole;
import com.repository.MacoRoleRepository;
import com.service.MacoRoleService;

/**
 * 服务层实现
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */
@Service
public class MacoRoleServiceImpl implements MacoRoleService {

	@Autowired
	private MacoRoleRepository macoRoleRepository;

	/**
	 * 主键查询
	 */
	@Override
	public MacoRole getOne(String id) {
		MacoRole macoRole = macoRoleRepository.getOne(id);
		return macoRole;
	}
	
	/**
	 * 新增
	 */
	@Override
	@Transactional
	public MacoRole save(MacoRole macoRole) {
		return macoRoleRepository.saveAndFlush(macoRole);
	}
	
	/**
	 * 删除
	 */
	@Override
	@Transactional
	public void delete(String id){
		macoRoleRepository.deleteById(id);
	}
	
	/**
	 * 修改
	 */
	@Override
	@Transactional
	public void update(MacoRole newMacoRole) {
		macoRoleRepository.saveAndFlush(newMacoRole);
	}
	
	/**
	 * 树查询
	 */
	@Override
	public List<MacoRole> findTree() {
		List<MacoRole> list = macoRoleRepository.findTree();
		return list;
	}
}
