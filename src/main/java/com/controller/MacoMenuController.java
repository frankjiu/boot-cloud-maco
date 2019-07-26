package com.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.MacoMenu;
import com.service.MacoMenuService;
import com.constant.Constants;
import com.utils.LogAssist;
import com.utils.LogOperation;
import com.utils.TreeUtils;

import net.sf.json.JSONObject;

/**
 * 菜单控制层
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */
@Controller
@RequestMapping("/menu")
public class MacoMenuController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private MacoMenuService macoMenuService;

	/**
	 * 跳转页面
	 */
	@RequestMapping("/")
	@LogAssist(operationType = LogOperation.OP_GOTO, operationModule = LogOperation.WP_SYSTEM, describe = "菜单--跳转页面")
	public String to(String id) {
		return "/menu_manage";
	}
	
	/**
	 * 主键查询
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	@LogAssist(operationType = LogOperation.OP_QUERY, operationModule = LogOperation.WP_SYSTEM, describe = "菜单--主键查询")
	public String getOne(String id) {
		JSONObject js = new JSONObject();
		try {
			MacoMenu obj = macoMenuService.getOne(id);
			js.put("data", obj);
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
	@RequestMapping("/getByAuth")
	@ResponseBody
	@LogAssist(operationType = LogOperation.OP_QUERY, operationModule = LogOperation.WP_SYSTEM, describe = "菜单--条件查询")
	@SuppressWarnings("unchecked")
	public String getByAuth(MacoMenu query) {
		JSONObject js = new JSONObject();
		try {
			HttpSession session = request.getSession(false);
			String loginRoleName = session.getAttribute(Constants.SESSION_LOGIN_ROLE).toString();
			
			// 加入权限
			List<String> menuList = (List<String>) session.getAttribute(Constants.SESSION_MENU_LIST);
			if (menuList == null) menuList = new ArrayList<String>();
			String[] authArr = menuList.toArray(new String[menuList.size()]);
			// 如果为超级管理员,取消权限控制
			if (loginRoleName.contains("管理")) {
				authArr = null;
			}
			List<MacoMenu> list = macoMenuService.getByAuth(query, authArr);
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
	 * 树查询
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/findTree")
	@ResponseBody
	@LogAssist(operationType = LogOperation.OP_QUERY, operationModule = LogOperation.WP_SYSTEM, describe = "菜单--树查询")
	public String findTree(String id) {
		JSONObject js = new JSONObject();
		List<MacoMenu> list = null;
		try {
			HttpSession session = request.getSession(false);
			String loginRoleName = session.getAttribute(Constants.SESSION_LOGIN_ROLE).toString();
			
			// 加入权限
			List<String> menuList = (List<String>) session.getAttribute(Constants.SESSION_MENU_LIST);
			if (menuList == null) menuList = new ArrayList<String>();
			String[] authArr = menuList.toArray(new String[menuList.size()]);
			// 如果为超级管理员,取消权限控制
			if (loginRoleName.contains("管理")) {
				authArr = null;
			}
			
			list = macoMenuService.findTree(authArr);
			List newList = new ArrayList<>();
			for(MacoMenu menu : list){
				Map<Object,Object> map  = new LinkedHashMap<>();
				map.put("id", menu.getId());
				map.put("name", menu.getMenuName());
				map.put("menu_url", menu.getMenuUrl());
				map.put("pid", menu.getPid());
				map.put("menu_level", menu.getMenuLevel());
				map.put("index_order", menu.getIndexOrder());
				map.put("create_time", menu.getCreateTime());
				map.put("update_time", menu.getUpdateTime());
				newList.add(map);
			}
			js.put("data", newList);
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
	 * 自定义Recurse递归查询子集(以根目录主键ID为条件)
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getRecurse")
	@ResponseBody
	@LogAssist(operationType = LogOperation.OP_QUERY, operationModule = LogOperation.WP_SYSTEM, describe = "菜单--自定义Recurse递归查询子集")
	public String getRecurse(MacoMenu query) {
		JSONObject js = new JSONObject();
		List<MacoMenu> list = new ArrayList<MacoMenu>();
		try {
			HttpSession session = request.getSession(false);
			String loginRoleName = session.getAttribute(Constants.SESSION_LOGIN_ROLE).toString();
			
			// 加入权限
			List<String> menuList = (List<String>) session.getAttribute(Constants.SESSION_MENU_LIST);
			if (menuList == null) menuList = new ArrayList<String>();
			String[] authArr = menuList.toArray(new String[menuList.size()]);
			// 如果为超级管理员,取消权限控制
			if (loginRoleName.contains("管理")) {
				authArr = null;
			}
			
			// 根目录集合数据
			list = macoMenuService.getByAuth(query, authArr);
			
			// 调用递归函数
			List<MacoMenu> newList = TreeUtils.build(list);
			
			js.put("data", newList);
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
	@LogAssist(operationType = LogOperation.OP_ADD, operationModule = LogOperation.WP_SYSTEM, describe = "菜单--新增")
	public String saveMacoMenu(MacoMenu macoMenu) {
		JSONObject js = new JSONObject();
		macoMenu.setCreateTime(new Date());
		macoMenu.setUpdateTime(new Date());
		try {
			macoMenu = macoMenuService.save(macoMenu);
			js.put("id", macoMenu.getId());
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
	@LogAssist(operationType = LogOperation.OP_DEL, operationModule = LogOperation.WP_SYSTEM, describe = "菜单--删除")
	public String delete(String id) {
		JSONObject js = new JSONObject();
		try {
			//禁止删除根菜单和一级菜单
			MacoMenu menu = macoMenuService.getOne(id);
			if ((menu.getMenuLevel() != null && menu.getMenuLevel().equals(0)) || (menu.getMenuLevel() != null && menu.getMenuLevel().equals(1))) {
				js.put("flag", false);
				js.put("msg", "failure");
			} else {
				macoMenuService.delete(id);
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
	@LogAssist(operationType = LogOperation.OP_MODIFY, operationModule = LogOperation.WP_SYSTEM, describe = "菜单--修改")
	public String update(MacoMenu newMacoMenu) {
		JSONObject js = new JSONObject();
		try {
			// 查询原菜单数据
			MacoMenu menu = macoMenuService.getOne(newMacoMenu.getId());
			
			// 禁止修改根菜单
			if (menu.getMenuLevel().equals(0)) {
				js.put("flag", false);
				return js.toString();
			}
			newMacoMenu.setCreateTime(menu.getCreateTime());
			newMacoMenu.setUpdateTime(new Date());
			macoMenuService.update(newMacoMenu);
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
