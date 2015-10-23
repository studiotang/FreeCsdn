package com.free.csdn.lib.umeng;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.free.csdn.R;
import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.SyncListener;
import com.umeng.fb.model.Conversation;
import com.umeng.fb.model.Reply;

/**
 * 自定义反馈界面
 * 
 * @author tangqi
 *
 */
@SuppressLint({ "InflateParams", "SimpleDateFormat" })
public class CustomActivity extends Activity {

	private ListView mListView;
	private Conversation mComversation;
	private Context mContext;
	private ReplyAdapter adapter;
	private Button sendBtn;
	private EditText inputEdit;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private final int VIEW_TYPE_COUNT = 2;
	private final int VIEW_TYPE_USER = 0;
	private final int VIEW_TYPE_DEV = 1;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			adapter.notifyDataSetChanged();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.umeng_fb_activity_custom);
		mContext = this;

		initView();
		mComversation = new FeedbackAgent(this).getDefaultConversation();
		adapter = new ReplyAdapter();
		mListView.setAdapter(adapter);
		sync();

	}

	private void initView() {
		TextView mTitleView = (TextView) findViewById(R.id.tv_title);
		mTitleView.setText(getString(R.string.feedback));

		ImageView mBackBtn = (ImageView) findViewById(R.id.btn_back);
		mBackBtn.setVisibility(View.VISIBLE);
		mBackBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		mListView = (ListView) findViewById(R.id.fb_reply_list);
		sendBtn = (Button) findViewById(R.id.fb_send_btn);
		inputEdit = (EditText) findViewById(R.id.fb_send_content);
		// 下拉刷新组件
		mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.fb_reply_refresh);
		sendBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String content = inputEdit.getText().toString();
				inputEdit.getEditableText().clear();
				if (!TextUtils.isEmpty(content)) {
					// 将内容添加到会话列表
					mComversation.addUserReply(content);
					// 刷新新ListView
					mHandler.sendMessage(new Message());
					// 数据同步
					sync();
				}
			}
		});

		// 下拉刷新
		mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				sync();
			}
		});

		// 顶部刷新的样式
//		mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light,
//				android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
	}

	// 数据同步
	private void sync() {

		mComversation.sync(new SyncListener() {

			@Override
			public void onSendUserReply(List<Reply> replyList) {
			}

			@Override
			public void onReceiveDevReply(List<Reply> replyList) {
				// SwipeRefreshLayout停止刷新
				mSwipeRefreshLayout.setRefreshing(false);
				// 发送消息，刷新ListView
				mHandler.sendMessage(new Message());
				// 如果开发者没有新的回复数据，则返回
				if (replyList == null || replyList.size() < 1) {
					return;
				}
			}
		});
		// 更新adapter，刷新ListView
		adapter.notifyDataSetChanged();
		mListView.smoothScrollToPosition(mComversation.getReplyList().size());
	}

	// adapter
	class ReplyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mComversation.getReplyList().size();
		}

		@Override
		public Object getItem(int arg0) {
			return mComversation.getReplyList().get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public int getViewTypeCount() {
			// 两种不同的Tiem布局
			return VIEW_TYPE_COUNT;
		}

		@Override
		public int getItemViewType(int position) {
			// 获取单条回复
			Reply reply = mComversation.getReplyList().get(position);
			if (Reply.TYPE_DEV_REPLY.equals(reply.type)) {
				// 开发者回复Item布局
				return VIEW_TYPE_DEV;
			} else {
				// 用户反馈、回复Item布局
				return VIEW_TYPE_USER;
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			// 获取单条回复
			Reply reply = mComversation.getReplyList().get(position);
			if (convertView == null) {
				// 根据Type的类型来加载不同的Item布局
				if (Reply.TYPE_DEV_REPLY.equals(reply.type)) {
					// 开发者的回复
					convertView = LayoutInflater.from(mContext).inflate(R.layout.umeng_fb_custom_dev_reply, null);
				} else {
					// 用户的反馈、回复
					convertView = LayoutInflater.from(mContext).inflate(R.layout.umeng_fb_custom_user_reply, null);
				}

				// 创建ViewHolder并获取各种View
				holder = new ViewHolder();
				holder.replyContent = (TextView) convertView.findViewById(R.id.fb_reply_content);
				holder.replyProgressBar = (ProgressBar) convertView.findViewById(R.id.fb_reply_progressBar);
				holder.replyStateFailed = (ImageView) convertView.findViewById(R.id.fb_reply_state_failed);
				holder.replyData = (TextView) convertView.findViewById(R.id.fb_reply_date);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// 以下是填充数据
			// 设置Reply的内容
			holder.replyContent.setText(reply.content);
			// 在App应用界面，对于开发者的Reply来讲status没有意义
			if (!Reply.TYPE_DEV_REPLY.equals(reply.type)) {
				// 根据Reply的状态来设置replyStateFailed的状态
				if (Reply.STATUS_NOT_SENT.equals(reply.status)) {
					holder.replyStateFailed.setVisibility(View.VISIBLE);
				} else {
					holder.replyStateFailed.setVisibility(View.GONE);
				}

				// 根据Reply的状态来设置replyProgressBar的状态
				if (Reply.STATUS_SENDING.equals(reply.status)) {
					holder.replyProgressBar.setVisibility(View.VISIBLE);
				} else {
					holder.replyProgressBar.setVisibility(View.GONE);
				}
			}

			// 回复的时间数据，这里仿照QQ两条Reply之间相差100000ms则展示时间
			if ((position + 1) < mComversation.getReplyList().size()) {
				Reply nextReply = mComversation.getReplyList().get(position + 1);
				if (nextReply.created_at - reply.created_at > 100000) {
					Date replyTime = new Date(reply.created_at);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					holder.replyData.setText(sdf.format(replyTime));
					holder.replyData.setVisibility(View.VISIBLE);
				}
			}
			return convertView;
		}

		class ViewHolder {
			TextView replyContent;
			ProgressBar replyProgressBar;
			ImageView replyStateFailed;
			TextView replyData;
		}
	}

}
