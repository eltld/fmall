package com.ngu.meishishuo.adapter;

import java.util.List;

import com.ngu.meishishuo.R;
import com.ngu.meishishuo.model.Comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter{
	private Context context;
	private List<Comment> list;
	
	public CommentAdapter(Context context, List<Comment> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// 
		if (list != null) {
			return list.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// 
		if (list != null) {
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
					R.layout.comment_item, null);
			convertView.setTag(hold);
		}else {
			hold=(ViewHolder) convertView.getTag();
		}
		
		hold.imageview=(ImageView) convertView.findViewById(R.id.comment_imageview_head);
		hold.tv_name=(TextView) convertView.findViewById(R.id.comment_textview_name);
		hold.tv_time=(TextView) convertView.findViewById(R.id.comment_textview_time);
		hold.tv_content=(TextView) convertView.findViewById(R.id.comment_textview_content);
		hold.tv_praise=(TextView) convertView.findViewById(R.id.comment_textview_praise);
		
		hold.imageview.setImageResource(R.drawable.myhead);
		hold.tv_name.setText(list.get(position).getName());
		hold.tv_time.setText(list.get(position).getTime());
		hold.tv_content.setText(list.get(position).getContent());
		return convertView;
	}
	
	private class ViewHolder {
		public ImageView imageview;
		public TextView tv_name,tv_time,tv_content,tv_praise;
	}
}
