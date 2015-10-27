package com.free.csdn.view.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.free.csdn.R;

/**
 * 加载对话框
 * 
 * @author tangqi
 * @data 2015年8月8日下午5:16:41
 */

public class LoadingDialog extends ProgressDialog {

	private String mMessage;

	// private Context mContext;

	public LoadingDialog(Context context, String message) {
		// TODO Auto-generated constructor stub
		super(context, R.style.Theme_Light_LoadingDialog);

		// this.mContext = context;
		this.mMessage = message;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.dialog_loading);
		TextView messageView = (TextView) findViewById(R.id.tv_loading_dialog);
		messageView.setText(mMessage);
	}

	/**
	 * 显示在底部
	 */
	public void showButtom() {
		// WindowManager windowManager = ((Activity)
		// mContext).getWindowManager();
		// Display display = windowManager.getDefaultDisplay();
		//
		// WindowManager.LayoutParams lp = getWindow().getAttributes();
		// lp.width = (int) (display.getWidth() * 0.8);
		// getWindow().setAttributes(lp);
		// super.show();
	}

}
