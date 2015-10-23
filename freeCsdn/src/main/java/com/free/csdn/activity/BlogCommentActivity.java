package com.free.csdn.activity;

import java.util.Collections;
import java.util.List;

import com.free.csdn.R;
import com.free.csdn.adapter.CommentAdapter;
import com.free.csdn.base.BaseActivity;
import com.free.csdn.bean.Comment;
import com.free.csdn.bean.CommentComparator;
import com.free.csdn.config.AppConstants;
import com.free.csdn.db.BlogCommentDao;
import com.free.csdn.db.DaoFactory;
import com.free.csdn.task.HttpAsyncTask;
import com.free.csdn.task.OnResponseListener;
import com.free.csdn.util.DateUtil;
import com.free.csdn.util.JsoupUtil;
import com.free.csdn.util.ToastUtil;
import com.free.csdn.util.URLUtil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * 博客评论列表
 * 
 * @author tangqi
 * @data 2015年7月20日下午8:20:20
 *
 */
public class BlogCommentActivity extends BaseActivity implements IXListViewRefreshListener, IXListViewLoadMore {

	private XListView mListView;
	private CommentAdapter mAdapter;
	private ImageView mReLoadImageView;
	private ImageView mBtnBack;
	private TextView mTvComment;
	private ProgressBar mPbLoading;

	private HttpAsyncTask mAsyncTask;
	private String mFileName;
	private int mPage = 1;
	private int mPageSize = 20;
	private BlogCommentDao mBlogCommentDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);

		initData();
		initComponent();
	}

	private void initData() {
		mFileName = getIntent().getExtras().getString("filename"); // 获得文件名
		mAdapter = new CommentAdapter(this);

		mBlogCommentDao = DaoFactory.getInstance().getBlogCommentDao(this, mFileName);
	}

	private void initComponent() {
		mPbLoading = (ProgressBar) findViewById(R.id.pb_loading);
		mReLoadImageView = (ImageView) findViewById(R.id.reLoadImage);
		mReLoadImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("click");
				mReLoadImageView.setVisibility(View.INVISIBLE);
				onRefresh();
			}
		});

		mBtnBack = (ImageView) findViewById(R.id.btn_back);
		mBtnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mTvComment = (TextView) findViewById(R.id.comment);

		mListView = (XListView) findViewById(R.id.listview);
		mListView.setAdapter(mAdapter);
		mListView.NotRefreshAtBegin();
		mListView.setPullRefreshEnable(this);

		// 先预加载数据，再请求最新数据
		mHandler.sendEmptyMessage(AppConstants.MSG_PRELOAD_DATA);
	}

	@Override
	public void onLoadMore() {
		mPage++;
		requestData(mPage);
	}

	@Override
	public void onRefresh() {
		mPage = 1;
		requestData(mPage);
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.push_no, R.anim.push_right_out);
	}

	private void requestData(int page) {
		if (mAsyncTask != null) {
			mAsyncTask.cancel(true);
		}

		mAsyncTask = new HttpAsyncTask(this);
		String url = URLUtil.getCommentListURL(mFileName, String.valueOf(page));
		mAsyncTask.execute(url);
		mAsyncTask.setOnResponseListener(onResponseListener);
	}

	private OnResponseListener onResponseListener = new OnResponseListener() {

		@Override
		public void onResponse(String resultString) {
			// TODO Auto-generated method stub
			// 解析html页面获取列表
			if (resultString != null) {
				List<Comment> list = JsoupUtil.getBlogCommentList(resultString, mPage, mPageSize);
				CommentComparator comparator = new CommentComparator();
				Collections.sort(list, comparator);
				if (mPage == 1) {
					mAdapter.setList(list);
				} else {
					mAdapter.addList(list);
				}
				mAdapter.notifyDataSetChanged();
				mListView.setPullLoadEnable(BlogCommentActivity.this);// 设置可上拉加载
				mTvComment.setText(mAdapter.getCount() + "条");
				saveDB(list);

			} else {
				ToastUtil.show(BlogCommentActivity.this, "网络已断开");
				mListView.disablePullLoad();
			}

			mPbLoading.setVisibility(View.GONE);
			mReLoadImageView.setVisibility(View.GONE);
			mListView.stopRefresh(DateUtil.getDate());
			mListView.stopLoadMore();
		}
	};

	/**
	 * 保存数据库
	 * 
	 * @param list
	 */
	private void saveDB(final List<Comment> list) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mBlogCommentDao.insert(list);
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
				mListView.setRefreshTime(DateUtil.getDate());
				List<Comment> list = mBlogCommentDao.query(mPage);

				if (list != null) {
					mAdapter.setList(list);
					mAdapter.notifyDataSetChanged();
					mListView.setPullLoadEnable(BlogCommentActivity.this);
					mListView.setRefreshTime(DateUtil.getDate());
					mTvComment.setText(mAdapter.getCount() + "条");
				} else {
					// 不请求最新数据，让用户自己刷新或者加载
					mPbLoading.setVisibility(View.VISIBLE);
					requestData(mPage);
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
