package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.domain.MacoMedia;
import com.repository.MacoMediaRepository;
import com.service.MacoMediaService;

/**
 * 服务层实现
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */
@Service
public class MacoMediaServiceImpl implements MacoMediaService {

	@Autowired
	private MacoMediaRepository macoMediaRepository;

	/**
	 * 主键查询
	 */
	@Override
	public MacoMedia getOne(String id) {
		MacoMedia macoMedia = macoMediaRepository.getOne(id);
		return macoMedia;
	}
	
	/**
	 * 条件查询
	 */
	@Override
	public List<MacoMedia> findByTitle(String title) {
		List<MacoMedia> list = macoMediaRepository.findByTitle(title);
		return list;
	}
	
	/**
	 * 新增
	 */
	@Override
	@Transactional
	public MacoMedia save(MacoMedia macoMedia) {
		return macoMediaRepository.saveAndFlush(macoMedia);
	}
	
	/**
	 * 删除
	 */
	@Override
	@Transactional
	public void delete(String id){
		macoMediaRepository.deleteById(id);
	}
	
	/**
	 * 修改
	 */
	@Override
	@Transactional
	public void update(MacoMedia newMacoMedia) {
		macoMediaRepository.saveAndFlush(newMacoMedia);
	}
	
}
