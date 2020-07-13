package com.yww.doggie.service;

import java.util.List;

import com.yww.doggie.Util.SanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yww.doggie.entity.SanComment;
import com.yww.doggie.entity.SanStar;
import com.yww.doggie.entity.SansanUser;
import com.yww.doggie.dao.SansanDao;

@Service
public class SansanService {

	@Autowired(required = false)
	SansanDao sansanDao;

	public SansanUser getUser(int oid) {
		return sansanDao.getUserByOid(oid);
	}
	
	public SansanUser getUserByNickname(String nick_name) {
		return sansanDao.getUserByNickname(nick_name);
	}
	
	public ResponseObj registUser(String nick_name, String password) {
		ResponseObj res = new ResponseObj();
		if (nick_name.isEmpty() || password.isEmpty()) {
			res.setErrorCode(1);
			res.setInfo("用户名或密码为空");
			return res;
		}
		String token = "qwer";
		
		java.util.Date current_date = new java.util.Date();
		java.text.SimpleDateFormat sdf = 
		new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String regist_time = sdf.format(current_date);
		
		SansanUser userRes = this.getUserByNickname(nick_name);
		if (userRes == null) {
			sansanDao.registUser(nick_name, password, regist_time, token);
			res.setErrorCode(0);
			res.setInfo("OK");
		} else {
			res.setErrorCode(1);
			res.setInfo("用户已存在");
		}
		
		return res;
	}
	
	public ResponseObj commitStar(int user_oid, String content) {
		ResponseObj res = new ResponseObj();
		
		SansanUser user = this.getUser(user_oid);
		if (user == null) {
			res.setErrorCode(1);
			res.setInfo("用户不存在");
		} else if (content == null || content.isEmpty()) {
			res.setErrorCode(1);
			res.setInfo("发布内容不能为空");
		} else {
			String star_oid = SanUtil.createStarOid();
			
			java.util.Date current_date = new java.util.Date();
			java.text.SimpleDateFormat sdf = 
			new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String pub_time = sdf.format(current_date);
			
			sansanDao.commitStar(star_oid, user_oid, user.getNick_name(), content, 0, 0, 0, pub_time);
			res.setErrorCode(0);
			res.setInfo("OK");
		}
		
		return res;
	}
	
	public SanStar getStarByOid(String star_oid) {
		return sansanDao.getStarsByOid(star_oid);
	}
	
	public ResponseObj commitComment(String star_oid, String content, int from_user_oid, int to_user_oid) {
		ResponseObj res = new ResponseObj();
		SanStar star = this.getStarByOid(star_oid);
		if (star == null) {
			res.setErrorCode(1);
			res.setInfo("评论对象不存在");
			return res;
		} 
		SansanUser from_user = this.getUser(from_user_oid);
		if (from_user == null) {
			res.setErrorCode(1);
			res.setInfo("Star发布者不存在");
			return res;
		}
		SansanUser to_user = this.getUser(to_user_oid);
		if (to_user == null) {
			res.setErrorCode(1);
			res.setInfo("评论发布者不存在");
			return res;
		}
		String oid = SanUtil.createStarCommentOid(from_user_oid);
		
		java.util.Date current_date = new java.util.Date();
		java.text.SimpleDateFormat sdf = 
		new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String comment_time = sdf.format(current_date);
		
		sansanDao.commitComment(oid, star_oid, comment_time, content, from_user_oid, from_user.getNick_name(), to_user_oid, to_user.getNick_name(), 0);
		sansanDao.updateCommentNum(star_oid);
		return res;
	}
	
	public List<SanComment> getCommentByStarOid(String star_oid) {
		return sansanDao.getCommentByStarOid(star_oid);
	}
	
	public List<SanStar> getStars(int offset, int limit) {
		return sansanDao.getStars(offset, limit);
	}
	public List<SanStar> getStarsByUserOid(int user_oid) {
		return sansanDao.getStarsByUserOid(user_oid);
	}
	
	public ResponseObj hug(String star_oid) {
		ResponseObj res = new ResponseObj();

		SanStar star = this.getStarByOid(star_oid);
		if (star == null) {
			res.setErrorCode(1);
			res.setInfo("star不存在");
		} else {
			sansanDao.hug(star_oid);
			res.setErrorCode(0);
			res.setInfo("OK");
		}
		return res;
	}
	
	public ResponseObj calm(String star_oid) {
		ResponseObj res = new ResponseObj();

		SanStar star = this.getStarByOid(star_oid);
		if (star == null) {
			res.setErrorCode(1);
			res.setInfo("star不存在");
		} else {
			sansanDao.calm(star_oid);
			res.setErrorCode(0);
			res.setInfo("OK");
		}
		return res;
	}
	
	public ResponseObj updateCommentNum(String star_oid) {
		ResponseObj res = new ResponseObj();
		
		SanStar star = this.getStarByOid(star_oid);
		if (star == null) {
			res.setErrorCode(1);
			res.setInfo("star不存在");
		} else {
			sansanDao.updateCommentNum(star_oid);
			res.setErrorCode(0);
			res.setInfo("OK");
		}
		return res;
	}
	
	public SanComment getCommentByOid(String comment_oid) {
		return sansanDao.getCommentByOid(comment_oid);
	}
	
	public ResponseObj updateZanNum(String comment_oid) {
		ResponseObj res = new ResponseObj();

		SanComment comment = this.getCommentByOid(comment_oid);
		if (comment == null) {
			res.setErrorCode(1);
			res.setInfo("评论不存在");
		} else {
			sansanDao.updateZanNum(comment_oid);
			res.setErrorCode(0);
			res.setInfo("OK");
		}
		return res;
	}
	
	public List<String> getImages(String time) {

		List<String> images = sansanDao.getImages(time);
		return images;
	}
	
	public List<String> getAllImages() {

		List<String> images = sansanDao.getAllImages();
		return images;
	}
	
	public ResponseObj saveImageName(String img_name) {
		ResponseObj res = new ResponseObj();
		if (img_name.isEmpty()) {
			res.setErrorCode(1);
			res.setInfo("图片名字为空");
			return res;
		}
		
		sansanDao.saveImageName(img_name);
		res.setErrorCode(0);
		res.setInfo("OK");
		
		return res;
	}
}
