package com.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.domain.MacoLogs;

/**
 * 持久层接口
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */
public interface MacoLogsRepository extends JpaRepository<MacoLogs, String>, JpaSpecificationExecutor<MacoLogs> {
	
	List<MacoLogs> findByUserId(String userId);
	
	@Query(value = " select u.*, p.`name` policeName from login_user u "
			+ " LEFT JOIN police_info p ON u.police_id = p.id "
			+ " where if(?1 !='', u.login_name like ?1,1=1) and if(?2 !='', u.create_time>=?2,1=1) and if(?3 !='', u.create_time<=?3,1=1) "
			+ " order by u.update_time desc, u.create_time desc ", 
			countQuery = " select count(*) from login_user u "
			+ " LEFT JOIN police_info p ON u.police_id = p.id "
			+ " where if(?1 !='', u.login_name like ?1,1=1) and if(?2 !='', u.create_time>=?2,1=1) and if(?3 !='', u.create_time<=?3,1=1) "
			+ " order by u.update_time desc, u.create_time desc ",
			nativeQuery = true)
	Page<Map<String,Object>> findPage(String userId, Date createTimeBefore, Date createTimeAfter, Pageable pageable);
	
}
