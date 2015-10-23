package com.free.csdn.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.free.csdn.R;
import com.free.csdn.base.BaseActivity;


/**
 * 更新日志
 *
 * @author  tangqi
 * @data    2015年8月15日下午10:05:51
 */
public class UpdateLogActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_log);

		initView();
	}	

	private void initView() {
		// TODO Auto-generated method stub
		TextView mTitleView = (TextView) findViewById(R.id.tv_title);
		mTitleView.setText("更新日志");

		ImageView mBackBtn = (ImageView) findViewById(R.id.btn_back);
		mBackBtn.setVisibility(View.VISIBLE);
		mBackBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		WebView mWebView = (WebView) findViewById(R.id.webview_update_log);
		mWebView.loadUrl("file:///android_asset/about/about.html");
	}
}
