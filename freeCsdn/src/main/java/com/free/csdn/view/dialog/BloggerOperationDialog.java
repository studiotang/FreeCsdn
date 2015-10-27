package com.free.csdn.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.free.csdn.R;
import com.free.csdn.bean.Blogger;

/**
 * 博主相关操作
 * 
 * @author tangqi
 * @data 2015年8月8日下午9:54:05
 */

public class BloggerOperationDialog extends BaseDialog implements android.view.View.OnClickListener {

	private OnDeleteListener mOnDeleteListener;
	private OnStickListener mOnStickListener;
	private Blogger mBlogger;

	public BloggerOperationDialog(Context context, Blogger blogger) {
		super(context, R.style.Theme_Light_FullScreenDialogAct);
		// TODO Auto-generated constructor stub

		this.mBlogger = blogger;
		setContentView(R.layout.dialog_blogger_operation);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		TextView deleteView = (TextView) findViewById(R.id.tv_delete_blogger);
		TextView stickView = (TextView) findViewById(R.id.tv_stick_blogger);
		TextView cacleView = (TextView) findViewById(R.id.tv_cacle_operate);

		if (mBlogger.getIsTop() == 0) {
			stickView.setText("置顶博主");
		} else {
			stickView.setText("取消置顶");
		}

		deleteView.setOnClickListener(this);
		stickView.setOnClickListener(this);
		cacleView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		dismiss();

		switch (v.getId()) {
		case R.id.tv_delete_blogger:
			mOnDeleteListener.onDelete(null);
			break;

		case R.id.tv_stick_blogger:
			mOnStickListener.onStick(null);
			break;

		case R.id.tv_cacle_operate:
			// TODO
			break;

		default:
			break;
		}
	}

	public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
		this.mOnDeleteListener = onDeleteListener;
	}

	public void setOnStickListener(OnStickListener onStickListener) {
		this.mOnStickListener = onStickListener;
	}

}
