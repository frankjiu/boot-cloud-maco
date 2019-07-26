package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.domain.MacoRole;

/**
 * 持久层接口
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */
public interface MacoRoleRepository extends JpaRepository<MacoRole, String>, JpaSpecificationExecutor<MacoRole> {
	
	@Query(value = " SELECT M.* " +
				 " FROM   MACO_ROLE M " + 
				 " WHERE  1 = 1 " + 
				 " START  WITH M.PID = '0' " + 
				 " CONNECT BY PRIOR M.ID = M.PID ", nativeQuery = true )
	List<MacoRole> findTree();
	
}
