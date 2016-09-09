package com.example.tmall;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.example.tmall.adapter.OrderAdapter;
import com.example.tmall.bean.Product;
import com.example.tmall.constant.HttpUrl;
import com.example.tmall.http.OkHttpUtil;
import com.example.tmall.user.AddressActivity;
import com.example.tmall.view.MyListView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.Request;
import okhttp3.Response;

public class OrderActivity extends Activity implements OnClickListener{
	
	
	private RelativeLayout address;//
	private TextView Back;//返回
	private TextView ensureOrder;//确认订单
	private MyListView listView;//订单列表
	private OrderAdapter mAdapter;
	private List<Product> orderList;
	private TextView tv_name,tv_phone,tv_address,tv_totalprice;
	private String TotalPrice;
	private PayPopupWindow mPayWindow;
	private SimpleDateFormat sDateFormat;
	private String date;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_activity);
		initView();
		loadData();
	}
	@Override
	protected void onResume() {
		super.onResume();
		updateAddress();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Datas.ensureOrderProducts.clear();
	}
	
	private void updateAddress(){
		tv_name.setText(Datas.morenAddress.getName());
		tv_phone.setText(Datas.morenAddress.getPhone());
		tv_address.setText(Datas.morenAddress.getCity()+Datas.morenAddress.getDetail());
	}
	
	public static final String TOTALPRICE="total_price";
	
	private void initView(){
		sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		date = sDateFormat.format(new java.util.Date());
		
		Back=(TextView) findViewById(R.id.iv_top_back);
		Back.setOnClickListener(this);
		ensureOrder=(TextView) findViewById(R.id.tv_ensure_order);
		ensureOrder.setOnClickListener(this);
		listView=(MyListView) findViewById(R.id.order_listview);
		address=(RelativeLayout) findViewById(R.id.order_address);
		address.setOnClickListener(this);
		tv_name=(TextView) findViewById(R.id.order_tv_name);
		tv_phone=(TextView) findViewById(R.id.order_tv_phone);
		tv_address=(TextView) findViewById(R.id.order_tv_address);
		tv_totalprice=(TextView) findViewById(R.id.order_tv_totalprice);
		Intent intent =getIntent();
		TotalPrice=intent.getStringExtra(TOTALPRICE);
		tv_totalprice.setText("总金额 ￥"+TotalPrice+"元");
		
		mPayWindow=new PayPopupWindow(OrderActivity.this, TotalPrice);
		mPayWindow.setOnClickListener(new PayPopupWindow.OnPayWindowClickListener() {
			
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				new SubmitOrderTask().execute("");
				Toast.makeText(OrderActivity.this,"订单已提交！", Toast.LENGTH_SHORT).show();
				OrderActivity.this.finish();
			}
		});
	}
	private void loadData(){
		orderList=Datas.ensureOrderProducts;
		mAdapter=new OrderAdapter(orderList, OrderActivity.this);
		listView.setAdapter(mAdapter);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.tv_ensure_order:
			if(Datas.adderssList.size()==0){
				Toast.makeText(OrderActivity.this, "请先添加收货地址！", Toast.LENGTH_SHORT).show();
			}else{
				mPayWindow.showAsDropDown(v);
			}
		
			break;
		case R.id.order_address:
			Intent intent = new Intent(OrderActivity.this,AddressActivity.class );
			startActivity(intent);
			break;
		case R.id.iv_top_back:
			finish();
			break;
		}
	}
	
	
	private final String SUCCESS="update_success";
	private final String FAIL="update_fail";
	
	/*
	 * 提交订单
	 */
	private class SubmitOrderTask extends AsyncTask<String, Integer, String>{
		ProgressDialog progressDialog = new ProgressDialog(OrderActivity.this);
		@Override
		protected void onPreExecute() {
			// 
			super.onPreExecute();
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("正在处理···");
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			// 
			super.onPostExecute(result);
			progressDialog.dismiss();
			if(result.equals(SUCCESS)){
				Toast.makeText(OrderActivity.this,"订单已提交！", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(OrderActivity.this,"抱歉，出现了点小问题！", Toast.LENGTH_SHORT).show();
			}
				
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// 
			super.onProgressUpdate(values);
		}

		@Override
		protected String doInBackground(String... params) {
			
			String HttpArg;
			String result=FAIL;
			String dingDanHao="";//订单号
			HttpArg="?sql=INSERT INTO dingdan (yhID, xdsj, zfje) VALUES ( '"+Datas.currentUser.getUserName()+"', '"+date+"', '"+TotalPrice+"')";
			Request request=new Request.Builder()
	                .get()
	                .tag(this)
	                .url(HttpUrl.ORDER_URL+HttpArg)
	                .build();
			Response response = null;
			try {
				response = OkHttpUtil.getOkHttpClient().newCall(request).execute();
				if (response.isSuccessful()) {
					result=response.body().string();
				} else {
					throw new IOException("Unexpected code " + response);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			//
			
			if(result.equals(SUCCESS)){
				request=new Request.Builder()
		                .get()
		                .tag(this)
		                .url("http://120.27.124.129:8080/shopping/sqlQueryMaxdingdan?sql=SELECT MAX(dingdan.ddh) AS ddh FROM dingdan")
		                .build();
				try {
					response = OkHttpUtil.getOkHttpClient().newCall(request).execute();
					if (response.isSuccessful()) {
						dingDanHao=response.body().string();
						
					} else {
						throw new IOException("Unexpected code " + response);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//
			result=FAIL;
			if(dingDanHao!=""){
				Product product;
				for(int i=0;i<Datas.ensureOrderProducts.size();i++){
					product=Datas.ensureOrderProducts.get(i);
					HttpArg="?sql=INSERT INTO dinggouxiangmu (ddh, spID, spSl) VALUES ("+dingDanHao+", "+product.getId()+","+product.getProCount() +")";
					request=new Request.Builder()
			                .get()
			                .tag(this)
			                .url(HttpUrl.ORDER_URL+HttpArg)
			                .build();
					try {
						response = OkHttpUtil.getOkHttpClient().newCall(request).execute();
						if (response.isSuccessful()) {
							result=response.body().string();
						
						} else {
							throw new IOException("Unexpected code " + response);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return result;
		}
		
	}

}
