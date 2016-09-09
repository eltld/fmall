package com.example.tmall.classify;

import com.example.tmall.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ClassifyAdapter extends BaseAdapter{
	private String [] classifyList;
	private Context mContext;
	private int selectedIndex = 0;
	public ClassifyAdapter(String[] classifyList, Context mContext) {
		super();
		this.classifyList = classifyList;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		
		return classifyList==null?0:classifyList.length;
	}

	@Override
	public Object getItem(int position) {
		
		return classifyList==null?null:classifyList[position];
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(mContext).inflate(R.layout.item_classify_list, null);
			holder.textView=(TextView) convertView.findViewById(R.id.item_classify_textview);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.textView.setText(classifyList[position]);
		
		 if (position == selectedIndex) {
			 holder.textView.setBackgroundResource(android.R.color.white);
			 holder.textView.setTextColor(0xffff5d5e);
	        } else {
	        	holder.textView.setBackgroundResource(android.R.color.transparent);
	        	holder.textView.setTextColor(0xff000000);
	        }
		return convertView;
	}
	public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
        this.notifyDataSetChanged();
    }
	private class ViewHolder{
		public TextView textView;
	}
}
