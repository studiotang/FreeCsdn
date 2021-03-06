package com.free.blog.ui.home.mine;

import com.free.blog.ui.base.activity.BaseBlogListActivity;

/**
 * @author studiotang on 17/3/22
 */
public class BlogCollectActivity extends BaseBlogListActivity {
    @Override
    protected String getActionBarTitle() {
        return "博客收藏";
    }

    @Override
    protected void beforeInitView() {
        new BlogCollectPresenter(this);
    }
}
