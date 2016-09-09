package com.example.tmall.user;

import java.util.List;

import com.example.tmall.Datas;
import com.example.tmall.R;
import com.example.tmall.R.id;
import com.example.tmall.R.layout;
import com.example.tmall.adapter.AddressAdapter;
import com.example.tmall.bean.Address;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
/*
 * 收货地址管理
 */
public class AddressActivity extends Activity implements OnClickListener,OnItemClickListener{
	private TextView addAddress;
	private ListView listView;
	private AddressAdapter mAdapter;
	private List<Address> addressList;
	private TextView back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.address_activity);
		initView();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mAdapter.notifyDataSetChanged();
	}
	private void initView() {
		addAddress=(TextView) findViewById(R.id.address_tv_add);
		addAddress.setOnClickListener(this);
		listView=(ListView) findViewById(R.id.address_listview);
		addressList=Datas.adderssList;
		mAdapter=new AddressAdapter(addressList, this);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(this);
		back=(TextView) findViewById(R.id.iv_top_back);
		back.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.address_tv_add:
			Intent intent=new Intent(AddressActivity.this,NewAddressActivity.class);
			startActivity(intent);
			break;
		case R.id.iv_top_back:
			AddressActivity.this.finish();
			break;
		default:
			break;
		}
		
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mAdapter.setCheckedIndex(position);
		
	}
	
}
