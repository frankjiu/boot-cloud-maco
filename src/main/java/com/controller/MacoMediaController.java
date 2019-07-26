package com.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.MacoMedia;
import com.service.MacoMediaService;
import com.utils.LogAssist;
import com.utils.LogOperation;

import net.sf.json.JSONObject;

/**
 * 多媒体控制层
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */
@Controller
@RequestMapping("/media")
public class MacoMediaController {

	@Autowired
	private MacoMediaService macoMediaService;

	/**
	 * 主键查询
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	@LogAssist(operationType = LogOperation.OP_QUERY, operationModule = LogOperation.WP__MEDIA, describe = "媒体--主键查询")
	public String getOne(String id) {
		JSONObject js = new JSONObject();
		try {
			MacoMedia macoMedia = macoMediaService.getOne(id);
			js.put("data", macoMedia);
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
	@RequestMapping("/getBy")
	@ResponseBody
	@LogAssist(operationType = LogOperation.OP_QUERY, operationModule = LogOperation.WP__MEDIA, describe = "媒体--条件查询")
	public String getBy(MacoMedia query) {
		JSONObject js = new JSONObject();
		try {
			List<MacoMedia> list = macoMediaService.findByTitle(query.getTitle());
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
	@LogAssist(operationType = LogOperation.OP_ADD, operationModule = LogOperation.WP__MEDIA, describe = "媒体--新增")
	public String saveMacoMedia() {
		JSONObject js = new JSONObject();
		// 测试数据
		MacoMedia macoMedia = new MacoMedia();
		macoMedia.setAuthor("Admin");
		macoMedia.setMediaType(1); //媒体类型  1:图片 2:歌曲 3:视频
		macoMedia.setMediaSource(null); //媒体数据
		macoMedia.setTimes(7);
		macoMedia.setTitle("86版西游记");
		macoMedia.setCreateTime(new Date());
		macoMedia.setUpdateTime(new Date());
		try {
			macoMedia = macoMediaService.save(macoMedia);
			js.put("id", macoMedia.getId());
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
	@LogAssist(operationType = LogOperation.OP_DEL, operationModule = LogOperation.WP__MEDIA, describe = "媒体--删除")
	public String delete(String id) {
		JSONObject js = new JSONObject();
		try {
			macoMediaService.delete(id);
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
	@LogAssist(operationType = LogOperation.OP_MODIFY, operationModule = LogOperation.WP__MEDIA, describe = "媒体--修改")
	public String update(String id) {
		// 测试数据
		MacoMedia newMacoMedia = new MacoMedia();
		newMacoMedia.setId(id);
		newMacoMedia.setAuthor("VIP");
		newMacoMedia.setMediaType(2); //媒体类型  1:图片 2:歌曲 3:视频
		newMacoMedia.setMediaSource(null); //媒体数据
		newMacoMedia.setTimes(11);
		newMacoMedia.setTitle("新版三国演义");
		newMacoMedia.setCreateTime(new Date());
		newMacoMedia.setUpdateTime(new Date());

		JSONObject js = new JSONObject();
		try {
			macoMediaService.update(newMacoMedia);
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
