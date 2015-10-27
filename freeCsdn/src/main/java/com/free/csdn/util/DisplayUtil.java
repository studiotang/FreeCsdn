package com.free.csdn.util;

import android.content.Context;

/**
 * 显示数据相关计算
 * @author Administrator
 *
 */
public class DisplayUtil {
	
	public static int dp2px(Context context, float dp) { 
	    final float scale = context.getResources().getDisplayMetrics().density; 
	    return (int) (dp * scale + 0.5f); 
	}
	
	public static int px2dp(Context context, float px) { 
	    final float scale = context.getResources().getDisplayMetrics().density; 
	    return (int) (px / scale + 0.5f); 
	} 
}
