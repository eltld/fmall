package com.ngu.meishishuo.adapter;

import java.util.List;

import com.ngu.meishishuo.R;

import com.ngu.meishishuo.model.MeiShi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class ClassifyAdapter extends BaseAdapter{
	private Context context;
	private List<MeiShi> classifyList;
	public ClassifyAdapter(Context context, List<MeiShi> classifyList) {
		this.context = context;
		this.classifyList = classifyList;
	}

	@Override
	public int getCount() {
		// 
		if(classifyList!=null)
		{
			return classifyList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// 
		if(classifyList!=null)
		{
			return classifyList.get(position);
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
					R.layout.fragment_classify_item, null);
			convertView.setTag(hold);
		}else {
			hold=(ViewHolder) convertView.getTag();
		}
		
		hold.textView=(TextView) convertView.findViewById(R.id.classify_item_tv);
		
		hold.textView.setText("#"+classifyList.get(position).getName());
		//hold.textView.setBackgroundResource(R.drawable.bg_classify);
		return convertView;
	}
	private class ViewHolder{
		public TextView textView;
	}
}
