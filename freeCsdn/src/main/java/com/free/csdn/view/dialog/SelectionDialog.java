package com.free.csdn.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.free.csdn.R;
import com.free.csdn.view.dialog.BaseDialog.OnCancleListener;
import com.free.csdn.view.dialog.BaseDialog.OnConfirmListener;

/**
 * 选择对话框
 * 
 */
public class SelectionDialog extends Dialog {

	private TextView tvTitle, tvMessage;
	private Button btnCancle, btnConfirm;
	private String title, message, msgConfirm;
	private OnConfirmListener mOnConfirmListener;// 确定监听
	private OnCancleListener mOnCancleListener;// 取消监听

	public SelectionDialog(Context context, String message) {
		super(context, R.style.Theme_Light_FullScreenDialogAct);
		this.message = message;
	}

	public SelectionDialog(Context context, String title, String message) {
		super(context, R.style.Theme_Light_FullScreenDialogAct);
		this.title = title;
		this.message = message;
	}

	public SelectionDialog(Context context, String title, String message, String msgConfirm) {
		super(context, R.style.Theme_Light_FullScreenDialogAct);
		this.title = title;
		this.message = message;
		this.msgConfirm = msgConfirm;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_selection);
		initView();
		initListener();
	}

	private void initView() {
		btnConfirm = (Button) findViewById(R.id.btn_confirm_selection);
		btnCancle = (Button) findViewById(R.id.btn_cancle);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvMessage = (TextView) findViewById(R.id.tv_message);
		if (!TextUtils.isEmpty(title)) {
			tvTitle.setText(title);
		}
		if (!TextUtils.isEmpty(message)) {
			tvMessage.setText(message);
		}
		if (!TextUtils.isEmpty(msgConfirm)) {
			btnConfirm.setText(msgConfirm);
		} else {
			btnConfirm.setText("确定");
		}
	}

	private void initListener() {

		// 取消按钮
		btnCancle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dismiss();

				if (mOnCancleListener != null) {
					mOnCancleListener.onCancle(null);
				}
			}
		});

		// 确定按钮
		btnConfirm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dismiss();

				if (mOnConfirmListener != null) {
					mOnConfirmListener.onConfirm(null);
				}
			}
		});
	}

	/**
	 * 外部方法，设置确定监听器
	 * 
	 * @param dialogListener
	 */
	public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
		this.mOnConfirmListener = onConfirmListener;
	}

	/**
	 * 外部方法，设置取消监听器
	 * 
	 * @param dialogListener
	 */
	public void setOnCancleListener(OnCancleListener OnCancleListener) {
		this.mOnCancleListener = OnCancleListener;
	}

	/**
	 * 外部方法，显示对话框
	 */
	@Override
	public void show() {
		// TODO Auto-generated method stub
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		Point size = new Point();
		wm.getDefaultDisplay().getSize(size);

		super.show();
		getWindow().setLayout((int) (size.x * 9 / 10), LayoutParams.WRAP_CONTENT);
	}

}
