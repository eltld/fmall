package com.example.tmall.adapter;

import java.util.List;

import com.example.tmall.R;
import com.example.tmall.bean.Product;
import com.example.tmall.constant.HttpUrl;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderAdapter extends BaseAdapter {
	private List<Product> orderList;
	private Context mContext;
	public OrderAdapter(List<Product> orderList, Context mContext) {
		super();
		this.orderList = orderList;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return orderList==null?0:orderList.size();
	}

	@Override
	public Object getItem(int position) {
		return orderList==null?null:orderList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 
		ViewHolder holder;
		if(convertView==null){
			convertView=LayoutInflater.from(mContext).inflate(R.layout.item_order,null);
			holder=new ViewHolder();
			holder.setView(convertView);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
		Product product=orderList.get(position);
		
		Picasso.with(mContext).load(HttpUrl._HTTP+product.getProImg()).into(holder.iv_pic);
		holder.tv_name.setText(product.getProName());
		holder.tv_msg.setText(product.getProMsg());
		holder.tv_price.setText("гд"+String.valueOf(product.getProPrice()));
		holder.tv_count.setText("x"+String.valueOf(product.getProCount()));
		return convertView;
	}
	
	private class ViewHolder{
		private TextView tv_name,tv_msg,tv_price,tv_count;
		private ImageView iv_pic;
		
		public void setView(View view){
			tv_name=(TextView) view.findViewById(R.id.order_tv_name);
			tv_msg=(TextView) view.findViewById(R.id.order_tv_msg);
			tv_price=(TextView) view.findViewById(R.id.order_tv_price);
			tv_count=(TextView) view.findViewById(R.id.order_tv_num);
			iv_pic=(ImageView) view.findViewById(R.id.order_iv_pic);
		}
	}

}
