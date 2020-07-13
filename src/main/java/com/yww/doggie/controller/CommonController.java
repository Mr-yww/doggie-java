package com.yww.doggie.controller;

import java.io.IOException;
import java.util.Date;

import network.WebTTS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.yww.doggie.interceptor.Auth;
import com.yww.doggie.service.SansanService;

@CrossOrigin
@Auth
@RestController
@RequestMapping("/sansan/common")
public class CommonController {
	
	private static final Log Log = LogFactory.getLog(com.yww.doggie.controller.SansanController.class);
	
	@Autowired SansanService sansanService;
	
	@PostMapping("getXFAudio")
	public JSONObject commitStar(@RequestBody String param) {
		
		JSONObject paramJSON = JSONObject.parseObject(param);
		String text = paramJSON.getString("text");
		
		int error = 1;
		String info = "参数为空";
		Date ts = new java.util.Date();
		String audioURL = "";
		
		Log.trace("param text: " + text);
		if (text != null && text.length() > 0) {
			try {
				audioURL = WebTTS.createAudio(text);
				Log.trace("audioURL : " + audioURL);
				if (audioURL != null) {
					error = 0;
					info = "音频已合成";
				} else {
					info = "语音合成失败！";
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				audioURL = null;
				error = 1;
				info = "语音合成失败！";
			}
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("error", error);
		jsonObject.put("info", info);
		jsonObject.put("ts", ts);
		if (audioURL != null) {
			jsonObject.put("audioURL", audioURL);
		}
		return jsonObject;
	}
}
