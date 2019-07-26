package com.constant;

/**
 * 常量类
 * 
 * @author xinbe
 *
 */
public final class Constants {

	// 避免实例化
	private Constants() {
	};

	// 登录用户主键ID
	public static final String SESSION_LOGIN_ID = "_SESSION_LOGIN_ID";

	// 用户账号名称
	public static final String SESSION_LOGIN_NAME = "_SESSION_LOGIN_NAME";

	// 用户角色主键ID
	public static final String SESSION_LOGIN_ROLE_ID = "_SESSION_LOGIN_ROLE_ID";
	
	// 用户角色名称
	public static final String SESSION_LOGIN_ROLE = "_SESSION_LOGIN_ROLE";

	// 用户菜单权限集合
	public static final String SESSION_MENU_LIST = "_SESSION_MENU_LIST";
	
	// 用户菜单权限数组--菜单主键ID数组
	public static final String SESSION_MENU_IDS_ARRAY = "_SESSION_MENU_IDS_ARRAY";

	// 图片上传路径(可由后台重置)
	public static final String IMAGE_UPLOAD_PATH = "/";

	// 图片访问路径
	public static final String BASE_URL = "/upload_textarea/"; // http://localhost:8080/upload_textarea/

	// 接口常量
	public interface RoleType {
		public static final int ROLE_TYPE_A = 0;
		public static final int ROLE_TYPE_B = 1;
		public static final int ROLE_TYPE_C = 2;
		public static final int ROLE_TYPE_D = 3;
	}
	
	// 队列消息名称
	public static final String QUEUE_DESTINATION = "QUEUE_DESTINATION";
	
	// 话题消息名称
	public static final String TOPIC_NAME = "ACTIVE_MQ_TOPIC_SPE";

}
