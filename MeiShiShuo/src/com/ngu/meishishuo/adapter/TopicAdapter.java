package com.ngu.meishishuo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ngu.meishishuo.R;
import com.ngu.meishishuo.bean.Comment;
import com.ngu.meishishuo.bean.Topic;
import com.ngu.meishishuo.view.MyListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class TopicAdapter extends BaseAdapter {
	private List<Topic> list;
	private Context context;
	private EditText et;//评论框
	private CommentAdapter myAdapter;
	private List<Comment> commList;
	public TopicAdapter(List<Topic> list, Context context, EditText edittext) {
		super();
		this.list = list;
		this.context = context;
		this.et=edittext;
	}

	@Override
	public int getCount() {
		// 
		if(list!=null){
			return list.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// 
		if(list!=null){
			return list.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// 
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//
		ViewHolder hold;
		if (convertView == null) {
			hold = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.fragment_topic_item, null);
			convertView.setTag(hold);
		}else {
			hold=(ViewHolder) convertView.getTag();
		}
		hold.imageview=(ImageView) convertView.findViewById(R.id.topic_imageview_head);
		hold.tv_name=(TextView) convertView.findViewById(R.id.topic_textview_user);
		hold.tv_time=(TextView) convertView.findViewById(R.id.topic_textview_time);
		hold.tv_content=(TextView) convertView.findViewById(R.id.topic_textview_content);
		hold.tv_comment=(TextView) convertView.findViewById(R.id.topic_textview_comment);
		hold.tv_praise=(TextView) convertView.findViewById(R.id.topic_textview_praise);
		hold.listview=(MyListView) convertView.findViewById(R.id.topic_listview_comment);

		hold.imageview.setImageResource(R.drawable.myhead);
		hold.tv_comment.setText(list.get(position).getCommentCount());
		hold.tv_content.setText(list.get(position).getContent());
		hold.tv_name.setText(list.get(position).getName());
		hold.tv_praise.setText(list.get(position).getPraiseCount());
		hold.tv_time.setText(list.get(position).getTime());
//		//评论
//		Comment comm;
//		commList=new ArrayList<Comment>();
//		for(int i=0;i<5;i++){
//			comm=new Comment();
//			comm.setContent("开心就好啊");
//			commList.add(comm);
//		}
//		myAdapter=new CommentAdapter(context, commList);
//		hold.listview.setAdapter(myAdapter);
//		//评论监听
//		hold.tv_comment.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// 
//				//et.setVisibility(View.VISIBLE);
//			}
//		});
		return convertView;
	}
	
	private class ViewHolder{
		public ImageView imageview;
		public TextView tv_name,tv_content,tv_time,tv_praise,tv_comment;
		public MyListView listview;
	}
}
