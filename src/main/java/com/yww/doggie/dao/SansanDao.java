package com.yww.doggie.dao;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.yww.doggie.entity.SanComment;
import com.yww.doggie.entity.SanStar;
import com.yww.doggie.entity.SansanUser;

/**
 * @author yangxincai
 *
 */
@Mapper
public interface SansanDao {

	
	/**
	 * @param oid
	 * @return 根据oid查找用户数据
	 */
	@Select("select * from star_user where oid = #{oid}")
	SansanUser getUserByOid(int oid);

	/**
	 * @param nick_name
	 * @return 根据nick_name查找用户数据
	 */
	@Select("select * from star_user where nick_name = #{nick_name}")
	SansanUser getUserByNickname(String nick_name);

	/**
	 * 用户注册
	 * @param nick_name
	 * @param password
	 * @param regist_time
	 * @param token
	 */
	@Insert("INSERT INTO star_user (nick_name, password, regist_time, token) VALUES (#{nick_name}, #{password}, #{regist_time}, #{token})")
	void registUser(@Param("nick_name") String nick_name, @Param("password") String password,
					@Param("regist_time") String regist_time, @Param("token") String token);

	/**
	 * 发表评论
	 * @param oid
	 * @param star_oid
	 * @param comment_time
	 * @param content
	 * @param from_user_oid
	 * @param from_user_name
	 * @param to_user_oid
	 * @param to_user_name
	 */
	@Insert("INSERT INTO star_comment (oid, star_oid, comment_time, content, from_user_oid, from_user_name, to_user_oid, to_user_name, zan_num) VALUES (#{oid}, #{star_oid}, #{comment_time}, #{content}, #{from_user_oid}, #{from_user_name}, #{to_user_oid}, #{to_user_name}, #{zan_num})")
	void commitComment(@Param("oid") String oid, @Param("star_oid") String star_oid,
					   @Param("comment_time") String comment_time, @Param("content") String content,
					   @Param("from_user_oid") int from_user_oid, @Param("from_user_name") String from_user_name,
					   @Param("to_user_oid") int to_user_oid, @Param("to_user_name") String to_user_name, @Param("zan_num") int zan_num);
	
	/**
	 * 发布star
	 * @param oid
	 * @param user_oid
	 * @param user_nick_name
	 * @param content
	 * @param hug_num
	 * @param calm_num
	 * @param comment_num
	 * @param pub_time
	 */
	@Insert("INSERT INTO star_stars (oid, user_oid, user_nick_name, content, hug_num, calm_num, comment_num, pub_time) VALUES (#{oid}, #{user_oid}, #{user_nick_name}, #{content}, #{hug_num}, #{calm_num}, #{comment_num}, #{pub_time})")
	void commitStar(@Param("oid") String oid, @Param("user_oid") int user_oid,
					@Param("user_nick_name") String user_nick_name, @Param("content") String content,
					@Param("hug_num") int hug_num, @Param("calm_num") int calm_num,
					@Param("comment_num") int comment_num, @Param("pub_time") String pub_time);
	
	
	/**
	 * 根据用户oid查询其发表的star
	 * @param user_oid
	 * @return 
	 */
	@Select("select * from star_stars where user_oid = #{user_oid}")
	List<SanStar> getStarsByUserOid(int user_oid);
	
	/**
	 * 根据star_oid查询star
	 * @param star_oid
	 * @return 
	 */
	@Select("select * from star_stars where oid = #{star_oid}")
	SanStar getStarsByOid(String star_oid);
	
	/**
	 * stars数据的分页接口
	 * @param offset
	 * @param limit
	 * @return
	 */
	@Select("select * from star_stars limit #{offset},#{limit}")
	List<SanStar> getStars(@Param("offset") int offset, @Param("limit") int limit);
	
	/**
	 * 根据star_oid查找评论
	 * @param star_oid
	 * @return
	 */
	@Select("select * from star_comment where star_oid = #{star_oid}")
	List<SanComment> getCommentByStarOid(String star_oid);
	
	/**
	 * 抱抱
	 * @param star_oid
	 * @return
	 */
	@Update("update star_stars set hug_num=hug_num+1 where oid = #{star_oid}")
	void hug(String star_oid);
	
	/**
	 * 冷静
	 * @param star_oid
	 * @return
	 */
	@Update("update star_stars set calm_num=calm_num+1 where oid = #{star_oid}")
	void calm(String star_oid);
	
	/**
	 * 评论数量
	 * @param star_oid
	 * @return
	 */
	@Update("update star_stars set comment_num=comment_num+1 where oid = #{star_oid}")
	void updateCommentNum(String star_oid);
	
	
	/**
	 * 评论的赞数
	 * @param comment_oid
	 */
	@Update("update star_comment set zan_num=zan_num+1 where oid = #{comment_oid}")
	void updateZanNum(String comment_oid);
	
	/**
	 * 根据comment_oid查找评论
	 * @param comment_oid
	 * @return
	 */
	@Select("select * from star_comment where oid = #{comment_oid}")
	SanComment getCommentByOid(String comment_oid);
	
	/**
	 * 获取指定日期图片
	 * @param time 形如：yy-MM-dd
	 * @return
	 */
	@Select("select * from image_path where image_path.img_name like CONCAT('%',#{time},'%')")
	List<String> getImages(String time);
	
	/**
	 * 获取所有日期图片
	 * @return
	 */
	@Select("select * from image_path")
	List<String> getAllImages();
	
	/**
	 * 存储图片名字
	 * @param img_name
	 * @return
	 */
	@Select("INSERT INTO image_path (img_name) VALUES (#{img_name})")
	void saveImageName(@Param("img_name") String img_name);
}
