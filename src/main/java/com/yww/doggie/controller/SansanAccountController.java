package com.yww.doggie.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.yww.doggie.interceptor.Auth;
import com.yww.doggie.entity.SansanUser;
import com.yww.doggie.service.ResponseObj;
import com.yww.doggie.service.SansanService;

@CrossOrigin
@Auth
@RestController
@RequestMapping("/sansan/account")
public class SansanAccountController {

	private static final Log Log = LogFactory.getLog(SansanController.class);
	
	@Autowired SansanService sansanService;
	
	
	/**
	 * @param user
	 * @return
	 */
	@PostMapping("regist")
	public JSONObject registUser(@RequestBody SansanUser user) {
		if (Log.isTraceEnabled()) {
			Log.trace("插入数据库 nick_name: " + user.getNick_name());
		}
		ResponseObj res = sansanService.registUser(user.getNick_name(), user.getPassword());
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("error", res.getErrorCode());
		jsonObject.put("info", res.getInfo());
		jsonObject.put("ts", res.getTs());
		
		return jsonObject;
	}
	
}
