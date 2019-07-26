package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.domain.MacoMedia;

/**
 * 持久层接口
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */
public interface MacoMediaRepository extends JpaRepository<MacoMedia, String>, JpaSpecificationExecutor<MacoMedia> {
	
	List<MacoMedia> findByTitle(String title);
	
}
