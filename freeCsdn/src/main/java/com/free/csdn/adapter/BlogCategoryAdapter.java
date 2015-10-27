package com.free.csdn.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.free.csdn.R;
import com.free.csdn.bean.BlogCategory;

/**
 * 博客分类
 * 
 * @author tangqi
 * @data 2015年8月9日下午2:01:25
 */
public class BlogCategoryAdapter extends BaseAdapter {

	private Context context;
	private List<BlogCategory> list;
	private int selection = -1;

	public BlogCategoryAdapter(Context context, List<BlogCategory> list) {
		super();
		this.context = context;
		this.list = list;
	}

	public void setList(List<BlogCategory> list) {
		this.list = list;
	}

	public int getCount() {
		return list.size();
	}

	@Override
	public BlogCategory getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.listitem_blogtype, null);
			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvName.setText(getItem(position).getName());

		return convertView;
	}

	public void setSelectionPosition(int selection) {
		this.selection = selection;
		notifyDataSetChanged();
	}

	public int getSelectionPosition() {
		return this.selection;
	}

	static class ViewHolder {
		TextView tvName;
	}

}
