package com.free.csdn.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.umeng.analytics.MobclickAgent;

/**
 * FragmentActivity-基类
 * 
 * @author tangqi
 * @data 2015年8月10日上午12:07:57
 */

public class BaseActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		BaseApplication.getInstance().addActivity(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}
}
