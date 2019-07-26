package com.utils;

/**
 * 日志操作类型划分
 */
public class LogOperation {

	// ===============操作类型 CRUD Login Goto===============

	/**
	 * 操作类型 系统登录
	 */
	public static final String OP_LOGIN = "登录";
	
	/**
	 * 操作类型 页面跳转
	 */
	public static final String OP_GOTO = "跳转";
	
	/**
	 * 操作类型 新增
	 */
	public static final String OP_ADD = "新增";
	
	/**
	 * 操作类型 删除
	 */
	public static final String OP_DEL = "删除";
	
	/**
	 * 操作类型 修改
	 */
	public static final String OP_MODIFY = "修改";
	
	/**
	 * 操作类型 查询
	 */
	public static final String OP_QUERY = "查询";
	
	

	// ===============功能业务模块(具体功能模块)===============

	/**
	 * 操作所属业务模块 文章管理
	 */
	public static final String WP_SYSTEM = "系统管理";
	
	/**
	 * 操作所属业务模块 文章管理
	 */
	public static final String WP_ARTICLE = "文章管理";

	/**
	 * 操作所属业务模块 多媒体管理
	 */
	public static final String WP__MEDIA = "多媒体管理";


	
	/**
	 * 操作类型
	 */
	public String operationType;
	
	/**
	 * 功能业务模块
	 */
	public String operationModule;
	
	/**
	 * 操作描述
	 */
	public String describe;

	LogOperation(String operationType, String operationModule, String describe) {
		this.operationType = operationType;
		this.operationModule = operationModule;
		this.describe = describe;
	}

}
