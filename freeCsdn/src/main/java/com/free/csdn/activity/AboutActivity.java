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
 * 关于
 * 
 * @author tangqi
 * @data 2015年8月9日下午11:09:57
 */

public class AboutActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		TextView mTitleView = (TextView) findViewById(R.id.tv_title);
		mTitleView.setText("关于");

		ImageView mBackBtn = (ImageView) findViewById(R.id.btn_back);
		mBackBtn.setVisibility(View.VISIBLE);
		mBackBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		WebView mWebView = (WebView) findViewById(R.id.webview_about);
		mWebView.loadUrl("file:///android_asset/about/about.html");
	}
}
