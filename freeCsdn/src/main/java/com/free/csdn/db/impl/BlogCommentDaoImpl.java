package com.free.csdn.db.impl;

import java.util.List;

import android.content.Context;

import com.free.csdn.bean.Comment;
import com.free.csdn.config.CacheManager;
import com.free.csdn.db.BlogCommentDao;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

/**
 * 博客评论-数据库实现
 * 
 * @author tangqi
 * @data 2015年8月7日下午11:53:19
 */

public class BlogCommentDaoImpl implements BlogCommentDao {

	private DbUtils db;

	public BlogCommentDaoImpl(Context context, String filename) {
		// TODO Auto-generated method stub
		db = DbUtils.create(context, CacheManager.getBlogCommentDbPath(context),
				filename + "_comment");
	}

	public void insert(List<Comment> list) {
		try {
			for (int i = 0; i < list.size(); i++) {
				Comment commentItem = list.get(i);
				Comment findItem = db.findFirst(Selector.from(Comment.class)
						.where("commentId", "=", commentItem.getCommentId()));
				if (findItem != null) {
					db.update(
							commentItem,
							WhereBuilder.b("commentId", "=",
									commentItem.getCommentId()));
				} else {
					db.save(commentItem);
				}
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Comment> query(int page) {
		try {
			List<Comment> list = db.findAll(Selector.from(Comment.class).limit(
					20 * page));
			return list;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
