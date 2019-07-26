package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.MacoUserRole;
import com.service.MacoUserRoleService;
import com.utils.LogAssist;
import com.utils.LogOperation;

import net.sf.json.JSONObject;


/**
 * 用户角色控制层
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */
@Controller
@RequestMapping("/urole")
public class MacoUserRoleController {

	@Autowired
	private MacoUserRoleService macoUserRoleService;

	/**
	 * 主键查询
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	@LogAssist(operationType = LogOperation.OP_QUERY, operationModule = LogOperation.WP_SYSTEM, describe = "用户角色--主键查询")
	public String getOne(String id) {
		JSONObject js = new JSONObject();
		MacoUserRole macoUserRole = macoUserRoleService.getOne(id);
		try {
			js.put("data", macoUserRole);
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
	@RequestMapping("/findByUserId")
	@ResponseBody
	@LogAssist(operationType = LogOperation.OP_QUERY, operationModule = LogOperation.WP_SYSTEM, describe = "用户角色--条件查询")
	public String findByUserId(String userId) {
		JSONObject js = new JSONObject();
		try {
			List<MacoUserRole> list = new ArrayList<MacoUserRole>();
			MacoUserRole macoUserRole = new MacoUserRole();
			list = macoUserRoleService.findByUserId(userId);
			if (list != null && list.size() > 0) {
				macoUserRole = list.get(0);
			}
			js.put("data", macoUserRole);
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
	@LogAssist(operationType = LogOperation.OP_ADD, operationModule = LogOperation.WP_SYSTEM, describe = "用户角色--新增")
	public String saveMacoUserRole(MacoUserRole macoUserRole) {
		JSONObject js = new JSONObject();
		try {
			macoUserRole = macoUserRoleService.save(macoUserRole);
			js.put("id", macoUserRole.getId());
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
	@LogAssist(operationType = LogOperation.OP_DEL, operationModule = LogOperation.WP_SYSTEM, describe = "用户角色--删除")
	public String delete(String id) {
		JSONObject js = new JSONObject();
		try {
			macoUserRoleService.delete(id);
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
	 * 修改
	 */
	@RequestMapping("/update")
	@ResponseBody
	@LogAssist(operationType = LogOperation.OP_MODIFY, operationModule = LogOperation.WP_SYSTEM, describe = "用户角色--修改")
	public String update(String id) {
		JSONObject js = new JSONObject();
		try {
			MacoUserRole newMacoUserRole = new MacoUserRole();
			macoUserRoleService.update(newMacoUserRole);
			js.put("flag", true);
			js.put("msg", "success");
		} catch (Exception e) {
			js.put("flag", false);
			js.put("msg", "failure");
			e.printStackTrace();
		}
		return js.toString();
	}

}
