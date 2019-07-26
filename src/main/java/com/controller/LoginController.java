package com.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.MacoRole;
import com.domain.MacoRoleMenu;
import com.domain.MacoUser;
import com.domain.MacoUserRole;
import com.service.MacoMenuService;
import com.service.MacoRoleMenuService;
import com.service.MacoRoleService;
import com.service.MacoUserRoleService;
import com.service.MacoUserService;
import com.constant.Constants;
import com.utils.LogAssist;
import com.utils.LogOperation;
import com.utils.MD5Helper;

import net.sf.json.JSONObject;

/**
 * 登录控制层
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */
@Controller
@RequestMapping("/")
public class LoginController {

	private final static Log LOGS = LogFactory.getLog(LoginController.class);

	@Autowired
	MacoUserService macoUserService;
	
	@Autowired
	MacoUserRoleService macoUserRoleService;
	
	@Autowired
	MacoRoleService macoRoleService;
	
	@Autowired
	MacoRoleMenuService macoRoleMenuService;
	
	@Autowired
	MacoMenuService macoMenuService;
	
	/**
	 * 登录系统
	 */
	@RequestMapping(value = "/checkLogin", method = RequestMethod.POST)
	@ResponseBody
	@LogAssist(operationType = LogOperation.OP_LOGIN, operationModule = LogOperation.WP_SYSTEM, describe = "登录系统")
	public String checkLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session, MacoUser macoUser, String menuRootId) {
		// 获取session值使用false, 有就返回值, 否则返回null
		String innerCode = (String) request.getSession().getAttribute("imgCode");
		JSONObject js = new JSONObject();
		try {
			//密码加密
			macoUser.setPassWord(MD5Helper.encode(macoUser.getPassWord()));
			List<MacoUser> userList = macoUserService.findByLoginNameAndPassWord(macoUser);
			MacoUser loginUser = null;
			if (userList != null && userList.size() > 0) {
				loginUser = userList.get(0);
				loginUser.setCode(macoUser.getCode());
			}
			// 若:卡密信息不正确
			if (loginUser == null) {
				js.put("code", 4001);
				js.put("flag", false);
				js.put("msg", "用户名或密码错误!");
			// 卡密信息正确,但验证码错误
			} else if (loginUser != null && !innerCode.equalsIgnoreCase(loginUser.getCode())) {
				js.put("code", 4002);
				js.put("flag", false);
				js.put("msg", "验证码错误!");
			// 都正确但被冻结
			} else if (loginUser != null && innerCode.equalsIgnoreCase(loginUser.getCode()) && loginUser.getIsFreeze().equals(1)) {
				js.put("code", 4004);
				js.put("flag", false);
				js.put("msg", "账号已冻结!");
			// 都正确且未被冻结
			} else if (loginUser != null && innerCode.equalsIgnoreCase(loginUser.getCode()) && loginUser.getIsFreeze().equals(0)) {
				js.put("code", 200);
				js.put("flag", true);
				js.put("msg", "登录成功!");
				MacoUser user = loginUser;
				// 将登录用户信息存入session
				// 用户ID
				session.setAttribute(Constants.SESSION_LOGIN_ID, user.getId());
				// 用户账号
				session.setAttribute(Constants.SESSION_LOGIN_NAME, user.getLoginName());
				// 用户角色
				MacoUserRole userRole = new MacoUserRole();
				List<MacoUserRole> list = macoUserRoleService.findByUserId(user.getId());
				if (list != null && list.size() > 0 ) userRole = list.get(0);
				MacoRole role = macoRoleService.getOne(userRole.getRoleId());
				session.setAttribute(Constants.SESSION_LOGIN_ROLE_ID, role.getId());
				session.setAttribute(Constants.SESSION_LOGIN_ROLE, role.getRoleName());
				// 用户权限(用户角色--拥有的菜单ids: 根据用户角色ID查询菜单ID)
				List<MacoRoleMenu> roleMenuList = macoRoleMenuService.getByRoleId(role.getId());
				List<String> menuIdList = new ArrayList<String>();
				
				if (roleMenuList != null && roleMenuList.size() > 0) {
					for (int i = 0; i < roleMenuList.size(); i++) {
						String menu_id = roleMenuList.get(i).getMenuId();
						menuIdList.add(menu_id);
					}
				}
				session.setAttribute(Constants.SESSION_MENU_LIST, menuIdList);
				
				// String[] menuIdArray = (String[]) menuIdList.toArray();
				// 集合转数组
				String[] menuIdArray= (String[]) menuIdList.toArray(new String[menuIdList.size()]);
				
				session.setAttribute(Constants.SESSION_MENU_IDS_ARRAY, menuIdArray);
				// 更新用户登录时间
				user.setLoginTime(new Date());
				macoUserService.update(user);
				// 清除密码
				user.setPassWord("");
				// 设置session失效时间30分钟
				session.setMaxInactiveInterval(60*30);
				LOGS.warn("<<<<<<<<<<<<<<<<<<<  登录验证成功!  >>>>>>>>>>>>>>>>>>>");
			} else {
				js.put("code", 50000);
				js.put("flag", false);
				js.put("msg", "系统异常!");
			}
		} catch (Exception e) {
			js.put("code", 4003);
			js.put("flag", false);
			js.put("msg", "登录异常!");
			e.printStackTrace();
		}
		return js.toString();
	}
	
	/**
	 * 查询用户菜单权限
	 */
	/*@RequestMapping("findMenu")
	@ResponseBody
	public String findMenu(HttpServletRequest request, HttpServletResponse response, String menuRootId) {
		JSONObject js = new JSONObject();
		try {
			HttpSession session = request.getSession(false);
			if (session == null) {
				return null;
			}
			String userId = (String) session.getAttribute(Constants.SESSION_LOGIN_ID);
			List<MacoMenu> list = new ArrayList<MacoMenu>();
			List<String> menuIdList = new ArrayList<String>();
			// 根据登录id查询登陆者可访问菜单权限
			List<MacoMenu> parentList = macoUserService.findParentMenu(userId, menuRootId);
			List<MacoMenu> childMenuList = new ArrayList<MacoMenu>();
			for (int i = 0; i < parentList.size(); i++) {
				MacoMenu parentMenu = parentList.get(i);
				String parentMenuId = String.valueOf(parentMenu.getId());
				childMenuList = macoUserService.findChildMenu(userId, parentMenuId);
				parentMenu.setChildren(childMenuList);
				list.add(parentMenu);
				menuIdList.add(parentMenu.getId());
			}
			// 将当前用户的权限-可访问的菜单 名称和URL 放入session
			session.setAttribute(Constants.SESSION_MENU_LIST, list);
			String[] menuIdArray = (String[]) menuIdList.toArray();
			session.setAttribute(Constants.SESSION_MENU_IDS_ARRAY, menuIdArray);
			
			js.put("data", list);
			js.put("msg", "ok");
		} catch (Exception e) {
			js.put("msg", "no");
			e.printStackTrace();
		}
		return js.toString();
	}*/

	/**
	 * 退出登录
	 */
	@RequestMapping(value = "logout")
	@LogAssist(operationType = LogOperation.OP_LOGIN, operationModule = LogOperation.WP_SYSTEM, describe = "退出登录")
	public String logout(HttpServletRequest request) {
		try {
			//若存在会话则返回该会话,否则返回NULL
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.setMaxInactiveInterval(0);
				session.invalidate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:login";
	}
	
}
