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
import com.domain.MacoLogs;
import com.service.MacoLogsService;
import com.utils.LogAssist;
import com.utils.LogOperation;
import com.utils.ResultVo;

import net.sf.json.JSONObject;

/**
 * 日志控制层
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */
@Controller
@RequestMapping("/logs")
public class MacoLogsController {

	@Autowired
	private MacoLogsService macoLogsService;

	/**
	 * 跳转页面
	 */
	@RequestMapping("/")
	@LogAssist(operationType = LogOperation.OP_GOTO, operationModule = LogOperation.WP_SYSTEM, describe = "日志--跳转页面")
	public String to(String id) {
		return "/logs_manage";
	}
	
	/**
	 * 主键查询
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	@LogAssist(operationType = LogOperation.OP_QUERY, operationModule = LogOperation.WP_SYSTEM, describe = "日志--主键查询")
	public String getOne(String id) {
		JSONObject js = new JSONObject();
		try {
			MacoLogs macoLogs = macoLogsService.getOne(id);
			js.put("data", macoLogs);
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
	@LogAssist(operationType = LogOperation.OP_QUERY, operationModule = LogOperation.WP_SYSTEM, describe = "日志--条件查询")
	public String findByUserId(MacoLogs query) {
		JSONObject js = new JSONObject();
		try {
			List<MacoLogs> list = macoLogsService.findByUserId(query.getUserId());
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
	@RequestMapping("/save1")
	@ResponseBody
	@LogAssist(operationType = LogOperation.OP_ADD, operationModule = LogOperation.WP_SYSTEM, describe = "日志--新增")
	public String saveMacoLogs() {
		JSONObject js = new JSONObject();
		try {
			// 测试数据
			MacoLogs macoLogs = new MacoLogs();
			macoLogs.setUserId("4028abbd69b495f40169b5b941990000");
			macoLogs.setIpAddress("192.168.1.51");
			macoLogs.setIntroduce("列表查询");
			macoLogs.setOpModule("查询");
			macoLogs.setCreateTime(new Date());
			
			macoLogs = macoLogsService.save(macoLogs);
			js.put("id", macoLogs.getId());
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
	@LogAssist(operationType = LogOperation.OP_QUERY, operationModule = LogOperation.WP_SYSTEM, describe = "日志--分页查询")
	public ResponseEntity<ResultVo> findPage(HttpServletRequest request, MacoLogs logs, Integer page, Integer limit) {
		ResultVo resultVo = new ResultVo();
		try {
			Integer pageNum = page == null ? 0 : page-1;  //注意此处,其它插件设置为1,此处从0开始.
			Integer pageSize = limit == null ? 10 : limit;
			
	        Pageable pageable = PageRequest.of(pageNum, pageSize);
	        // 执行查询
	        String userId = logs.getUserId() == null ? null : "%" + logs.getUserId() + "%";
	        Page<Map<String,Object>> pageData = macoLogsService.findPage(userId, logs.getCreateTimeBefore(), logs.getCreateTimeAfter(), pageable);
 
	        // Map转List
	        List<MacoLogs> list = new ArrayList<>();
	        List<Map<String, Object>> mapList = pageData.getContent();
	        for (int i = 0; i < mapList.size(); i++) {
	        	Map<String, Object> map = mapList.get(i);
	        	MacoLogs entity = JSON.parseObject(JSON.toJSONString(map), MacoLogs.class);
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
