package com.free.blog.ui.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.free.blog.R;
import com.free.blog.model.entity.BlogCategory;

import java.util.List;


/**
 * 博客分类
 *
 * @author tangqi
 * @since 2015年8月9日下午2:01:25
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
        return list != null ? list.size() : 0;
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
        ViewHolder holder;
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

    @SuppressWarnings("unused")
    public void setSelectionPosition(int selection) {
        this.selection = selection;
        notifyDataSetChanged();
    }

    @SuppressWarnings("unused")
    public int getSelectionPosition() {
        return this.selection;
    }

    private static class ViewHolder {
        TextView tvName;
    }
}
