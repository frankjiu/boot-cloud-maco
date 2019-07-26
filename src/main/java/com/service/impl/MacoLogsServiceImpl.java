package com.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.domain.MacoLogs;
import com.repository.MacoLogsRepository;
import com.service.MacoLogsService;

/**
 * 服务层实现
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */
@Service
public class MacoLogsServiceImpl implements MacoLogsService {

	@Autowired
	private MacoLogsRepository macoLogsRepository;

	/**
	 * 主键查询
	 */
	@Override
	public MacoLogs getOne(String id) {
		MacoLogs macoLogs = macoLogsRepository.getOne(id);
		return macoLogs;
	}
	
	/**
	 * 条件查询
	 */
	@Override
	public List<MacoLogs> findByUserId(String userId) {
		List<MacoLogs> MacoLogs = macoLogsRepository.findByUserId(userId);
		return MacoLogs;
	}
	
	/**
	 * 新增
	 */
	@Override
	@Transactional
	public MacoLogs save(MacoLogs macoLogs) {
		return macoLogsRepository.saveAndFlush(macoLogs);
	}
	
	/**
	 * 分页查询
	 */
	@Override
	public Page<Map<String,Object>> findPage(String userId, Date reateTimeBefore, Date createTimeAfter, Pageable pageable) {
		Page<Map<String,Object>> page = macoLogsRepository.findPage(userId, reateTimeBefore, createTimeAfter, pageable);
		return page;
	}
	
}
