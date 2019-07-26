package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.domain.MacoUserRole;

/**
 * 持久层接口
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */
public interface MacoUserRoleRepository extends JpaRepository<MacoUserRole, String>, JpaSpecificationExecutor<MacoUserRole> {

	List<MacoUserRole> findByUserId(String userId);
	
}
