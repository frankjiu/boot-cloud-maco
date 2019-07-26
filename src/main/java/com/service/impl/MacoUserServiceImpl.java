package com.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.domain.MacoUser;
import com.repository.MacoUserRepository;
import com.service.MacoUserService;

/**
 * 服务层接口
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */
@Service
public class MacoUserServiceImpl implements MacoUserService {
	
	@Autowired
	MacoUserRepository macoUserRepository;
	
	public Optional<MacoUser> findById(String id){
		return macoUserRepository.findById(id);
	}

	@Override
	public MacoUser getOne(String id) {
		MacoUser macoUser = macoUserRepository.findById(id).get();
		return macoUser;
	}

	@Override
	public List<MacoUser> findByUserName(String userName) {
		List<MacoUser> list = macoUserRepository.findByUserName(userName);
		return list;
	}
	
	@Override
	public List<MacoUser> findByLoginNameAndPassWord(MacoUser macoUser) {
		List<MacoUser> list = macoUserRepository.findByLoginNameAndPassWord(macoUser.getLoginName(), macoUser.getPassWord());
		return list;
	}

	@Override
	public MacoUser save(MacoUser macoUser) {
		macoUser = macoUserRepository.saveAndFlush(macoUser);
		return macoUser;
	}

	@Override
	public void delete(String id) {
		macoUserRepository.deleteById(id);
	}

	@Override
	public void update(MacoUser newMacoUser) {
		macoUserRepository.saveAndFlush(newMacoUser);
	}

	@Override
	public Integer count(MacoUser query) {
		Integer count = (int) macoUserRepository.count();
		return count;
	}

	@Override
	//public List<MacoUser> paging(MacoUser query, PageUtil page) {
	public Page<Map<String,Object>> findPage(String loginName, Date createTimeBefore, Date createTimeAfter, Pageable pageable) {
		return macoUserRepository.findPage(loginName, createTimeBefore, createTimeAfter, pageable);
	}
}
