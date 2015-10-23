/** Copyright © 2015-2020 100msh.com All Rights Reserved */
package com.free.csdn.activity;

import java.util.List;

import com.free.csdn.R;
import com.free.csdn.adapter.BlogListAdapter;
import com.free.csdn.base.BaseActivity;
import com.free.csdn.bean.BlogItem;
import com.free.csdn.bean.Channel;
import com.free.csdn.config.AppConstants;
import com.free.csdn.config.CategoryManager.CategoryName;
import com.free.csdn.config.ExtraString;
import com.free.csdn.db.BlogItemDao;
import com.free.csdn.db.DaoFactory;
import com.free.csdn.task.HttpAsyncTask;
import com.free.csdn.task.OnResponseListener;
import com.free.csdn.util.DateUtil;
import com.free.csdn.util.JsoupUtil;
import com.free.csdn.util.NetUtil;
import com.free.csdn.util.ToastUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * 热门文章列表
 * 
 * @author Frank
 * @date 2015年9月29日下午3:02:29
 */

public class HotListActivity extends BaseActivity implements OnClickListener, OnItemClickListener, IXListViewRefreshListener {

	private XListView mListView;
	private ImageView mReLoadImageView;
	private ProgressBar mPbLoading;

	private Channel mChannel;
	private String mChannelName;
	private String mUrl;
	private BlogItemDao mBlogItemDao;
	private HttpAsyncTask mAsyncTask;
	private BlogListAdapter mAdapter;
	private List<BlogItem> mBlogList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_bloglist);
		initData();
		initView();

		queryDb();
	}

	private void initData() {
		mChannel = (Channel) getIntent().getSerializableExtra(ExtraString.CHANNEL);
		if (mChannel != null) {
			mChannelName = mChannel.getChannelName();
			mUrl = mChannel.getUrl().replace("experts.html", "hot.html");
		} else {
			mChannelName = CategoryName.ANDROID;
		}

		mBlogItemDao = DaoFactory.getInstance().getBlogItemDao(this, mChannelName);
	}

	private void initView() {
		// TODO Auto-generated method stub
		ImageView mBackBtn = (ImageView) findViewById(R.id.btn_back);
		mBackBtn.setOnClickListener(this);

		TextView tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText(mChannelName);

		mPbLoading = (ProgressBar) findViewById(R.id.pb_loading);
		mReLoadImageView = (ImageView) findViewById(R.id.reLoadImage);
		mReLoadImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				mReLoadImageView.setVisibility(View.INVISIBLE);
				mPbLoading.setVisibility(View.VISIBLE);

				requestData();
			}
		});

		initListView();
	}

	private void initListView() {
		// TODO Auto-generated method stub
		mListView = (XListView) findViewById(R.id.listView_blog);
		mAdapter = new BlogListAdapter(this);

		mListView.setPullRefreshEnable(this);
		mListView.NotRefreshAtBegin();
		mListView.setRefreshTime(DateUtil.getDate());
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.btn_back:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		BlogItem item = (BlogItem) arg0.getAdapter().getItem(position);
		Intent i = new Intent();
		i.setClass(HotListActivity.this, BlogContentActivity.class);
		i.putExtra("blogItem", item);
		startActivity(i);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_no);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		requestData();
	}

	/**
	 * 查询数据库
	 */
	private void queryDb() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				mBlogList = mBlogItemDao.queryAll();
				if (mBlogList != null && mBlogList.size() > 0) {
					// 数据库有数据，则更新UI
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							mAdapter.setList(mBlogList);
						}
					});
				} else {
					// 否则请求数据
					mPbLoading.setVisibility(View.VISIBLE);
					requestData();
				}
			}
		}).start();
	}

	/**
	 * 请求数据
	 */
	protected void requestData() {
		// TODO Auto-generated method stub

		mAsyncTask = new HttpAsyncTask(this);
		mAsyncTask.execute(mUrl);
		mAsyncTask.setOnResponseListener(new OnResponseListener() {

			@Override
			public void onResponse(String resultString) {
				mPbLoading.setVisibility(View.GONE);
				mReLoadImageView.setVisibility(View.GONE);
				mListView.stopRefresh(DateUtil.getDate());

				if (!TextUtils.isEmpty(resultString)) {
					mBlogList = JsoupUtil.getHotBlogList(AppConstants.BLOG_CATEGORY_ALL, resultString);
					mAdapter.setList(mBlogList);
					saveDb(mBlogList);
				} else {
					if (NetUtil.isNetAvailable(HotListActivity.this)) {
						ToastUtil.show(HotListActivity.this, "暂无最新数据");
					} else {
						ToastUtil.show(HotListActivity.this, "网络已断开");
						mReLoadImageView.setVisibility(View.VISIBLE);
					}
				}
			}
		});
	}

	/**
	 * 保存数据库
	 * 
	 * @param blogList
	 */
	private void saveDb(final List<BlogItem> blogList) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mBlogItemDao.deleteAll();
				mBlogItemDao.insert(mChannelName, blogList);
			}
		}).start();
	}

}
