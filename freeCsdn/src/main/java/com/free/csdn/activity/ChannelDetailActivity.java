/** Copyright © 2015-2020 100msh.com All Rights Reserved */
package com.free.csdn.activity;

import java.util.ArrayList;
import java.util.List;

import com.free.csdn.R;
import com.free.csdn.adapter.BloggerListAdapter;
import com.free.csdn.base.BaseActivity;
import com.free.csdn.bean.Blogger;
import com.free.csdn.bean.Channel;
import com.free.csdn.config.ExtraString;
import com.free.csdn.db.BloggerDao;
import com.free.csdn.db.DaoFactory;
import com.free.csdn.task.HttpAsyncTask;
import com.free.csdn.task.OnResponseListener;
import com.free.csdn.util.DateUtil;
import com.free.csdn.util.JsoupUtil;
import com.free.csdn.util.SpfUtils;
import com.free.csdn.util.ToastUtil;
import com.free.csdn.view.dialog.BaseDialog.OnCancleListener;
import com.free.csdn.view.dialog.BaseDialog.OnConfirmListener;
import com.free.csdn.view.dialog.BaseDialog.OnDeleteListener;
import com.free.csdn.view.dialog.BaseDialog.OnStickListener;
import com.free.csdn.view.dialog.BloggerOperationDialog;
import com.free.csdn.view.dialog.SelectionDialog;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * 频道详情
 * 
 * @author Frank
 * @date 2015年9月18日下午5:27:58
 */

public class ChannelDetailActivity extends BaseActivity
		implements OnClickListener, OnItemClickListener, OnItemLongClickListener, IXListViewRefreshListener {

	private XListView mListView;

	private List<Blogger> mBloggerList;
	private BloggerListAdapter mAdapter;
	private BloggerDao mBloggerDao;
	private Channel mChannel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channel_detail);

		initData();
		initView();
		queryDb(true);
	}

	private void initData() {
		mChannel = (Channel) getIntent().getSerializableExtra(ExtraString.CHANNEL);
		mBloggerDao = DaoFactory.getInstance().getBloggerDao(this, mChannel.getChannelName());
	}

	private void initView() {
		// TODO Auto-generated method stub
		if (mChannel != null) {
			TextView mTitleView = (TextView) findViewById(R.id.tv_title);
			mTitleView.setText(mChannel.getChannelName());
		}
		ImageView mBackBtn = (ImageView) findViewById(R.id.btn_back);
		ImageView mMenuBtn = (ImageView) findViewById(R.id.btn_menu);
		mBackBtn.setVisibility(View.VISIBLE);
		mBackBtn.setOnClickListener(this);
		mMenuBtn.setVisibility(View.VISIBLE);
		mMenuBtn.setImageResource(R.drawable.ic_yes);
		mMenuBtn.setOnClickListener(this);

		mListView = (XListView) findViewById(R.id.listView);
		mBloggerList = new ArrayList<Blogger>();
		mAdapter = new BloggerListAdapter(this, mBloggerList);
		mListView.setPullRefreshEnable(this);
		mListView.NotRefreshAtBegin();
		mListView.setRefreshTime(DateUtil.getDate());
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
		mListView.setOnItemLongClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.btn_back:
			finish();
			break;

		case R.id.btn_menu:
			showMenu();
			break;

		default:
			break;
		}
	}

	/**
	 * 设置为默认频道
	 */
	private void showMenu() {
		// TODO Auto-generated method stub
		SelectionDialog dialog = new SelectionDialog(this, "设置【" + mChannel.getChannelName() + "】为默认频道？");
		dialog.setOnConfirmListener(new OnConfirmListener() {

			@Override
			public void onConfirm(String result) {
				// TODO Auto-generated method stub
				SpfUtils.put(ChannelDetailActivity.this, ExtraString.BLOG_TYPE, mChannel.getChannelName());
				ToastUtil.show(ChannelDetailActivity.this, "设置成功");
				finish();
			}
		});
		dialog.show();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		Blogger blogger = (Blogger) parent.getAdapter().getItem(position);
		Intent intent = new Intent(this, BlogListActivity.class);
		intent.putExtra("blogger", blogger);
		startActivity(intent);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		final Blogger blogger = (Blogger) parent.getAdapter().getItem(position);
		BloggerOperationDialog dialog = new BloggerOperationDialog(this, blogger);
		dialog.setOnDeleteListener(new OnDeleteListener() {

			@Override
			public void onDelete(String result) {
				// TODO Auto-generated method stub
				deleleBlogger(blogger);
			}
		});

		dialog.setOnStickListener(new OnStickListener() {

			@Override
			public void onStick(String result) {
				// TODO Auto-generated method stub
				stickBlogger(blogger);
			}
		});
		dialog.show();
		return true;
	}

	/**
	 * 查询数据库
	 * 
	 * @param isRequest
	 */
	private void queryDb(final boolean isRequest) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				final List<Blogger> list = mBloggerDao.queryAll();
				if (list != null && list.size() != 0) {
					// 数据库有数据，则更新UI
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							mAdapter.setList(list);
						}
					});
				} else {
					// 否则请求数据
					if (isRequest) {
						requestData();
					}
				}

			}
		}).start();
	}

	/**
	 * 请求数据
	 */
	private void requestData() {
		if (mChannel != null) {
			String url = mChannel.getUrl();
			HttpAsyncTask asyncTask = new HttpAsyncTask(this);
			asyncTask.execute(url);
			asyncTask.setOnResponseListener(new OnResponseListener() {

				@Override
				public void onResponse(String resultString) {
					// TODO Auto-generated method stub
					if (resultString != null) {
						List<Blogger> bloggerList = JsoupUtil.getBloggerList(mChannel.getChannelName(), resultString);
						if (bloggerList != null) {
							mAdapter.setList(bloggerList);
							saveDB(bloggerList);
						}
					} else {
						ToastUtil.show(ChannelDetailActivity.this, "数据请求失败");
					}

					updateListView();
				}
			});
		}
	}

	/**
	 * 更新ListView的刷新、加载状态
	 */
	protected void updateListView() {
		// TODO Auto-generated method stub
		mListView.stopRefresh(DateUtil.getDate());
	}

	/**
	 * 保存数据库
	 * 
	 * @param list
	 */
	private void saveDB(final List<Blogger> list) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mBloggerDao.deleteAll();
				mBloggerDao.insert(list);
			}
		}).start();

	}

	/**
	 * 置顶博主
	 * 
	 * @param blogger
	 */
	private void stickBlogger(Blogger blogger) {
		if (blogger.getIsTop() == 1) {
			blogger.setIsTop(0);
			ToastUtil.show(this, "取消置顶成功");
		} else {
			blogger.setIsTop(1);
			ToastUtil.show(this, "置顶成功");
		}

		blogger.setUpdateTime(System.currentTimeMillis());
		mBloggerDao.insert(blogger);
		queryDb(false);
	}

	/**
	 * 删除博主
	 * 
	 * @param blogger
	 */
	private void deleleBlogger(Blogger blogger) {
		if (blogger != null) {
			ToastUtil.show(this, "删除成功");
			mBloggerDao.delete(blogger);
			queryDb(false);
		} else {
			ToastUtil.show(this, "删除失败");
		}
	}

	/**
	 * 刷新
	 */
	@Override
	public void onRefresh() {
		// 处理刷新的问题
		SelectionDialog dialog = new SelectionDialog(this, "刷新数据会导致以前的数据丢失，确定要执行吗？");
		dialog.setOnConfirmListener(new OnConfirmListener() {

			@Override
			public void onConfirm(String result) {
				// TODO Auto-generated method stub
				requestData();
			}
		});

		dialog.setOnCancleListener(new OnCancleListener() {

			@Override
			public void onCancle(String result) {
				// TODO Auto-generated method stub
				mListView.stopRefresh(DateUtil.getDate());
			}
		});

		dialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface arg0) {
				// TODO Auto-generated method stub
				mListView.stopRefresh(DateUtil.getDate());
			}
		});

		dialog.show();
	}
}
