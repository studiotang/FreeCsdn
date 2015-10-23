package com.free.csdn.db;

import java.util.List;

import com.free.csdn.bean.Comment;

/**
 * 博客评论数据库
 * 
 * @author tangqi
 * @data 2015年8月7日下午11:58:31
 */

public interface BlogCommentDao {

	/**
	 * 保存博客评论列表
	 * 
	 * @param list
	 */
	public void insert(List<Comment> list);

	/**
	 * 获取博客评论列表
	 * 
	 * @param page
	 * @return
	 */
	public List<Comment> query(int page);
}
