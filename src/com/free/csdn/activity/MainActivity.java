package com.free.csdn.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.free.csdn.R;
import com.free.csdn.adapter.DrawerAdapter;
import com.free.csdn.bean.DrawerItem;
import com.free.csdn.fragment.BloggerFragment;
import com.free.csdn.fragment.ChannelFragment;
import com.free.csdn.view.CircleImageView;
import com.free.csdn.view.drawerlayout.ActionBarDrawerToggle;
import com.free.csdn.view.drawerlayout.DrawerArrowDrawable;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

/**
 * 侧滑风格主Activity
 * 
 * @author tangqi
 * @data 2015年8月12日下午10:46:07
 */
public class MainActivity extends FragmentActivity implements OnClickListener {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private RelativeLayout rl;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerArrowDrawable drawerArrow;
	private CircleImageView iv_main_left_head;
	private RelativeLayout toprl;
	private ImageView login_tv;
	private LinearLayout animll_id;
	private TextView user_name;

	public static FragmentManager fm;
	private Boolean openOrClose = false;
	private String[] mMenuTitles = { "首页", "频道", "收藏", "关于", "设置" };
	private int[] mResId = { R.drawable.me_06, R.drawable.me_03, R.drawable.me_02,
			R.drawable.me_04, R.drawable.me_05 };
	private long exitTime;
	private final static long TIME_DIFF = 2 * 1000;

	private BloggerFragment bloggerFragment = null;
	private ChannelFragment channelFragment = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		toprl = (RelativeLayout) findViewById(R.id.toprl);
		animll_id = (LinearLayout) findViewById(R.id.animll_id);
		login_tv = (ImageView) findViewById(R.id.login_tv);
		user_name = (TextView) findViewById(R.id.user_name);
		iv_main_left_head = (CircleImageView) findViewById(R.id.iv_main_left_head);
		rl = (RelativeLayout) findViewById(R.id.rl);

		toprl.setOnClickListener(this);
		login_tv.setOnClickListener(this);
		animll_id.setOnClickListener(this);
		user_name.setOnClickListener(this);
		iv_main_left_head.setOnClickListener(this);

		initFragment();
		initDrawerLayout();
		initDrawerList();

		initUmengStatistics();
		initUmengUpdate();
	}

	/**
	 * 友盟数据统计
	 */
	private void initUmengStatistics() {
		MobclickAgent.setDebugMode(true);
		// SDK在统计Fragment时，需要关闭Activity自带的页面统计，
		// 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
		MobclickAgent.openActivityDurationTrack(false);
		// MobclickAgent.setAutoLocation(true);
		// MobclickAgent.setSessionContinueMillis(1000);

		MobclickAgent.updateOnlineConfig(this);
	}

	/**
	 * 友盟自动更新
	 */
	private void initUmengUpdate() {
		UmengUpdateAgent.update(this);
	}

	/**
	 * 初始化侧滑栏
	 */
	private void initDrawerLayout() {
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.navdrawer);

		drawerArrow = new DrawerArrowDrawable(this) {
			@Override
			public boolean isLayoutRtl() {
				return false;
			}
		};
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, drawerArrow,
				R.string.drawer_open, R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				invalidateOptionsMenu();
				openOrClose = false;
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();
				openOrClose = true;
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerToggle.syncState();
	}

	/**
	 * 初始化侧滑ListView
	 */
	private void initDrawerList() {
		List<DrawerItem> list = new ArrayList<DrawerItem>();
		for (int i = 0; i < mMenuTitles.length; i++) {
			DrawerItem drawerItem = new DrawerItem();
			drawerItem.setName(mMenuTitles[i]);
			drawerItem.setResId(mResId[i]);
			list.add(drawerItem);
		}
		final DrawerAdapter adapter = new DrawerAdapter(this, list);
		mDrawerList.setAdapter(adapter);
		adapter.setSelectionPosition(0);
		mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@SuppressLint({ "ResourceAsColor", "Recycle" })
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = null;
				switch (position) {
				case 0:
					if (bloggerFragment == null) {
						bloggerFragment = new BloggerFragment();
					}
					initFragment(bloggerFragment);
					setTitle(mMenuTitles[position]);
					adapter.setSelectionPosition(position);
					break;

				case 1:
					if (channelFragment == null) {
						channelFragment = new ChannelFragment();
					}
					initFragment(channelFragment);
					setTitle(mMenuTitles[position]);
					adapter.setSelectionPosition(position);
					break;

				case 2:
					intent = new Intent(MainActivity.this, BlogCollectActivity.class);
					break;

				case 3:
					intent = new Intent(MainActivity.this, AboutActivity.class);
					break;

				case 4:
					break;
				}

				if (intent != null) {
					startActivity(intent);
				} else {
					mDrawerLayout.closeDrawers();
					openOrClose = false;
				}
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			if (mDrawerLayout.isDrawerOpen(rl)) {
				mDrawerLayout.closeDrawer(rl);
				openOrClose = false;
			} else {
				mDrawerLayout.openDrawer(rl);
				openOrClose = true;
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	private void initFragment() {
		fm = getSupportFragmentManager();
		// 只當容器，主要內容已Fragment呈現
		if (bloggerFragment == null) {
			bloggerFragment = new BloggerFragment();
		}
		initFragment(bloggerFragment);
		setTitle(mMenuTitles[0]);
	}

	// 切換Fragment
	public void changeFragment(Fragment f) {
		changeFragment(f, false);
	}

	// 初始化Fragment(FragmentActivity中呼叫)
	public void initFragment(Fragment f) {
		changeFragment(f, true);
	}

	private void changeFragment(Fragment f, boolean init) {
		FragmentTransaction ft = fm.beginTransaction();
		// .setCustomAnimations(
		// R.anim.umeng_fb_slide_in_from_left,
		// R.anim.umeng_fb_slide_out_from_left,
		// R.anim.umeng_fb_slide_in_from_right,
		// R.anim.umeng_fb_slide_out_from_right);
		ft.replace(R.id.fragment_layout, f);
		if (!init)
			ft.addToBackStack(null);
		ft.commitAllowingStateLoss();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if (openOrClose == false) {
				if ((System.currentTimeMillis() - exitTime) > TIME_DIFF) {
					Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
					exitTime = System.currentTimeMillis();
				} else {
					System.exit(0);
				}
				return true;
			} else {
				mDrawerLayout.closeDrawers();
				return true;
			}
		}

		return super.onKeyDown(keyCode, event);
	}
}