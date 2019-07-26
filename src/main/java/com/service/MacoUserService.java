package com.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.domain.MacoUser;

/**
 * 服务层接口
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */
public interface MacoUserService {
	
	MacoUser getOne(String id);

	List<MacoUser> findByUserName(String userName);

	List<MacoUser> findByLoginNameAndPassWord(MacoUser macoUser);
	
	MacoUser save(MacoUser macoUser);

	void delete(String id);

	void update(MacoUser newMacoUser);

	Integer count(MacoUser query);

	//List<MacoUser> findPage(MacoUser macoUser, PageUtil page);
	Page<Map<String,Object>> findPage(String loginName, Date createTimeBefore, Date createTimeAfter, Pageable pageable);

}
