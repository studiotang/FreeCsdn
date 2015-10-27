/**
 * Copyright © 2015-2020 100msh.com All Rights Reserved
 */
package com.free.csdn.db.impl;

import java.util.List;

import com.free.csdn.bean.BlogItem;
import com.free.csdn.config.CacheManager;
import com.free.csdn.db.BlogCollectDao;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import android.content.Context;

/**
 * 博客收藏-数据库实现
 *
 * @author Frank
 * @date 2015年8月13日下午12:58:51
 */

public class BlogCollectDaoImpl implements BlogCollectDao {
    private DbUtils db;

    public BlogCollectDaoImpl(Context context) {
        db = DbUtils.create(context, CacheManager.getBloggerCollectDbPath(context), getDbName());
    }

    public String getDbName() {
        return "collect_blog";
    }

    public void insert(List<BlogItem> list) {
        try {
            for (int i = 0; i < list.size(); i++) {
                BlogItem blogItem = list.get(i);
                BlogItem findItem = db.findFirst(Selector.from(BlogItem.class).where("link", "=", blogItem.getLink()));
                if (findItem != null) {
                    db.update(blogItem, WhereBuilder.b("link", "=", blogItem.getLink()));
                } else {
                    db.save(blogItem);
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(BlogItem blogItem) {
        try {
            BlogItem findItem = db.findFirst(Selector.from(BlogItem.class).where("link", "=", blogItem.getLink()));
            if (findItem != null) {
                db.update(blogItem, WhereBuilder.b("link", "=", blogItem.getLink()));
            } else {
                db.save(blogItem);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(BlogItem blogItem) {
        try {
            BlogItem findItem = db.findFirst(Selector.from(BlogItem.class).where("link", "=", blogItem.getLink()));
            if (findItem != null) {
                db.delete(findItem);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BlogItem query(String link) {
        try {
            return db.findFirst(Selector.from(BlogItem.class).where("link", "=", link));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BlogItem> query(int page, int pageSize) {
        try {
            return db.findAll(Selector.from(BlogItem.class).orderBy("updateTime", true).limit(page * pageSize));
        } catch (DbException e) {
            e.printStackTrace();
        }

        return null;
    }

}
