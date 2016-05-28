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

public class SearchAdapter extends BaseAdapter {
	private List<MeiShi> list;
	private Context context;
	public SearchAdapter(List<MeiShi> list, Context context) {
		super();
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		// 
		if(list!=null){
			return list.size();
		}else{
			return 0;
		}
		
	}

	@Override
	public Object getItem(int position) {
		// 
		if(list!=null){
			return list.get(position);
		}else{
			return null;
		}
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
					R.layout.activity_search_item, null);
			convertView.setTag(hold);
		}else {
			hold=(ViewHolder) convertView.getTag();
		}
		hold.tv_name=(TextView) convertView.findViewById(R.id.search_tv_name);
		hold.tv_count=(TextView) convertView.findViewById(R.id.search_tv_count);
		hold.tv_food=(TextView) convertView.findViewById(R.id.search_tv_food);
		hold.tv_name.setText(list.get(position).getName());
		hold.tv_count.setText(list.get(position).getCount()+"人浏览");
		hold.tv_food.setText(list.get(position).getFood());
		return convertView;
	}
	private class ViewHolder{
		TextView tv_name,tv_count,tv_food;
	}
}
