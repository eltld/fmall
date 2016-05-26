package com.ngu.meishishuo.adapter;

import java.util.List;

import com.ngu.meishishuo.R;
import com.ngu.meishishuo.customview.MyDialog;
import com.ngu.meishishuo.model.MeiShi;
import com.ngu.meishishuo.utils.MeiShiDao;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CollectionAdapter extends BaseAdapter {
	private List<MeiShi> list;
	private Context context;
	private MeiShiDao dao;
	public CollectionAdapter(List<MeiShi> list, Context context,MeiShiDao dao) {
		super();
		this.list = list;
		this.context = context;
		this.dao=dao;
	}

	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(list!=null)
		{
			return list.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(list!=null)
		{
			return list.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
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
		
		MeiShi item=list.get(position);
		hold.tv_name.setText(item.getName());

		return convertView;
	}
	private class ViewHolder{
		public TextView tv_name;
	}
}
