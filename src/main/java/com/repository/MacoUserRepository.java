package com.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.domain.MacoUser;

/**
 * 服务层接口
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */
public interface MacoUserRepository extends JpaRepository<MacoUser, String>, JpaSpecificationExecutor<MacoUser> {

	/**
	 *  根据登录名查询用户
	 * @param loginName
	 * @return
	 */
	@Query(value = " SELECT * FROM login_user u WHERE u.login_name=:loginName ", nativeQuery = true)
    List<MacoUser> findByUserName(@Param("loginName") String loginName);
	
	/**
	 *  根据登录名和密码查询用户
	 * @param loginName
	 * @param password
	 * @return
	 */
	@Query(value = " SELECT * FROM login_user u WHERE u.login_name=:loginName and u.login_password=:password ", nativeQuery = true)
	List<MacoUser> findByLoginNameAndPassWord(@Param("loginName") String loginName, @Param("password") String password);
	
	/**
	 *  根据登录名和(非)ID查询用户
	 * @param loginName
	 * @param id
	 * @return
	 */
	@Query(value = " SELECT * FROM login_user u WHERE u.login_name=:loginName and u.id<>:id ", nativeQuery = true)
	List<MacoUser> findByNameAndId(@Param("loginName") String loginName, @Param("id") Integer id);
	
	/**
	 *  根据ID删除用户
	 * @param id
	 */
	@Transactional 
	@Modifying 
	@Query(value = " DELETE FROM login_user WHERE id=:id ", nativeQuery = true)
	void deleteByNativeId(@Param("id") Integer id);
	
	/**
	 * 根据干警查询其是否有设置账户
	 */
	@Query(value = " SELECT u.* FROM login_user u LEFT JOIN police_info p ON u.police_id = p.id WHERE p.id=:id ", nativeQuery = true)
	List<MacoUser> findUserByPoliceId(@Param("id") Integer id);
	
	/**
	 *  分页查询
	 * @param name
	 * @param createTimeBefore
	 * @param createTimeAfter
	 * @param pageable
	 * @return
	 */
	@Query(value = " select u.*, p.`name` policeName from login_user u "
			+ " LEFT JOIN police_info p ON u.police_id = p.id "
			+ " where if(?1 !='', u.login_name like ?1,1=1) and if(?2 !='', u.create_time>=?2,1=1) and if(?3 !='', u.create_time<=?3,1=1) "
			+ " order by u.update_time desc, u.create_time desc ", 
			countQuery = " select count(*) from login_user u "
			+ " LEFT JOIN police_info p ON u.police_id = p.id "
			+ " where if(?1 !='', u.login_name like ?1,1=1) and if(?2 !='', u.create_time>=?2,1=1) and if(?3 !='', u.create_time<=?3,1=1) "
			+ " order by u.update_time desc, u.create_time desc ",
			nativeQuery = true)
	Page<Map<String,Object>> findPage(String name, Date createTimeBefore, Date createTimeAfter, Pageable pageable);
	
}
