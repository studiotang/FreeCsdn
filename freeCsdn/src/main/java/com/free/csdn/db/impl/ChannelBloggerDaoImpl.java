package com.free.csdn.db.impl;

import java.util.ArrayList;
import java.util.List;

import com.free.csdn.bean.Blogger;
import com.free.csdn.bean.Channel;
import com.free.csdn.config.CacheManager;
import com.free.csdn.db.ChannelBloggerDao;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import android.content.Context;

/**
 * 某频道博客专家-数据库实现
 * 
 * @author tangqi
 * @data 2015年9月25日下午16:50:35
 */

public class ChannelBloggerDaoImpl implements ChannelBloggerDao {

	private DbUtils db;
	private Context context;

	public ChannelBloggerDaoImpl(Context context, Channel channel) {
		this.context = context;
		init(channel);
	}

	public void init(Channel channel) {
		this.db = DbUtils.create(context, CacheManager.getChannelBloggerDbPath(context), channel.getChannelName());
	}

	public void insert(Blogger blogger) {
		try {
			Blogger findItem = db.findFirst(Selector.from(Blogger.class).where("userId", "=", blogger.getUserId()));
			if (findItem != null) {
				db.update(blogger, WhereBuilder.b("userId", "=", blogger.getUserId()));
			} else {
				db.save(blogger);
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insert(List<Blogger> list) {
		try {
			db.saveOrUpdateAll(list);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Blogger query(String userId) {
		try {
			return db.findFirst(Selector.from(Blogger.class).where("userId", "=", userId));
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<Blogger> queryAll() {
		try {
			// // 最新的排最前面
			List<Blogger> list = new ArrayList<Blogger>();
			List<Blogger> toplist = db.findAll(Selector.from(Blogger.class).where("isTop", "=", 1).orderBy("updateTime", true));
			List<Blogger> newlist = db.findAll(Selector.from(Blogger.class).where("isTop", "=", 0).and("isNew", "=", 1).orderBy("updateTime", true));
			List<Blogger> oldlist = db.findAll(Selector.from(Blogger.class).where("isTop", "=", 0).and("isNew", "=", 0));

			if (toplist != null) {
				list.addAll(toplist);
			}

			if (newlist != null) {
				list.addAll(newlist);
			}

			if (oldlist != null) {
				list.addAll(oldlist);
			}
			return list;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public List<Blogger> query(int pageIndex, int pageSize) {
		List<Blogger> list = null;
		try {
			list = db.findAll(Selector.from(Blogger.class).orderBy("isNew", true).limit(pageSize).offset(pageIndex * pageSize));
			return list;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public void delete(Blogger blogger) {
		try {
			db.delete(blogger);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteAll(List<Blogger> list) {
		try {
			db.delete(list);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		try {
			db.deleteAll(Blogger.class);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
