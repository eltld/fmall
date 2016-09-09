package com.example.tmall.adapter;

import java.util.List;

import com.example.tmall.Datas;
import com.example.tmall.R;
import com.example.tmall.bean.Address;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class AddressAdapter extends BaseAdapter {
	private List<Address> addressList;
	private Context mContext;
	private int checkedIndex=0;
	public AddressAdapter(List<Address> addressList, Context mContext) {
		super();
		this.addressList = addressList;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		
		return addressList==null?0:addressList.size();
	}

	@Override
	public Object getItem(int position) {

		return addressList==null?null:addressList.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(mContext).inflate(R.layout.item_address, null);
			holder.setView(convertView);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		final Address address=addressList.get(position);
		holder.tv_name.setText(address.getName());
		holder.tv_phone.setText(address.getPhone());
		holder.tv_detail.setText(address.getCity()+address.getDetail());
		holder.tv_edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
		holder.tv_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Datas.adderssList.remove(address);
				notifyDataSetChanged();
			}
		});
		
		if(position==checkedIndex){
			holder.cb_moren.setChecked(true);
			Datas.morenAddress=address;
		}else{
			holder.cb_moren.setChecked(false);
		}
		return convertView;
	}
	
	public void setCheckedIndex(int position){
		this.checkedIndex=position;
		notifyDataSetChanged();
	}
	
	private class ViewHolder{
		public TextView tv_name,tv_phone,tv_detail,tv_edit,tv_delete;
		public CheckBox cb_moren;
		public void setView(View view){
			tv_name=(TextView) view.findViewById(R.id.address_name);
			tv_phone=(TextView) view.findViewById(R.id.address_phone);
			tv_detail=(TextView) view.findViewById(R.id.address_detail);
			tv_edit=(TextView) view.findViewById(R.id.address_edit);
			tv_delete=(TextView) view.findViewById(R.id.address_delete);
			cb_moren=(CheckBox) view.findViewById(R.id.address_checkbox);
		}
	}
}
