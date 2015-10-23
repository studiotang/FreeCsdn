package com.free.csdn.db;

import java.util.List;

import com.free.csdn.bean.BlogItem;

/**
 * 博客收藏-数据库定义
 * 
 * @author tangqi
 * @data 2015年8月7日下午11:19:46
 */

public interface BlogCollectDao {

	/**
	 * 保存博客列表
	 * 
	 * @param list
	 */
	public void insert(List<BlogItem> list);
	
	/**
	 * 保存博客
	 * 
	 * @param list
	 */
	public void insert(BlogItem blogItem);
	
	/**
	 * 删除博客
	 * 
	 * @param list
	 */
	public void delete(BlogItem blogItem);
	
	/**
	 * 查找博客
	 * 
	 * @param list
	 */
	public BlogItem query(String link);

	/**
	 * 查找博客列表
	 * 
	 * @param page
	 * @return
	 */
	public List<BlogItem> query(int page, int pageSize);
}
