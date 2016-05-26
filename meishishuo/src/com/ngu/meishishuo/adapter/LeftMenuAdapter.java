package com.ngu.meishishuo.adapter;

import java.util.List;

import com.ngu.meishishuo.R;
import com.ngu.meishishuo.model.LeftMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LeftMenuAdapter extends BaseAdapter {
	private Context context;
	private List<LeftMenu> list;
	
	public LeftMenuAdapter(Context context, List<LeftMenu> list) {
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
		ViewHold hold;
		if (convertView == null) {
			hold = new ViewHold();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.leftmenu_item, null);
			convertView.setTag(hold);
		}else {
			hold=(ViewHold) convertView.getTag();
		}
		
		hold.imageView=(ImageView) convertView.findViewById(R.id.menu_item_imageview);
		hold.textView=(TextView) convertView.findViewById(R.id.menu_item_textview);
		
		hold.imageView.setImageResource(list.get(position).getImageView());
		hold.textView.setText(list.get(position).getText());
		return convertView;
	}

	private class ViewHold {
		public ImageView imageView;
		public TextView textView;
	}
	
}
