package com.free.csdn.util;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.free.csdn.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.widget.ImageView;

/**
 * 图片加载工具类
 *
 * @author tangqi
 * @info 使用Glide加载图片
 * @data 2015年8月15日上午9:41:15
 */
public class ImageLoaderUtils {

    /**
     * 加载图片
     *
     * @param url
     * @param imageView
     */
    public static void displayImg(String url, ImageView imageView) {
        // Glide.with(myFragment).load(url).centerCrop().placeholder(R.drawable.loading_spinner)
        // .crossFade().into(myImageView);
        Glide.with(imageView.getContext()).load(url).centerCrop().crossFade().placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default).into(imageView);
    }

    /**
     * 加载图片
     *
     * @param url
     * @param container
     * @param defaultResId 默认占位图片
     */
    public static void displayImg(String url, ImageView container, int defaultResId) {
        Glide.with(container.getContext()).load(url).centerCrop().crossFade().placeholder(defaultResId)
                .error(R.drawable.ic_default).into(container);
    }

    /**
     * 加载圆形图片
     *
     * @param url
     * @param imageView
     * @error 加载圆形图片或者其他自定义图片，需要作一些转化，详细请参考：
     * <a herf="http://www.jianshu.com/p/4a3177b57949">
     */
    public static void displayRoundImage(String url, final ImageView imageView) {
        final Context context = imageView.getContext().getApplicationContext();
        Glide.with(context).load(url).asBitmap().centerCrop().placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory
                        .create(context.getResources(), resource);
                // circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
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
