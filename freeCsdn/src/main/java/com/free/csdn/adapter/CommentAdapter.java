package com.free.csdn.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.free.csdn.R;
import com.free.csdn.bean.Comment;
import com.free.csdn.config.AppConstants;
import com.free.csdn.util.ImageLoaderUtils;

/**
 *  评论列表适配器
 * 
 * @author tangqi
 * @data 2015年8月9日下午4:01:25
 */
public class CommentAdapter extends BaseAdapter {
	private ViewHolder holder;
	private LayoutInflater layoutInflater;
	private Context context;
	private List<Comment> list;

	private String replyText;

	public CommentAdapter(Context c) {
		super();
		this.context = c;
		layoutInflater = (LayoutInflater) LayoutInflater.from(this.context);
		list = new ArrayList<Comment>();
	}

	public void setList(List<Comment> list) {
		this.list = list;
	}

	public void addList(List<Comment> list) {
		this.list.addAll(list);
	}

	public void clearList() {
		this.list.clear();
	}

	public List<Comment> getList() {
		return list;
	}

	public void removeItem(int position) {
		if (list.size() > 0) {
			list.remove(position);
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Comment item = list.get(position); // 获取评论项
		if (null == convertView) {
			holder = new ViewHolder();
			switch (item.getType()) {
			case AppConstants.DEF_COMMENT_TYPE.PARENT: // 父项
				convertView = layoutInflater.inflate(R.layout.listitem_comment, null);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.content = (TextView) convertView.findViewById(R.id.content);
				holder.date = (TextView) convertView.findViewById(R.id.date);
				// holder.reply = (TextView) convertView
				// .findViewById(R.id.replyCount);
				holder.userface = (ImageView) convertView.findViewById(R.id.userface);

				break;
			case AppConstants.DEF_COMMENT_TYPE.CHILD: // 子项
				convertView = layoutInflater.inflate(R.layout.listitem_comment_child, null);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.content = (TextView) convertView.findViewById(R.id.content);
				holder.date = (TextView) convertView.findViewById(R.id.date);
				break;
			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (null != item) {
			switch (item.getType()) {
			case AppConstants.DEF_COMMENT_TYPE.PARENT:
				holder.name.setText(item.getUsername());
				holder.content.setText(Html.fromHtml(item.getContent()));
				holder.date.setText(item.getPostTime());
				// holder.reply.setText(item.getReplyCount());
				ImageLoaderUtils.displayRoundImage(item.getUserface(), holder.userface);
				break;
			case AppConstants.DEF_COMMENT_TYPE.CHILD:
				holder.name.setText(item.getUsername());
				replyText = item.getContent().replace("[reply]", "【");
				replyText = replyText.replace("[/reply]", "】");
				holder.content.setText(Html.fromHtml(replyText));
				holder.date.setText(item.getPostTime());
				break;
			default:
				break;
			}
		}
		return convertView;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		switch (list.get(position).getType()) {
		case AppConstants.DEF_COMMENT_TYPE.PARENT:
			return 0;
		case AppConstants.DEF_COMMENT_TYPE.CHILD:
			return 1;
		}
		return 1;
	}

	@Override
	public boolean isEnabled(int position) {
		return true;
	}

	private class ViewHolder {
		// TextView id;
		TextView date;
		TextView name;
		TextView content;
		ImageView userface;
		// TextView reply;
	}
}
