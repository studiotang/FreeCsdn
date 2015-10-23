package com.free.csdn.fragment;

import java.util.List;

import com.free.csdn.R;
import com.free.csdn.activity.ChannelDetailActivity;
import com.free.csdn.adapter.ChannelListAdapter;
import com.free.csdn.base.BaseFragment;
import com.free.csdn.bean.Channel;
import com.free.csdn.config.CategoryManager.CategoryName;
import com.free.csdn.config.ChannelManager;
import com.free.csdn.config.ExtraString;
import com.free.csdn.util.DateUtil;
import com.free.csdn.util.SpfUtils;
import com.free.csdn.util.ToastUtil;
import com.free.csdn.view.dialog.SelectionDialog;
import com.free.csdn.view.dialog.BaseDialog.OnConfirmListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;
import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * 频道
 * 
 * @author tangqi
 * @data 2015年8月9日上午11:07:09
 */

public class ChannelFragment extends BaseFragment
		implements OnItemClickListener, OnItemLongClickListener, IXListViewRefreshListener, IXListViewLoadMore {

	private View rootView;
	private XListView mListView;
	private ChannelListAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragment_channel, container, false);
			initView(rootView);
		}
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}

		return rootView;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		TextView mTitleView = (TextView) view.findViewById(R.id.tv_title);
		mTitleView.setText(R.string.change_type);

		ChannelManager channelManager = new ChannelManager(getActivity());
		List<Channel> list = channelManager.getChannelList();
		mListView = (XListView) view.findViewById(R.id.listView);
		mAdapter = new ChannelListAdapter(getActivity(), list);
		mListView.setPullRefreshEnable(this);// 设置可下拉刷新
		mListView.setPullLoadEnable(this);
		mListView.NotRefreshAtBegin();
		mListView.setRefreshTime(DateUtil.getDate());
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
		mListView.setOnItemLongClickListener(this);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		refresh();
		
	}

	private void refresh() {
		// TODO Auto-generated method stub
		String type = (String) SpfUtils.get(getActivity(), ExtraString.BLOG_TYPE, CategoryName.ANDROID);
		mAdapter.setCheckType(type);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mListView.stopRefresh(DateUtil.getDate());
				ToastUtil.showCenter(getActivity(), getActivity().getString(R.string.refresh_complete));
			}
		}, 1000);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mListView.stopLoadMore("暂无更多数据");
			}
		}, 1000);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		final Channel channel = (Channel) parent.getAdapter().getItem(position);
		SelectionDialog dialog = new SelectionDialog(getActivity(), "设置【" + channel.getChannelName() + "】为默认频道？");
		dialog.setOnConfirmListener(new OnConfirmListener() {

			@Override
			public void onConfirm(String result) {
				// TODO Auto-generated method stub
				ToastUtil.show(getActivity(), "设置成功");
				SpfUtils.put(getActivity(), ExtraString.BLOG_TYPE, channel.getChannelName());
				refresh();
			}
		});
		dialog.show();
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Channel channel = (Channel) parent.getAdapter().getItem(position);

		Intent intent = new Intent(getActivity(), ChannelDetailActivity.class);
		intent.putExtra(ExtraString.CHANNEL, channel);
		startActivity(intent);
	}

}
