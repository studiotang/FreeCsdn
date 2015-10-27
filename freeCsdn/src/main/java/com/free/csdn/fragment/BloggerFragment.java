package com.free.csdn.fragment;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import com.free.csdn.R;
import com.free.csdn.activity.BlogListActivity;
import com.free.csdn.adapter.BloggerListAdapter;
import com.free.csdn.base.BaseFragment;
import com.free.csdn.bean.Blogger;
import com.free.csdn.config.AppConstants;
import com.free.csdn.config.BloggerManager;
import com.free.csdn.config.CategoryManager.CategoryName;
import com.free.csdn.config.ExtraString;
import com.free.csdn.db.BloggerDao;
import com.free.csdn.db.DaoFactory;
import com.free.csdn.task.HttpAsyncTask;
import com.free.csdn.task.OnResponseListener;
import com.free.csdn.util.DateUtil;
import com.free.csdn.util.JsoupUtil;
import com.free.csdn.util.SpfUtils;
import com.free.csdn.util.ToastUtil;
import com.free.csdn.view.dialog.BaseDialog.OnConfirmListener;
import com.free.csdn.view.dialog.BaseDialog.OnDeleteListener;
import com.free.csdn.view.dialog.BaseDialog.OnStickListener;
import com.free.csdn.view.dialog.BloggerAddDialog;
import com.free.csdn.view.dialog.BloggerOperationDialog;
import com.free.csdn.view.dialog.LoadingDialog;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * 博主列表
 * 
 * @author tangqi
 * @data 2015年7月8日下午9:20:20
 *
 */
@SuppressLint("HandlerLeak")
public class BloggerFragment extends BaseFragment
		implements OnItemClickListener, OnItemLongClickListener, IXListViewRefreshListener, IXListViewLoadMore {

	private View mRootView;
	private XListView mListView;
	private List<Blogger> mBloggerList;
	private BloggerListAdapter mAdapter;
	private ProgressDialog mProgressdialog;

	private HashMap<String, String> mAddBloggerItem = null;
	private BloggerDao mBloggerDao = null;
	private String mNewUserId = null;
	private String mCategory = CategoryName.MOBILE;// 这个属性暂未使用
	private String mType = CategoryName.ANDROID;

	private static final int MSG_ADD_SUCCESS = 1000;
	private static final int MSG_ADD_FAILURE = 1001;
	private static final int MSG_ADD_REPEAT = 1002;
	private static final int MSG_ADD_EMPTY = 1003;
	private static final int MSG_ADD_BLOG = 1004;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		initData();

		if (mRootView == null) {
			mRootView = inflater.inflate(R.layout.fragment_blogger_list, container, false);
			initView(mRootView);
		}
		ViewGroup parent = (ViewGroup) mRootView.getParent();
		if (parent != null) {
			parent.removeView(mRootView);
		}
		return mRootView;
	}

	private void initData() {
		// TODO Auto-generated method stub
		mType = (String) SpfUtils.get(getActivity(), ExtraString.BLOG_TYPE, CategoryName.ANDROID);
		mBloggerDao = DaoFactory.getInstance().getBloggerDao(getActivity(), mType);
		new BloggerManager().init(getActivity(), mBloggerDao, mType);
		mBloggerList = mBloggerDao.queryAll();
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		mListView = (XListView) view.findViewById(R.id.listView);
		mAdapter = new BloggerListAdapter(getActivity(), mBloggerList);
		mListView.setPullRefreshEnable(this);// 设置可下拉刷新
		if (mBloggerList != null && mBloggerList.size() > 0) {
			mListView.setPullLoadEnable(this);// 设置可上拉加载
		}
		mListView.NotRefreshAtBegin();
		mListView.setRefreshTime(DateUtil.getDate());
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
		mListView.setOnItemLongClickListener(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);// 为了在Fragment中显示右上角的menu
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mAdapter.setList(mBloggerList);
	}

	/**
	 * 加载菜单
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu; this adds items to the action bar if it is present.
		setIconEnable(menu, true);
		getActivity().getMenuInflater().inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	/**
	 * 设置菜单添加图标有效
	 * 
	 * @说明 enable为true时，菜单添加图标有效，enable为false时无效。4.0系统默认无效
	 * @param menu
	 * @param enable
	 */
	private void setIconEnable(Menu menu, boolean enable) {
		try {
			Class<?> clazz = Class.forName("com.android.internal.view.menu.MenuBuilder");
			Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);
			m.setAccessible(true);

			// MenuBuilder实现Menu接口，创建菜单时，传进来的menu其实就是MenuBuilder对象(java的多态特征)
			m.invoke(menu, enable);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Blogger blogger = (Blogger) parent.getAdapter().getItem(position);
		Intent intent = new Intent(getActivity(), BlogListActivity.class);
		intent.putExtra("blogger", blogger);
		startActivity(intent);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		final Blogger blogger = (Blogger) parent.getAdapter().getItem(position);
		BloggerOperationDialog dialog = new BloggerOperationDialog(getActivity(), blogger);
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_add) {
			showCenterAddDialog();
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * 显示添加Dialog
	 */
	private void showCenterAddDialog() {
		BloggerAddDialog dialog = new BloggerAddDialog(getActivity(), new OnConfirmListener() {

			@Override
			public void onConfirm(String result) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(result)) {
					mHandler.sendEmptyMessage(MSG_ADD_EMPTY);
					return;
				}

				if (mBloggerDao.query(result) != null) {
					mHandler.sendEmptyMessage(MSG_ADD_REPEAT);
					return;
				}

				mNewUserId = result;
				mProgressdialog = new LoadingDialog(getActivity(), "正在添加博客...");
				mProgressdialog.setCancelable(false);
				mProgressdialog.show();
				mHandler.sendEmptyMessageDelayed(MSG_ADD_BLOG, 1000);
			}

		});

		dialog.show();
	}

	/**
	 * 请求博主数据
	 * 
	 * @param result
	 */
	private void requestData(String result) {
		HttpAsyncTask httpAsyncTask = new HttpAsyncTask(getActivity());
		httpAsyncTask.execute(AppConstants.CSDN_BASE_URL + result);
		httpAsyncTask.setOnResponseListener(new OnResponseListener() {
			@Override
			public void onResponse(String resultString) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(resultString)) {
					mHandler.sendEmptyMessage(MSG_ADD_FAILURE);
				} else {
					mAddBloggerItem = JsoupUtil.getBloggerItem(resultString);
					mHandler.sendEmptyMessage(MSG_ADD_SUCCESS);
				}
			}
		});
	}

	/**
	 * 增加博主
	 */
	private void addBlogger() {
		Blogger blogger = new Blogger();
		blogger.setUserId(mNewUserId);
		blogger.setTitle(mAddBloggerItem.get("title"));
		blogger.setDescription(mAddBloggerItem.get("description"));
		blogger.setImgUrl(mAddBloggerItem.get("imgUrl"));
		blogger.setLink(AppConstants.CSDN_BASE_URL + mNewUserId);
		blogger.setType(mType);
		blogger.setCategory(mCategory);
		blogger.setIsTop(0);
		blogger.setIsNew(1);
		blogger.setUpdateTime(System.currentTimeMillis());
		mBloggerDao.insert(blogger);

		mBloggerList = mBloggerDao.queryAll();
		mAdapter.setList(mBloggerList);
	}

	/**
	 * 置顶博主
	 * 
	 * @param blogger
	 */
	private void stickBlogger(Blogger blogger) {
		if (blogger.getIsTop() == 1) {
			blogger.setIsTop(0);
			ToastUtil.show(getActivity(), "取消置顶成功");
		} else {
			blogger.setIsTop(1);
			ToastUtil.show(getActivity(), "置顶成功");
		}

		blogger.setUpdateTime(System.currentTimeMillis());
		mBloggerDao.insert(blogger);

		mBloggerList = mBloggerDao.queryAll();
		mAdapter.setList(mBloggerList);
	}

	/**
	 * 删除博主
	 * 
	 * @param blogger
	 */
	private void deleleBlogger(Blogger blogger) {
		mBloggerDao.delete(blogger);

		mBloggerList = mBloggerDao.queryAll();
		mAdapter.setList(mBloggerList);

		ToastUtil.show(getActivity(), "删除成功");
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub

			if (msg.what != MSG_ADD_BLOG && mProgressdialog != null && mProgressdialog.isShowing()) {
				mProgressdialog.dismiss();
			}

			switch (msg.what) {
			case MSG_ADD_SUCCESS:
				ToastUtil.show(getActivity(), "博客ID添加成功");
				addBlogger();
				break;

			case MSG_ADD_FAILURE:
				ToastUtil.show(getActivity(), "博客ID不存在，添加失败");
				break;

			case MSG_ADD_EMPTY:
				ToastUtil.show(getActivity(), "博客ID为空");
				break;

			case MSG_ADD_REPEAT:
				ToastUtil.show(getActivity(), "博客ID重复添加");
				break;

			case MSG_ADD_BLOG:
				requestData(mNewUserId);
				break;

			default:
				break;
			}

			super.handleMessage(msg);
		}

	};

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mListView.stopLoadMore(" -- THE END --");
			}
		}, 1000);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mListView.stopRefresh(DateUtil.getDate());
			}
		}, 1000);
	}

}
