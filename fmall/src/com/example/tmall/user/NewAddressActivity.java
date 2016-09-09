package com.example.tmall.user;

import java.io.IOException;

import com.example.tmall.Datas;
import com.example.tmall.OrderActivity;
import com.example.tmall.R;
import com.example.tmall.R.id;
import com.example.tmall.R.layout;
import com.example.tmall.bean.Address;
import com.example.tmall.constant.HttpUrl;
import com.example.tmall.http.OkHttpUtil;
import com.example.tmall.view.CityDialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.Request;
import okhttp3.Response;
/*
 * 新增收货地址
 */
public class NewAddressActivity extends Activity implements OnClickListener{
	
	private RelativeLayout city;
	private EditText et_name,et_phone,et_street,et_mail,et_detail;
	private Button btn_save;
	private TextView Back;//返回
	private TextView tv_city;
	private CityDialog cityDialog;
	private Address address=new Address();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newaddress_activity);
		initView();
	}
	private void initView() {
		tv_city=(TextView) findViewById(R.id.addaddress_tv_city);
		et_name=(EditText) findViewById(R.id.addaddress_name);
		et_phone=(EditText) findViewById(R.id.addaddress_phone);
		city= (RelativeLayout) findViewById(R.id.addaddress_city);
		city.setOnClickListener(this);
		et_street=(EditText) findViewById(R.id.addaddress_street);
		et_mail=(EditText) findViewById(R.id.addaddress_mail);
		et_detail=(EditText) findViewById(R.id.addaddress_detail);
		btn_save=(Button) findViewById(R.id.addaddress_btn_save);
		btn_save.setOnClickListener(this);
		Back=(TextView) findViewById(R.id.iv_top_back);
		Back.setOnClickListener(this);
		cityDialog=new CityDialog(NewAddressActivity.this);
		cityDialog.setOnCityChooseListener(new CityDialog.OnCityChooseListener() {
			
			@Override
			public void onChoose(String city) {
				tv_city.setText(city);
				address.setCity(city);
			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.addaddress_city://选择城市
				cityDialog.show();
				break;
			case R.id.addaddress_btn_save:
				String name=et_name.getText().toString().trim();
				String phone=et_phone.getText().toString().trim();
				String street = et_street.getText().toString().trim();
				String detail=et_detail.getText().toString().trim();
				if(TextUtils.isEmpty(name)||TextUtils.isEmpty(phone)||TextUtils.isEmpty(detail)||TextUtils.isEmpty(street)){
					Toast.makeText(NewAddressActivity.this, "...不能为空", Toast.LENGTH_SHORT).show();
				}else{
					address.setName(name);
					address.setPhone(phone);
					address.setStreet(et_street.getText().toString().trim());
					//address.setMail(et_mail.getText().toString().trim());
					address.setDetail(detail);
					Datas.adderssList.add(address);
					new AddressTask().execute("");
					NewAddressActivity.this.finish();
				}
				break;
			case R.id.iv_top_back:
				NewAddressActivity.this.finish();
				break;
			default:
				break;
		}
	}
	//
	private final String SUCCESS="update_success";
	private final String FAIL="update_fail";
	private class AddressTask extends AsyncTask<String, Integer, String>{
		ProgressDialog progressDialog = new ProgressDialog(NewAddressActivity.this);
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String HttpArg;
			String result=FAIL;
			HttpArg="?sql=INSERT INTO dizhi (dz, yhID) VALUES ('"+address.getCity()+address.getStreet()+address.getDetail()+"','"+Datas.currentUser.getUserName()+"')";
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
			return result;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("正在处理···");
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progressDialog.dismiss();
			if(result.equals(SUCCESS)){
				Toast.makeText(NewAddressActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(NewAddressActivity.this, "抱歉，出了点小问题！", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
}
