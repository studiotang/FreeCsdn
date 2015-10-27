package com.free.csdn.db.impl;

import android.content.Context;

import com.free.csdn.db.BlogBrowsehistoryDao;
import com.free.csdn.db.BlogCollectDao;

/**
 * Created by shenshan on 15/10/27.
 */
public class BlogBrowsehistoryDaoImpl extends BlogCollectDaoImpl implements BlogBrowsehistoryDao{
    public BlogBrowsehistoryDaoImpl(Context context) {
        super(context);
    }
}
