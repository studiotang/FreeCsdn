package com.free.csdn.config;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.free.csdn.R;
import com.free.csdn.bean.Channel;
import com.free.csdn.config.CategoryManager.CategoryName;
import com.free.csdn.config.CategoryManager.CategoryUrl;

/**
 * 频道管理
 * 
 * @author tangqi
 * @data 2015年8月9日下午2:27:17
 */

public class ChannelManager {

	String[] mChannelNameArray = { CategoryName.ANDROID, CategoryName.MOBILE, CategoryName.WEB, CategoryName.ENTERPRISE, CategoryName.CODE,
			CategoryName.WWW, CategoryName.DATABASE, CategoryName.SYSTEM, CategoryName.CLOUD, CategoryName.SOFTWARE };

	int[] mResourceId = { R.drawable.logo_dropbox, R.drawable.logo_evernote, R.drawable.logo_googleplus, R.drawable.logo_neteasemicroblog,
			R.drawable.logo_yixinmoments, R.drawable.logo_pinterest, R.drawable.logo_sohumicroblog, R.drawable.logo_twitter,
			R.drawable.logo_vkontakte, R.drawable.logo_yixin };

	String[] mUrls = { CategoryUrl.ANDROID, CategoryUrl.MOBILE, CategoryUrl.WEB, CategoryUrl.ENTERPRISE, CategoryUrl.CODE, CategoryUrl.WWW,
			CategoryUrl.DATABASE, CategoryUrl.SYSTEM, CategoryUrl.CLOUD, CategoryUrl.SOFTWARE, };

	public ChannelManager(Context context) {
		// TODO Auto-generated constructor stub
	}

	public List<Channel> getChannelList() {
		List<Channel> list = new ArrayList<Channel>();
		for (int i = 0; i < mChannelNameArray.length; i++) {
			Channel channel = new Channel();
			channel.setChannelName(mChannelNameArray[i]);
			channel.setImgResourceId(mResourceId[i]);
			channel.setUrl(mUrls[i]);
			list.add(channel);
		}
		return list;
	}
}
