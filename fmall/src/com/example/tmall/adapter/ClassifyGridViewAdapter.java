package com.example.tmall.adapter;


import java.util.ArrayList;

import com.example.tmall.R;
import com.example.tmall.bean.Type;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ClassifyGridViewAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
	    private ArrayList<Type> list;
	    private Context context;
	    private Type type;
		public ClassifyGridViewAdapter(Context context,ArrayList<Type> list){
			mInflater=LayoutInflater.from(context);
			this.list=list;
			this.context=context;
		}
		
		@Override
		public int getCount() {
			if(list!=null&&list.size()>0)
				return list.size();
			else
			    return 0;
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final MyView view;
			if(convertView==null){
				view=new MyView();
				convertView=mInflater.inflate(R.layout.item_gridview_classify, null);
				view.icon=(ImageView)convertView.findViewById(R.id.typeicon);
				view.name=(TextView)convertView.findViewById(R.id.typename);
				convertView.setTag(view);
			}else{
				view=(MyView) convertView.getTag();
			}
			if(list!=null&&list.size()>0)
			{
				type=list.get(position);
				view.name.setText(type.getTypename());
				view.icon.setImageResource(type.getId());
			}
			
	        return convertView;
		}

		
		private class MyView{
			private ImageView icon;		
			private TextView name;
		}
		
}
