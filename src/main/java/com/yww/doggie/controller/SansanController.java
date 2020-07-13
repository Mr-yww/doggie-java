package com.yww.doggie.controller;

import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.yww.doggie.interceptor.Auth;
import com.yww.doggie.entity.SanComment;
import com.yww.doggie.entity.SanStar;
import com.yww.doggie.entity.SansanUser;
import com.yww.doggie.service.ResponseObj;
import com.yww.doggie.service.SansanService;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

@CrossOrigin
@Auth
@RestController
@RequestMapping("/sansan/user")
public class SansanController {
	
	private static final Log Log = LogFactory.getLog(SansanController.class);
	
	@Autowired SansanService sansanService;
	
	@PostMapping("")
	public JSONObject getUserInfoById(@RequestBody SansanUser userBody) {
		if (Log.isTraceEnabled()) {
			Log.trace("getUserInfoById(); " + userBody.getOid());
		}
		
		SansanUser user = sansanService.getUser(userBody.getOid());
		JSONObject jsonObject = new JSONObject();
		if (user != null) {
			String jsonStr = JSONObject.toJSONString(user);
			jsonObject = JSONObject.parseObject(jsonStr);
			jsonObject.put("error", 0);
			jsonObject.put("info", "OK");
			jsonObject.put("ts", System.currentTimeMillis());
		} else {
			jsonObject.put("error", 1);
			jsonObject.put("info", "无此用户");
			jsonObject.put("ts", System.currentTimeMillis());
		}
		return jsonObject;
	}
	
	@PostMapping("login")
	public JSONObject getUserInfoByName(@RequestBody SansanUser userBody) {
		
		SansanUser user = sansanService.getUserByNickname(userBody.getNick_name());
		JSONObject jsonObject = new JSONObject();
		if (user != null) {
			if (!user.getPassword().equals(userBody.getPassword())) {
				jsonObject.put("error", 1);
				jsonObject.put("info", "密码错误");
				jsonObject.put("mysql_pw", user.getPassword());
				jsonObject.put("param_pw", userBody.getPassword());
				jsonObject.put("ts", System.currentTimeMillis());
			} else {
				String jsonStr = JSONObject.toJSONString(user);
				jsonObject = JSONObject.parseObject(jsonStr);
				jsonObject.put("error", 0);
				jsonObject.put("info", "OK");
				jsonObject.put("ts", System.currentTimeMillis());
			}
		} else {
			jsonObject.put("error", 1);
			jsonObject.put("info", "无此用户");
			jsonObject.put("ts", System.currentTimeMillis());
		}
		return jsonObject;
	}
	
	@PostMapping("commitStar")
	public JSONObject commitStar(@RequestBody String param) {
		
		JSONObject paramJSON = JSONObject.parseObject(param);
		String content = paramJSON.getString("content");
		int user_oid = paramJSON.getIntValue("user_oid");
		
		ResponseObj responseObj = sansanService.commitStar(user_oid, content);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("error", responseObj.getErrorCode());
		jsonObject.put("info", responseObj.getInfo());
		jsonObject.put("ts", responseObj.getTs());
		return jsonObject;
	}
	
	@PostMapping("getStarByOid")
	public JSONObject getStarByOid(@RequestBody String param) {
		
		JSONObject paramJSON = JSONObject.parseObject(param);
		String star_oid = paramJSON.getString("star_oid");
		
		SanStar star = sansanService.getStarByOid(star_oid);
		JSONObject jsonObject = new JSONObject();
		if (star != null) {
			String jsonStr = JSONObject.toJSONString(star);
			jsonObject = JSONObject.parseObject(jsonStr);
			jsonObject.put("error", 0);
			jsonObject.put("info", "OK");
			jsonObject.put("ts", System.currentTimeMillis());
		} else {
			jsonObject.put("error", 1);
			jsonObject.put("info", "该Star不存在");
			jsonObject.put("ts", System.currentTimeMillis());
		}
		return jsonObject;
	}
	
	@PostMapping("commitComment")
	public JSONObject commitComment(@RequestBody String param) {
		
		JSONObject paramJSON = JSONObject.parseObject(param);
		String content = paramJSON.getString("content");
		String star_oid = paramJSON.getString("star_oid");
		int from_user_oid = paramJSON.getIntValue("from_user_oid");
		int to_user_oid = paramJSON.getIntValue("to_user_oid");
		
		ResponseObj responseObj = sansanService.commitComment(star_oid, content, from_user_oid, to_user_oid);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("error", responseObj.getErrorCode());
		jsonObject.put("info", responseObj.getInfo());
		jsonObject.put("ts", responseObj.getTs());
		return jsonObject;
	}
	
	@PostMapping("getCommentByStarOid")
	public JSONObject getCommentByStarOid(@RequestBody String param) {
		
		JSONObject paramJSON = JSONObject.parseObject(param);
		String star_oid = paramJSON.getString("star_oid");
		
		List<SanComment> comments = sansanService.getCommentByStarOid(star_oid);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("error", 0);
		jsonObject.put("info", "OK");
		jsonObject.put("ts", System.currentTimeMillis());
		jsonObject.put("comments", comments);
		return jsonObject;
	}
	
	@PostMapping("getCommentByOid")
	public JSONObject getCommentByOid(@RequestBody String param) {
		
		JSONObject paramJSON = JSONObject.parseObject(param);
		String oid = paramJSON.getString("oid");
		
		SanComment comment = sansanService.getCommentByOid(oid);
		
		JSONObject jsonObject = new JSONObject();
		String jsonStr = JSONObject.toJSONString(comment);
		jsonObject = JSONObject.parseObject(jsonStr);
		jsonObject.put("error", 0);
		jsonObject.put("info", "OK");
		jsonObject.put("ts", System.currentTimeMillis());
		return jsonObject;
	}
	
	
	@PostMapping("getStars")
	public JSONObject getStars(@RequestBody String param) {
		
		JSONObject paramJSON = JSONObject.parseObject(param);
		int offset = paramJSON.getIntValue("offset");
		int limit = paramJSON.getIntValue("limit");
		
		List<SanStar> stars = sansanService.getStars(offset, limit);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("error", 0);
		jsonObject.put("info", "OK");
		jsonObject.put("ts", System.currentTimeMillis());
		jsonObject.put("stars", stars);
		return jsonObject;
	}
	
	@PostMapping("getStarsByUserOid")
	public JSONObject getStarsByUserOid(@RequestBody String param) {
		JSONObject paramJSON = JSONObject.parseObject(param);
		int user_oid = paramJSON.getIntValue("user_oid");
		List<SanStar> stars = sansanService.getStarsByUserOid(user_oid);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("error", 0);
		jsonObject.put("info", "OK");
		jsonObject.put("ts", System.currentTimeMillis());
		jsonObject.put("stars", stars);
		return jsonObject;
	}
	
	@PostMapping("hug")
	public JSONObject hug(@RequestBody String param) {
		
		JSONObject paramJSON = JSONObject.parseObject(param);
		String star_oid = paramJSON.getString("star_oid");
		
		ResponseObj responseObj = sansanService.hug(star_oid);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("error", responseObj.getErrorCode());
		jsonObject.put("info", responseObj.getInfo());
		jsonObject.put("ts", responseObj.getTs());
		return jsonObject;
	}
	
	@PostMapping("calm")
	public JSONObject calm(@RequestBody String param) {
		
		JSONObject paramJSON = JSONObject.parseObject(param);
		String star_oid = paramJSON.getString("star_oid");
		
		ResponseObj responseObj = sansanService.calm(star_oid);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("error", responseObj.getErrorCode());
		jsonObject.put("info", responseObj.getInfo());
		jsonObject.put("ts", responseObj.getTs());
		return jsonObject;
	}
	
	@PostMapping("updateCommentNum")
	public JSONObject updateCommentNum(@RequestBody String param) {
		
		JSONObject paramJSON = JSONObject.parseObject(param);
		String star_oid = paramJSON.getString("star_oid");
		
		ResponseObj responseObj = sansanService.updateCommentNum(star_oid);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("error", responseObj.getErrorCode());
		jsonObject.put("info", responseObj.getInfo());
		jsonObject.put("ts", responseObj.getTs());
		return jsonObject;
	}
	
	@PostMapping("updateZanNum")
	public JSONObject updateZanNum(@RequestBody String param) {
		
		JSONObject paramJSON = JSONObject.parseObject(param);
		String comment_oid = paramJSON.getString("comment_oid");
		
		ResponseObj responseObj = sansanService.updateZanNum(comment_oid);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("error", responseObj.getErrorCode());
		jsonObject.put("info", responseObj.getInfo());
		jsonObject.put("ts", responseObj.getTs());
		return jsonObject;
	}
	
	@PostMapping("getImageList")
	public JSONObject getImageList(@RequestBody String param) {
		
		JSONObject paramJSON = JSONObject.parseObject(param);
		String time_str = paramJSON.getString("time");
		
		List<String> images = sansanService.getImages(time_str);
		JSONObject jsonObject = new JSONObject();
		if (images != null) {
			jsonObject.put("images", images);
			jsonObject.put("error", 0);
			jsonObject.put("info", "OK");
			jsonObject.put("ts", System.currentTimeMillis());
		} else {
			jsonObject.put("error", 1);
			jsonObject.put("info", "无图片");
			jsonObject.put("ts", System.currentTimeMillis());
		}
		return jsonObject;
	}
	
	@PostMapping("getContentTimeList")
	public JSONObject getContentTimeList() {
		
		List<String> images = sansanService.getAllImages();
		List<String> time_list = new LinkedList<>();
		
		for(int i = 0;i<images.size();i++) {
			String temp = images.get(i);
			if (temp.length() >= 10) {
				String temp_time = temp.substring(0, 10);
				if (!time_list.contains(temp_time)) {
					time_list.add(temp_time);
				}
			}
		}
		
		JSONObject jsonObject = new JSONObject();
		if (images != null && images.size() > 0) {
			jsonObject.put("time_list", time_list);
			jsonObject.put("error", 0);
			jsonObject.put("info", "OK");
			jsonObject.put("ts", System.currentTimeMillis());
		} else {
			jsonObject.put("error", 1);
			jsonObject.put("info", "无图片");
			jsonObject.put("ts", System.currentTimeMillis());
		}
		return jsonObject;
	}
}
