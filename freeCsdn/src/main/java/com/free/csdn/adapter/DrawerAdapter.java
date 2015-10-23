package com.free.csdn.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.free.csdn.R;
import com.free.csdn.bean.DrawerInfo;

/**
 * 侧滑列表
 * 
 * @author tangqi
 * @data 2015年8月9日下午2:01:25
 */
public class DrawerAdapter extends BaseAdapter {

	private Context context;
	private List<DrawerInfo> list;
	private int selection = -1;

	public DrawerAdapter(Context context, List<DrawerInfo> list) {
		super();
		this.context = context;
		this.list = list;
	}

	public void setList(List<DrawerInfo> list) {
		this.list = list;
	}

	public int getCount() {
		return list.size();
	}

	@Override
	public DrawerInfo getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.listitem_drawer, null);
			holder = new ViewHolder();
			holder.llDrawer = (LinearLayout) convertView.findViewById(R.id.llDrawer);
			holder.imvDrawer = (ImageView) convertView.findViewById(R.id.imvDrawer);
			holder.tvDrawer = (TextView) convertView.findViewById(R.id.tvDrawer);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tvDrawer.setText(getItem(position).getName());
		holder.imvDrawer.setImageResource(getItem(position).getResId());
		
		if(position == selection){
			holder.llDrawer.setBackgroundResource(R.color.gray_bg);
			holder.tvDrawer.setTextColor(context.getResources().getColor(R.color.main_color));
		}else{
			holder.llDrawer.setBackgroundResource(R.drawable.selector_drawer_bg_white);
			holder.tvDrawer.setTextColor(context.getResources().getColor(R.color.black_text));
		}

		return convertView;
	}
	
	public void setSelectionPosition(int selection){
		this.selection = selection;
		notifyDataSetChanged();
	}
	
	public int getSelectionPosition(){
		return this.selection;
	}

	static class ViewHolder {
		LinearLayout llDrawer;
		ImageView imvDrawer;
		TextView tvDrawer;
	}

}
