package com.free.csdn.db;

import java.util.List;

import com.free.csdn.bean.Blogger;

/**
 * 博主数据库
 * 
 * @author tangqi
 * @data 2015年8月8日下午12:59:26
 */

public interface ChannelBloggerDao {

	/**
	 * 插入博主
	 * 
	 * @param blogger
	 */
	public void insert(Blogger blogger);

	/**
	 * 插入博主列表
	 * 
	 * @param list
	 */
	public void insert(List<Blogger> list);

	/**
	 * 查询某个博主是否存在
	 * 
	 * @param userId
	 * @return
	 */
	public Blogger query(String userId);

	/**
	 * 查询所有博主
	 * 
	 * @return
	 */
	public List<Blogger> queryAll();

	/**
	 * 查询博主（分页）
	 * 
	 * @param type
	 * @param page
	 * @return
	 */
	List<Blogger> query(int pageIndex, int pageSize);

	/**
	 * 删除博主
	 * 
	 * @param blogger
	 */
	public void delete(Blogger blogger);

	/**
	 * 删除博主列表
	 * 
	 * @param list
	 */
	public void deleteAll(List<Blogger> list);

	/**
	 * 删除所有博主
	 */
	public void deleteAll();
}
