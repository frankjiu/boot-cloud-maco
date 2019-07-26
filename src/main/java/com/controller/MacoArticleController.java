package com.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.MacoArticle;
import com.domain.MacoArticleHelp;
import com.service.MacoArticleService;
import com.utils.ArrayToDbString;
import com.constant.Constants;
import com.utils.LogAssist;
import com.utils.LogOperation;

import net.sf.json.JSONObject;

/**
 * 文章控制层
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */
@Controller
@RequestMapping("/article")
public class MacoArticleController {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private MacoArticleService macoArticleService;

	/**
	 * 跳转页面
	 */
	@RequestMapping("/")
	@LogAssist(operationType = LogOperation.OP_GOTO, operationModule = LogOperation.WP_ARTICLE, describe = "文章--跳转页面")
	public String to(String id) {
		return "/article_manage";
	}
	
	/**
	 * 主键查询
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	@LogAssist(operationType = LogOperation.OP_QUERY, operationModule = LogOperation.WP_ARTICLE, describe = "文章--主键查询")
	public String getOne(String id) {
		JSONObject js = new JSONObject();
		try {
			MacoArticle macoArticle = macoArticleService.getOne(id);
			js.put("data", macoArticle);
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
	@RequestMapping("/findByTitleOrContent")
	@ResponseBody
	@LogAssist(operationType = LogOperation.OP_QUERY, operationModule = LogOperation.WP_ARTICLE, describe = "文章--条件查询")
	public String findByTitleOrContent(MacoArticle macoArticle) {
		JSONObject js = new JSONObject();
		try {
			List<MacoArticle> list = macoArticleService.findByTitleOrContent(macoArticle.getTitle(), macoArticle.getContent());
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
	@LogAssist(operationType = LogOperation.OP_QUERY, operationModule = LogOperation.WP_ARTICLE, describe = "文章--树查询")
	public String findTree(MacoArticleHelp query) {
		JSONObject js = new JSONObject();
		List<MacoArticleHelp> list = new ArrayList<>();
		// 从session获取当前用户的权限(菜单ID数组)
		Object object = request.getSession().getAttribute(Constants.SESSION_MENU_IDS_ARRAY);
		String[] menuIdsArr = (String[]) object == null ? new String[0] : (String[]) object;
		if (menuIdsArr.length == 0) {
			return null;
		}
		String menudbstr = ArrayToDbString.transform(menuIdsArr);
		query.setMenuDbStr(menudbstr);
		try {
			list = macoArticleService.findTree(query);
			List newList = new ArrayList<>();
			for(MacoArticleHelp article : list){
				Map<Object,Object> map  = new LinkedHashMap<>();
				//TB.ID, TB.PID, TB.MNAME, TB.INDEX_ORDER, TB.UPDATE_TIME
				map.put("id", article.getId());
				map.put("pid", article.getPid());
				map.put("name", article.getMenuName());
				/*map.put("times", article.getTimes());
				map.put("keyword", article.getKeyword());
				map.put("content", article.getContent());
				map.put("author", article.getAuthor());
				map.put("update_time", article.getUpdate_time());
				map.put("create_time", article.getCreate_time());*/
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
	 * 新增
	 */
	@RequestMapping("/save")
	@ResponseBody
	@LogAssist(operationType = LogOperation.OP_ADD, operationModule = LogOperation.WP_ARTICLE, describe = "文章--新增")
	public String saveMacoArticle(MacoArticle macoArticle) {
		JSONObject js = new JSONObject();
		try {
			String author = request.getSession().getAttribute(Constants.SESSION_LOGIN_NAME).toString();
			macoArticle.setAuthor(author);
			macoArticle.setCreateTime(new Date());
			macoArticle.setUpdateTime(new Date());
			macoArticle = macoArticleService.save(macoArticle);
			js.put("id", macoArticle.getId());
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
	@LogAssist(operationType = LogOperation.OP_DEL, operationModule = LogOperation.WP_ARTICLE, describe = "文章--删除")
	public String delete(String id) {
		JSONObject js = new JSONObject();
		try {
			macoArticleService.delete(id);
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
	@LogAssist(operationType = LogOperation.OP_MODIFY, operationModule = LogOperation.WP_ARTICLE, describe = "文章--修改")
	public String update(MacoArticle newMacoArticle) {
		JSONObject js = new JSONObject();
		try {
			String id = newMacoArticle.getId();
			MacoArticle oldMacoArticle = macoArticleService.getOne(id);
			oldMacoArticle.setPid(newMacoArticle.getPid());
			oldMacoArticle.setTitle(newMacoArticle.getTitle());
			oldMacoArticle.setContent(newMacoArticle.getContent());
			oldMacoArticle.setUpdateTime(new Date());
			macoArticleService.update(oldMacoArticle);
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
