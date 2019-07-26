package com.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.domain.MacoRole;
import com.domain.MacoUser;
import com.domain.MacoUserRole;
import com.service.MacoRoleService;
import com.service.MacoUserRoleService;
import com.service.MacoUserService;
import com.constant.Constants;
import com.utils.LogAssist;
import com.utils.LogOperation;
import com.utils.MD5Helper;
import com.utils.ResultVo;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

/**
 * 用户控制层
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */
@Controller
@RequestMapping("/user")
public class MacoUserController {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private MacoUserService macoUserService;
	
	@Autowired
	private MacoUserRoleService macoUserRoleService;
	
	@Autowired
	private MacoRoleService macoRoleService;
	
	/**
	 * 跳转页面
	 */
	@RequestMapping("/")
	@LogAssist(operationType = LogOperation.OP_GOTO, operationModule = LogOperation.WP_SYSTEM, describe = "用户--跳转页面")
	@ApiOperation(value="页面跳转", notes="跳转用户管理页面")
	public String to(String id) {
		return "/user_manage";
	}
	
	/**
	 * 主键查询
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	@LogAssist(operationType = LogOperation.OP_QUERY, operationModule = LogOperation.WP_SYSTEM, describe = "用户--主键查询")
	@ApiOperation(value="查询用户", notes="根据主键条件查询用户")
	@ApiImplicitParam(name = "id", value = "用户主键ID", required = true, dataType = "String")
	public String getOne(String id) {
		JSONObject js = new JSONObject();
		try {
			// 查询用户
			MacoUser macoUser = macoUserService.getOne(id);
			// 根据用户ID查询用户角色
			List<MacoUserRole> macoUserRoleList = macoUserRoleService.findByUserId(id);
			if (macoUserRoleList != null && macoUserRoleList.size() > 0) macoUser.setRoleId(macoUserRoleList.get(0).getRoleId());
			js.put("data", macoUser);
			js.put("flag", true);
			js.put("msg", "success");
		} catch (Exception e) {
			js.put("flag", false);
			js.put("msg", "failure");
			e.printStackTrace();
		}
		return js.toString();
	}

	/**
	 * 条件查询
	 */
	@RequestMapping("/findByUserName")
	@ResponseBody
	@LogAssist(operationType = LogOperation.OP_QUERY, operationModule = LogOperation.WP_SYSTEM, describe = "用户--条件查询")
	@ApiOperation(value="查询用户", notes="根据UserName条件查询用户")
	@ApiImplicitParam(name = "userName", value = "用户userName", required = true, dataType = "String")
	public String findByUserName(MacoUser query) {
		JSONObject js = new JSONObject();
		try {
			// 查询用户
			List<MacoUser> list = macoUserService.findByUserName(query.getUserName());
			js.put("data", list);
			js.put("flag", true);
			js.put("msg", "success");
		} catch (Exception e) {
			js.put("flag", false);
			js.put("msg", "failure");
			e.printStackTrace();
		}
		return js.toString();
	}
	
	/**
	 * 新增
	 */
	@RequestMapping("/save")
	@ResponseBody
	@LogAssist(operationType = LogOperation.OP_ADD, operationModule = LogOperation.WP_SYSTEM, describe = "用户--新增")
	@ApiOperation(value="创建用户", notes="根据User对象创建用户")
	@ApiImplicitParam(name = "macoUser", value = "用户详细实体macoUser", required = true, dataType = "MacoUser")
	public String saveMacoUser(MacoUser macoUser) {
		JSONObject js = new JSONObject();
		// 密码加密
		macoUser.setPassWord(MD5Helper.encode(macoUser.getPassWord()));
		int is_freeze = macoUser.getIsFreeze() == null ? 0 : macoUser.getIsFreeze();
		macoUser.setIsFreeze(is_freeze);
		macoUser.setCreateTime(new Date());
		macoUser.setUpdateTime(new Date());
		try {
			//新增用户, 同时返回 新增记录 主键ID
			macoUser = macoUserService.save(macoUser);
			
			//初始化用户角色为普通角色
			MacoUserRole macoUserRole = new MacoUserRole();
			macoUserRole.setUserId(macoUser.getId());
			macoUserRole.setRoleId(macoUser.getRoleId());
			macoUserRole.setCreateTime(new Date());
			macoUserRoleService.save(macoUserRole);
			js.put("id", macoUser.getId());
			js.put("flag", true);
			js.put("msg", "success");
		} catch (Exception e) {
			js.put("flag", false);
			js.put("msg", "failure");
			e.printStackTrace();
		}
		return js.toString();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@LogAssist(operationType = LogOperation.OP_DEL, operationModule = LogOperation.WP_SYSTEM, describe = "用户--删除")
	@ApiOperation(value="删除用户", notes="根据主键ID删除用户")
	@ApiImplicitParam(name = "id", value = "用户主键ID", required = true, dataType = "String")
	public String delete(String id) {
		JSONObject js = new JSONObject();
		try {
			//查询用户角色
			MacoUserRole macoUserRole = new MacoUserRole();
			macoUserRole.setUserId(id);
			List<MacoUserRole> list = macoUserRoleService.findByUserId(id);
			MacoUserRole userRole = new MacoUserRole();
			if (list != null && list.size() > 0 ) {
				userRole = list.get(0);
			}
			
			//根据主键查询角色
			MacoRole role = macoRoleService.getOne(userRole.getRoleId());
			//禁止删除管理员和VIP用户
			if (role.getClasses() == 0 || role.getClasses() == 1) {
				js.put("flag", false);
				js.put("msg", "failure");
			} else {
				//删除用户
				macoUserService.delete(id);
				//删除用户角色
				macoUserRoleService.delete(userRole.getId());
				js.put("flag", true);
				js.put("msg", "success");
			}
		} catch (Exception e) {
			js.put("flag", false);
			js.put("msg", "failure");
			e.printStackTrace();
		}
		return js.toString();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@ResponseBody
	@LogAssist(operationType = LogOperation.OP_MODIFY, operationModule = LogOperation.WP_SYSTEM, describe = "用户--修改")
	@ApiOperation(value="更新用户", notes="根据主键ID更新用户")
	@ApiImplicitParam(name = "id", value = "用户主键ID", required = true, dataType = "String")
	public String update(MacoUser newMacoUser) {
		newMacoUser.setUpdateTime(new Date());
		JSONObject js = new JSONObject();
		try {
			// 修改更新时间
			newMacoUser.setUpdateTime(new Date());
			// 密码加密
			if (newMacoUser.getPassWord() != null && newMacoUser.getPassWord().length() != 32) {
				newMacoUser.setPassWord(MD5Helper.encode(newMacoUser.getPassWord()));
			}
			
			//修改用户角色
			//1.1 根据主键查询角色
			List<MacoUserRole> list = macoUserRoleService.findByUserId(newMacoUser.getId());
			MacoUserRole userRole = new MacoUserRole();
			if (list != null && list.size() > 0 ) userRole = list.get(0);
			
			// 判断角色类型
			if (userRole.getRoleId() != null) {
				// 根据主键查询角色
				MacoRole role = macoRoleService.getOne(userRole.getRoleId());
				// 禁止修改管理员用户
				boolean flag = request.getSession(false).getAttribute(Constants.SESSION_LOGIN_ROLE).toString().contains("管理");
				if (!flag) {
					if (role.getClasses() == 0) {
						js.put("flag", false);
						js.put("msg", "failure");
						return js.toString();
					}
				}
			}
			
			//1.2 设置用户角色
			userRole.setRoleId(newMacoUser.getRoleId());
			userRole.setUpdateTime(new Date());
			// 更新用户
			macoUserService.update(newMacoUser);
			//1.3 更新用户角色
			macoUserRoleService.update(userRole);
			
			js.put("flag", true);
			js.put("msg", "success");
		} catch (Exception e) {
			js.put("flag", false);
			js.put("msg", "failure");
			e.printStackTrace();
		}
		return js.toString();

	}
	
	/**
	 * 分页计数
	 */
	@RequestMapping("/count")
	@ResponseBody
	@LogAssist(operationType = LogOperation.OP_QUERY, operationModule = LogOperation.WP_SYSTEM, describe = "用户--分页计数")
	@ApiOperation(value="统计", notes="分页计数")
	public String count(MacoUser query) {
		JSONObject js = new JSONObject();
		int count = 0;
		try {
			// 查询用户
			count = macoUserService.count(query);
			js.put("data", count);
			js.put("flag", true);
			js.put("msg", "success");
		} catch (Exception e) {
			js.put("flag", false);
			js.put("msg", "failure");
			e.printStackTrace();
		}
		return js.toString();
	}
	
	/**
	 * 分页条件查询
	 */
	@RequestMapping("/findPage")
	@LogAssist(operationType = LogOperation.OP_QUERY, operationModule = LogOperation.WP_SYSTEM, describe = "用户--分页条件查询")
	@ApiOperation(value="分页查询", notes="分页条件查询")
	public ResponseEntity<ResultVo> findPage(HttpServletRequest request, MacoUser user, Integer page, Integer limit) {
		ResultVo resultVo = new ResultVo();
		try {
			Integer pageNum = page == null ? 0 : page-1;  //注意此处,其它插件设置为1,此处从0开始.
			Integer pageSize = limit == null ? 10 : limit;
			
	        Pageable pageable = PageRequest.of(pageNum, pageSize);
	        // 执行查询
	        String loginName = user.getLoginName() == null ? null : "%" + user.getLoginName() + "%";
	        Page<Map<String,Object>> pageData = macoUserService.findPage(loginName, user.getCreateTimeBefore(), user.getCreateTimeAfter(), pageable);
 
	        // Map转List
	        List<MacoUser> list = new ArrayList<>();
	        List<Map<String, Object>> mapList = pageData.getContent();
	        for (int i = 0; i < mapList.size(); i++) {
	        	Map<String, Object> map = mapList.get(i);
	        	MacoUser entity = JSON.parseObject(JSON.toJSONString(map), MacoUser.class);
	        	list.add(entity);
	        } 
			
	        resultVo.setCount((int) pageData.getTotalElements());
	        resultVo.setData(list);
			resultVo.setFlag(true);
			resultVo.setMsg("success");
			
		} catch (Exception e) {
			resultVo.setFlag(false);
			resultVo.setMsg("failure");
			e.printStackTrace();
		}
		return ResponseEntity.ok(resultVo);
	}
	

}
