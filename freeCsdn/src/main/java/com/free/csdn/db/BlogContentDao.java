package com.free.csdn.db;

import com.free.csdn.bean.BlogHtml;

/**
 * 博客内容-数据库
 * 
 * @author tangqi
 * @data 2015年8月7日下午11:47:16
 */

public interface BlogContentDao {

	/**
	 * 保存博客内容
	 * 
	 * @param blogHtml
	 */
	public void insert(BlogHtml blogHtml);

	/**
	 * 获取博客内容
	 * 
	 * @param url
	 * @return
	 */
	public BlogHtml query(String url);
}
