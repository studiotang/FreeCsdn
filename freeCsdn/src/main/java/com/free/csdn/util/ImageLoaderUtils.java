package com.free.csdn.util;

import com.bumptech.glide.Glide;
import com.free.csdn.R;

import android.text.TextUtils;
import android.widget.ImageView;

/**
 * 图片加载工具类
 * 
 * @info 使用Glide加载图片
 * @author tangqi
 * @data 2015年8月15日上午9:41:15
 */
public class ImageLoaderUtils {

	/**
	 * 加载图片
	 * 
	 * @param url
	 * @param container
	 */
	public static void displayImg(String url, ImageView container) {
		// Glide.with(myFragment).load(url).centerCrop().placeholder(R.drawable.loading_spinner)
		// .crossFade().into(myImageView);
		Glide.with(container.getContext()).load(url).centerCrop().crossFade()
				.error(R.drawable.ic_default).into(container);
	}

	/**
	 * 加载图片
	 * 
	 * @param url
	 * @param container
	 * @param defaultResId
	 *            默认占位图片
	 */
	public static void displayImg(String url, ImageView container, int defaultResId) {
		Glide.with(container.getContext()).load(url).centerCrop().crossFade()
				.placeholder(defaultResId).error(R.drawable.ic_default).into(container);
	}

	/**
	 * 加载SD卡中的图片(缓存内存)
	 * 
	 * @param url
	 * @param container
	 */
	public static void displaySdcardImg(String url, ImageView container) {
		if (TextUtils.isEmpty(url)) {
			return;
		}

		String fileUrl = "";
		if (url.contains("file:/")) {
			fileUrl = url;
		} else {
			fileUrl = "file:/" + url;
		}
		displayImg(fileUrl, container);
	}
}
