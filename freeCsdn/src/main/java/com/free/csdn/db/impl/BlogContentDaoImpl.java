package com.free.csdn.db.impl;

import android.content.Context;

import com.free.csdn.bean.BlogHtml;
import com.free.csdn.config.CacheManager;
import com.free.csdn.db.BlogContentDao;
import com.free.csdn.util.MD5;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

/**
 * 博客内容-数据库实现
 * 
 * @author tangqi
 * @data 2015年8月7日下午11:24:06
 */

public class BlogContentDaoImpl implements BlogContentDao {

	private DbUtils db;

	public BlogContentDaoImpl(Context context, String url) {
		// TODO Auto-generated constructor stub
		String urlMD5 = "url-md5";
		try {
			urlMD5 = MD5.getMD5(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db = DbUtils.create(context,
				CacheManager.getBlogContentDbPath(context), urlMD5);
	}

	public void insert(BlogHtml blogHtml) {
		BlogHtml findItem;
		try {
			findItem = db.findFirst(Selector.from(BlogHtml.class).where("url",
					"=", blogHtml.getUrl()));

			if (findItem != null) {
				db.update(blogHtml,
						WhereBuilder.b("url", "=", blogHtml.getUrl()));
			} else {
				db.save(blogHtml);
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return;
	}

	public BlogHtml query(String url) {
		BlogHtml blogHtml = null;
		try {
			blogHtml = db.findFirst(Selector.from(BlogHtml.class).where("url",
					"=", url));
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return blogHtml;
	}
}
