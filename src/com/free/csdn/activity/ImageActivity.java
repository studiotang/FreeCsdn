package com.free.csdn.activity;

import com.free.csdn.R;
import com.free.csdn.util.FileUtil;
import com.free.csdn.util.HttpUtil;
import com.free.csdn.util.ImageLoaderUtils;
import com.polites.android.GestureImageView;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * 2014/8/13 显示图片的界面
 * 
 * @author wwj_748
 * 
 */
@SuppressLint("NewApi")
public class ImageActivity extends BaseActivity {

	private String url; // 图片地址

	private GestureImageView imageView; // 图片组件
	private ProgressBar progressBar; // 进度条

	private ImageView backBtn; // 回退按钮
	private ImageView downLoadBtn; // 下载按钮

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_image_page);

		Bundle bundle = getIntent().getExtras();
		url = bundle.getString("url", "");
		System.out.println("image url -->" + url);

		imageView = (GestureImageView) findViewById(R.id.image);
		progressBar = (ProgressBar) findViewById(R.id.loading);

		backBtn = (ImageView) findViewById(R.id.back);

		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		downLoadBtn = (ImageView) findViewById(R.id.download);
		downLoadBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				imageView.setDrawingCacheEnabled(true);
				if (FileUtil.writeSDCard(url, imageView.getDrawingCache())) {
					Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "保存失败", Toast.LENGTH_SHORT).show();
				}
				imageView.setDrawingCacheEnabled(false);
			}
		});
		new MainTask().execute(url);
	}

	private class MainTask extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap temp = HttpUtil.HttpGetBmp(params[0]);
			return temp;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			if (result == null) {
				Toast.makeText(ImageActivity.this, "网络信号不佳", Toast.LENGTH_LONG).show();
				ImageLoaderUtils.displayImg(url, imageView);
			} else {
				imageView.setImageBitmap(result);
			}
			progressBar.setVisibility(View.GONE);
			super.onPostExecute(result);
		}

	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.push_no, R.anim.push_right_out);
	}
}