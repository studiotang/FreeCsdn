package com.free.csdn.adapter;

import java.util.List;

import com.free.csdn.R;
import com.free.csdn.bean.Blogger;
import com.free.csdn.util.ImageLoaderUtils;
import com.free.csdn.view.CircleImageView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 博主列表
 * 
 * @author tangqi
 * @data 2015年8月9日下午2:01:25
 */

public class BloggerListAdapter extends BaseAdapter {

	private Context context;
	private List<Blogger> list;

	public BloggerListAdapter(Context context, List<Blogger> list) {
		super();
		this.context = context;
		this.list = list;
	}

	public void setList(List<Blogger> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Blogger getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.listitem_blogger, null);
			holder = new ViewHolder();
			holder.imvBlogger = (CircleImageView) convertView.findViewById(R.id.imv_blogger);
			holder.tvBlogTitle = (TextView) convertView.findViewById(R.id.tv_blog_title);
			holder.tvBlogDesc = (TextView) convertView.findViewById(R.id.tv_blog_desc);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (!TextUtils.isEmpty(getItem(position).getImgUrl())) {
			ImageLoaderUtils.displayImg(getItem(position).getImgUrl(), holder.imvBlogger);
		} else {
			holder.imvBlogger.setImageResource(R.drawable.ic_default);
		}
		if (getItem(position).getIsTop() == 1) {
			holder.tvBlogTitle.setText(getItem(position).getTitle() + "\b[顶]");
			holder.tvBlogTitle.setTextColor(context.getResources().getColor(R.color.blue_text));
		} else {
			holder.tvBlogTitle.setText(getItem(position).getTitle());
			holder.tvBlogTitle.setTextColor(context.getResources().getColor(R.color.black_text));
		}

		if (!TextUtils.isEmpty(getItem(position).getDescription())) {
			holder.tvBlogDesc.setVisibility(View.VISIBLE);
			holder.tvBlogDesc.setText(getItem(position).getDescription());
		} else {
			holder.tvBlogDesc.setVisibility(View.GONE);
		}

		return convertView;
	}

	static class ViewHolder {
		CircleImageView imvBlogger;
		TextView tvBlogTitle, tvBlogDesc;
	}

}
