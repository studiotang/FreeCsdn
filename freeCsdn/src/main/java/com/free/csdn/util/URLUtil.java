package com.free.csdn.util;

import com.free.csdn.config.AppConstants;

/**
 * URL工具
 * 
 * @author tangqi
 * @data 2015年7月8日下午9:20:20
 */
public class URLUtil {

	/**
	 * 获取博客列表的URL
	 * 
	 * @param baseUrl
	 *            博客类型
	 * @param page
	 *            页数
	 * @return
	 */
	public static String getBlogListURL(String baseUrl, int page) {
		return baseUrl + page;
	}

	/**
	 * 获取CSDN博客默认URL
	 * 
	 * @param userId
	 * @return
	 */
	public static String getBlogDefaultUrl(String userId) {
		return AppConstants.CSDN_BASE_URL + userId + "/article/list" + "/";
	}

	public static String getBlogCategoryUrl(String categoryLink) {
		return AppConstants.CSDN_BASE_URL + categoryLink + "/";
	}

	/**
	 * 返回博文评论列表链接
	 * 
	 * @param filename
	 *            文件名
	 * @param pageIndex
	 *            页数
	 * @return
	 */
	public static String getCommentListURL(String filename, String pageIndex) {
		return "http://blog.csdn.net/wwj_748/comment/list/" + filename + "?page=" + pageIndex;
	}

}
