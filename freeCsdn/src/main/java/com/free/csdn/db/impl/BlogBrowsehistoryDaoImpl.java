package com.free.csdn.db.impl;

import android.content.Context;

import com.free.csdn.db.BlogBrowseHistoryDao;

/**
 * Created by shenshan on 15/10/27.
 */
public class BlogBrowseHistoryDaoImpl extends BlogCollectDaoImpl implements BlogBrowseHistoryDao {
    public BlogBrowseHistoryDaoImpl(Context context) {
        super(context);
    }

    @Override
    public String getDbName() {
        return "hostory_blog";
    }
}
