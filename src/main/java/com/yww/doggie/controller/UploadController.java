package com.yww.doggie.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.yww.doggie.service.SansanService;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

@CrossOrigin
@RestController
@RequestMapping("/sansan/upload")
public class UploadController {
	
	private static final Log Log = LogFactory.getLog(UploadController.class);
	
	@Autowired SansanService sansanService;
	
	@PostMapping("")
	public JSONObject upload(@RequestParam("file") MultipartFile file) {
		
		String msg = "";
		int error_code = 0;
		if (file.isEmpty()) {
			msg = "上传失败，请选择文件";
			error_code = 1;
        }

        String fileName = file.getOriginalFilename();
        String filePath = "/root/image/";
        String thum_filePath = "/root/thum/";
        String[] sourceStrArray = fileName.split("_", 2);
        String name = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        if (sourceStrArray.length < 2) {
			name = sdf.format(d)+"_"+String.valueOf(System.currentTimeMillis())+"_"+fileName;
		} else {
			try {
					d = sdf.parse(sourceStrArray[0]);
				} catch (ParseException e) {
					d = new Date();
				}
			name = sdf.format(d)+"_"+String.valueOf(System.currentTimeMillis())+"_"+sourceStrArray[1];
//			name = sourceStrArray[0]+"_"+String.valueOf(System.currentTimeMillis())+"_"+sourceStrArray[1];
		}
        
        File dest = new File(filePath + name);
        Log.trace("upload progress: " + name);
        try {
            file.transferTo(dest);
            BufferedImage bi = ImageIO.read(new FileInputStream(dest));
            int w = bi.getWidth();
            int h = bi.getHeight();
            int size_img = w>h?h:w;
            System.out.println("image size："+size_img);
            Thumbnails.of(filePath + name).sourceRegion(Positions.CENTER, size_img,size_img).size(230, 230).keepAspectRatio(true).toFile(thum_filePath+"thum_"+name);
            msg =  "上传成功";
            error_code = 0;
            JSONObject jsonObject = new JSONObject();
    		jsonObject.put("error", error_code);
    		jsonObject.put("info", msg);
    		jsonObject.put("ts", System.currentTimeMillis());
    		jsonObject.put("path", "http://dearsan.top/image/" + name);
    		Log.trace("upload success: " + name);
    		
    		//保存名字
    		sansanService.saveImageName(name);
    		
    		return jsonObject;
        } catch (IOException e) {
        	msg = e.toString();
        	error_code = 1;
        	JSONObject jsonObject = new JSONObject();
    		jsonObject.put("error", error_code);
    		jsonObject.put("info", msg);
    		jsonObject.put("ts", System.currentTimeMillis());
    		Log.trace("upload error: " + name);
    		return jsonObject;
        }
	}
}
