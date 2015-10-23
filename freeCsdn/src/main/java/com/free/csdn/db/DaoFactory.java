/** Copyright © 2015-2020 100msh.com All Rights Reserved */
package com.free.csdn.db;

import com.free.csdn.bean.Channel;
import com.free.csdn.db.impl.BlogCollectDaoImpl;
import com.free.csdn.db.impl.BlogCommentDaoImpl;
import com.free.csdn.db.impl.BlogContentDaoImpl;
import com.free.csdn.db.impl.BlogItemDaoImpl;
import com.free.csdn.db.impl.BloggerDaoImpl;
import com.free.csdn.db.impl.ChannelBloggerDaoImpl;

import android.content.Context;

/**
 * 数据库工厂类
 * 
 * @author Frank
 * @date 2015年8月22日上午10:26:06
 */

public class DaoFactory {

	private static DaoFactory mInstance = null;

	/**
	 * 获取DaoFactory的实例
	 * 
	 * @return
	 */
	public static DaoFactory getInstance() {
		if (mInstance == null) {
			synchronized (DaoFactory.class) {
				if (mInstance == null) {
					mInstance = new DaoFactory();
				}
			}
		}
		return mInstance;
	}

	/**
	 * 获取博主数据库
	 * 
	 * @param context
	 * @return
	 */
	public BloggerDao getBloggerDao(Context context, String type) {
		return new BloggerDaoImpl(context, type);
	}

	/**
	 * 获取博客列表数据库
	 * 
	 * @param context
	 * @return
	 */
	public BlogItemDao getBlogItemDao(Context context, String userId) {
		return new BlogItemDaoImpl(context, userId);
	}

	/**
	 * 获取博客内容数据库
	 * 
	 * @param context
	 * @return
	 */
	public BlogContentDao getBlogContentDao(Context context, String url) {
		return new BlogContentDaoImpl(context, url);
	}

	/**
	 * 获取博客收藏数据库
	 * 
	 * @param context
	 * @return
	 */
	public BlogCollectDao getBlogCollectDao(Context context) {
		return new BlogCollectDaoImpl(context);
	}

	/**
	 * 获取博客评论数据库
	 * 
	 * @param context
	 * @return
	 */
	public BlogCommentDao getBlogCommentDao(Context context, String filename) {
		return new BlogCommentDaoImpl(context, filename);
	}

	/**
	 * 获取某频道-博客专家数据库
	 * 
	 * @param context
	 * @param channel
	 * @return
	 */
	public ChannelBloggerDao getChannelBloggerDao(Context context, Channel channel) {
		return new ChannelBloggerDaoImpl(context, channel);
	}
}
