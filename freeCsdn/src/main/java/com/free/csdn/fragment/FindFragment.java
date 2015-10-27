package com.free.csdn.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.free.csdn.R;
import com.free.csdn.base.BaseFragment;
import com.free.csdn.util.ToastUtil;

/**
 * 发现
 * 
 * @author tangqi
 * @data 2015年8月9日上午11:08:13
 */

public class FindFragment extends BaseFragment implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_find, container, false);
		initView(view);
		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		TextView mTitleView = (TextView) view.findViewById(R.id.tv_title);
		mTitleView.setText("热门最新");

		LinearLayout mFindView01 = (LinearLayout) view.findViewById(R.id.ll_find_01);
		LinearLayout mFindView02 = (LinearLayout) view.findViewById(R.id.ll_find_02);
		LinearLayout mFindView03 = (LinearLayout) view.findViewById(R.id.ll_find_03);
		LinearLayout mFindView04 = (LinearLayout) view.findViewById(R.id.ll_find_04);
		LinearLayout mFindView05 = (LinearLayout) view.findViewById(R.id.ll_find_05);

		mFindView01.setOnClickListener(this);
		mFindView02.setOnClickListener(this);
		mFindView03.setOnClickListener(this);
		mFindView04.setOnClickListener(this);
		mFindView05.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		default:
			break;
		}

		ToastUtil.showCenter(getActivity(), getActivity().getString(R.string.coming_soon));
	}
}
