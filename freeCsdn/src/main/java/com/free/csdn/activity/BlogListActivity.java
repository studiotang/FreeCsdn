package com.free.csdn.activity;

import java.util.ArrayList;
import java.util.List;

import com.free.csdn.R;
import com.free.csdn.adapter.BlogCategoryAdapter;
import com.free.csdn.adapter.BlogListAdapter;
import com.free.csdn.base.BaseActivity;
import com.free.csdn.bean.BlogCategory;
import com.free.csdn.bean.BlogItem;
import com.free.csdn.bean.Blogger;
import com.free.csdn.config.AppConstants;
import com.free.csdn.db.BlogItemDao;
import com.free.csdn.db.DaoFactory;
import com.free.csdn.task.HttpAsyncTask;
import com.free.csdn.task.OnResponseListener;
import com.free.csdn.util.DateUtil;
import com.free.csdn.util.DisplayUtil;
import com.free.csdn.util.JsoupUtil;
import com.free.csdn.util.NetUtil;
import com.free.csdn.util.ToastUtil;
import com.free.csdn.util.URLUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * 博客列表
 * 
 * @author tangqi
 * @data 2015年7月8日下午9:20:20
 *
 */
@SuppressLint("InflateParams")
public class BlogListActivity extends BaseActivity implements OnItemClickListener, OnClickListener, IXListViewRefreshListener, IXListViewLoadMore {

	private XListView mListView;
	private BlogListAdapter mAdapter;
	private HttpAsyncTask mAsyncTask;
	private ImageView mReLoadImageView;
	private ProgressBar mPbLoading;
	private TextView mTvUserId;
	private PopupWindow mPopupWindow;

	private String mUserId;
	private int mPage = 1;
	private Blogger mBlogger;
	private BlogItemDao mBlogItemDao;
	private List<BlogCategory> mBlogCategoryList = new ArrayList<BlogCategory>();
	private String mCategory = AppConstants.BLOG_CATEGORY_ALL;
	private String mBaseUrl = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bloglist);

		initData();
		initView();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mAsyncTask != null) {
			mAsyncTask.cancel(true);
		}
	}

	private void initData() {
		mBlogger = (Blogger) getIntent().getSerializableExtra("blogger");
		mUserId = mBlogger.getUserId();
		mBlogItemDao = DaoFactory.getInstance().getBlogItemDao(this, mUserId);

		mBaseUrl = URLUtil.getBlogDefaultUrl(mUserId);
		queryCategory();
	}

	private void initView() {
		mListView = (XListView) findViewById(R.id.listView_blog);
		mPbLoading = (ProgressBar) findViewById(R.id.pb_loading);
		mTvUserId = (TextView) findViewById(R.id.tv_title);
		ImageView mBackBtn = (ImageView) findViewById(R.id.btn_back);
		ImageView mMenuBtn = (ImageView) findViewById(R.id.btn_menu);
		mBackBtn.setOnClickListener(this);
		mMenuBtn.setOnClickListener(this);
		mMenuBtn.setVisibility(View.VISIBLE);
		mMenuBtn.setImageResource(R.drawable.ic_menu);

		if (mBlogger != null) {
			setDefaultTitle();
		}

		mReLoadImageView = (ImageView) findViewById(R.id.reLoadImage);
		mReLoadImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				mReLoadImageView.setVisibility(View.INVISIBLE);
				mPbLoading.setVisibility(View.VISIBLE);

				refresh();
			}
		});

		initListView();
	}

	private void setDefaultTitle() {
		String title = mBlogger.getTitle();
		if (!TextUtils.isEmpty(title)) {
			mTvUserId.setText(title);
		}
	}

	/**
	 * 初始化ListView
	 */
	private void initListView() {
		mAdapter = new BlogListAdapter(this);
		mListView.setPullRefreshEnable(this);
		mListView.NotRefreshAtBegin();
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);

		// 先预加载数据，再请求最新数据
		mHandler.sendEmptyMessage(AppConstants.MSG_PRELOAD_DATA);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.btn_back:
			finish();
			break;

		case R.id.btn_menu:
			showMenu(view);
			break;

		default:
			break;
		}
	}

	/**
	 * 显示PopWindow
	 */
	private void showMenu(View view) {
		// TODO Auto-generated method stub
		if (mPopupWindow == null) {
			getPopupWindow(view);
		}

		int xOffset = (int) getResources().getDimension(R.dimen.popwindow_bloglist_width) - DisplayUtil.dp2px(this, 40);
		mPopupWindow.showAsDropDown(view, (-1) * xOffset, 0);
	}

	/**
	 * 初始化PopupWindow
	 * 
	 * @param view
	 */
	private void getPopupWindow(View view) {
		View contentView = LayoutInflater.from(this).inflate(R.layout.popwindow_bloglist, null);

		mPopupWindow = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		mPopupWindow.setTouchable(true);
		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

		ListView listView = (ListView) contentView.findViewById(R.id.lv_blog_type);
		BlogCategoryAdapter adapter = new BlogCategoryAdapter(this, mBlogCategoryList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				mPopupWindow.dismiss();
				if (position == 0) {
					setDefaultTitle();
					mCategory = AppConstants.BLOG_CATEGORY_ALL;
					mBaseUrl = URLUtil.getBlogDefaultUrl(mUserId);

					mAdapter.clearList();
					mPbLoading.setVisibility(View.VISIBLE);
					refresh();
				} else {
					BlogCategory blogCategory = ((BlogCategoryAdapter) parent.getAdapter()).getItem(position);
					mTvUserId.setText(blogCategory.getName());
					mCategory = blogCategory.getName();
					mBaseUrl = URLUtil.getBlogCategoryUrl(blogCategory.getLink());

					mAdapter.clearList();
					mPbLoading.setVisibility(View.VISIBLE);
					refresh();
				}
			}
		});

		contentView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mPopupWindow.dismiss();
			}
		});
	}

	/**
	 * ListView点击事件
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		BlogItem item = (BlogItem) mAdapter.getItem(position - 1);
		Intent i = new Intent();
		i.setClass(BlogListActivity.this, BlogContentActivity.class);
		i.putExtra("blogItem", item);
		startActivity(i);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_no);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		loadMore();
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		refresh();
	}

	private void loadMore() {
		mPage++;
		requestData();
	}

	private void refresh() {
		mReLoadImageView.setVisibility(View.GONE);
		mListView.disablePullLoad();
		mPage = 1;
		requestData();
	}

	private void requestData() {
		if (NetUtil.isNetAvailable(this)) {
			getData(mPage);
		} else {
			mHandler.sendEmptyMessage(AppConstants.MSG_PRELOAD_DATA);
		}
	}

	private void getData(int page) {
		if (mAsyncTask != null) {
			mAsyncTask.cancel(true);
		}

		mAsyncTask = new HttpAsyncTask(this);
		String url = URLUtil.getBlogListURL(mBaseUrl, page);
		mAsyncTask.execute(url);
		mAsyncTask.setOnResponseListener(mOnResponseListener);
	}

	private OnResponseListener mOnResponseListener = new OnResponseListener() {

		@Override
		public void onResponse(String resultString) {
			// TODO Auto-generated method stub
			// 解析html页面获取列表
			if (resultString != null) {
				List<BlogItem> list = JsoupUtil.getBlogItemList(mCategory, resultString, mBlogCategoryList);

				if (list != null && list.size() > 0) {
					if (mPage == 1) {
						mAdapter.setList(list);
					} else {
						mAdapter.addList(list);
					}
					mListView.setPullLoadEnable(BlogListActivity.this);// 设置可上拉加载

					saveDB(list);
					mReLoadImageView.setVisibility(View.GONE);
				} else {
					if (mAdapter.getCount() == 0) {
						mReLoadImageView.setVisibility(View.VISIBLE);
					}
					mListView.disablePullLoad();
					ToastUtil.show(BlogListActivity.this, "暂无最新数据");
				}
			} else {
				if (mAdapter.getCount() == 0) {
					mReLoadImageView.setVisibility(View.VISIBLE);
				}
				mListView.disablePullLoad();
				ToastUtil.show(BlogListActivity.this, "网络已断开");
			}

			mPbLoading.setVisibility(View.GONE);
			mListView.stopRefresh(DateUtil.getDate());
			mListView.stopLoadMore();
		}
	};

	/**
	 * 查询当前所有分类
	 */
	private void queryCategory() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				List<BlogCategory> blogCategoryList = mBlogItemDao.queryCategory();
				if (blogCategoryList != null) {
					mBlogCategoryList.addAll(blogCategoryList);
				}
			}
		}).start();
	}

	/**
	 * 保存数据库
	 * 
	 * @param list
	 */
	private void saveDB(final List<BlogItem> list) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mBlogItemDao.insert(mCategory, list);

				if (mBlogCategoryList != null) {
					mBlogItemDao.insertCategory(mBlogCategoryList);
				}
			}
		}).start();

	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case AppConstants.MSG_PRELOAD_DATA:
				List<BlogItem> list = mBlogItemDao.query(mCategory, mPage);

				if (list != null && list.size() != 0) {
					mAdapter.setList(list);
					mListView.setPullLoadEnable(BlogListActivity.this);// 设置可上拉加载
					mListView.setRefreshTime(DateUtil.getDate());
					mListView.stopRefresh();
					mListView.stopLoadMore();
					mPbLoading.setVisibility(View.GONE);
				} else {
					// 不请求最新数据，让用户自己刷新或者加载
					mPbLoading.setVisibility(View.VISIBLE);
					getData(mPage);
					mListView.disablePullLoad();
				}

				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
}
