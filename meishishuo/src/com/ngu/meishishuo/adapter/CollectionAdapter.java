package com.ngu.meishishuo.adapter;

import java.util.List;

import com.ngu.meishishuo.R;

import com.ngu.meishishuo.model.Collection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CollectionAdapter extends BaseAdapter {
	private List<Collection> list;
	private Context context;
	public CollectionAdapter(List<Collection> list, Context context) {
		super();
		this.list = list;
		this.context = context;
	}

	
	@Override
	public int getCount() {
		// 
		if(list!=null)
		{
			return list.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// 
		if(list!=null)
		{
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
					R.layout.activity_collection_item, null);
			convertView.setTag(hold);
		}else {
			hold=(ViewHolder) convertView.getTag();
		}
		
		hold.tv_name=(TextView) convertView.findViewById(R.id.tv_collection_name);
		hold.tv_description=(TextView) convertView.findViewById(R.id.tv_collection_description);
		hold.tv_time=(TextView) convertView.findViewById(R.id.tv_collection_time);

		Collection item=list.get(position);
		hold.tv_name.setText(item.getName());
		hold.tv_description.setText(item.getDescription());
		hold.tv_time.setText(item.getTime());
		return convertView;
	}
	private class ViewHolder{
		public TextView tv_name,tv_description,tv_time;
	}
}
