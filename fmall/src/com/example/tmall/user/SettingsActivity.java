package com.example.tmall.user;

import com.example.tmall.Datas;
import com.example.tmall.R;
import com.example.tmall.R.id;
import com.example.tmall.R.layout;
import com.example.tmall.utils.DataCleanManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends Activity implements OnClickListener{
	private TextView Back;//返回
	private LinearLayout address,suggesstion,about,cache,user_information;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);
		initView();
	}
	private void initView(){
		Back=(TextView) findViewById(R.id.iv_top_back);
		Back.setOnClickListener(this);
		user_information=(LinearLayout) findViewById(R.id.settings_user_information);
		user_information.setOnClickListener(this);
		address=(LinearLayout) findViewById(R.id.settings_address);
		address.setOnClickListener(this);
		cache=(LinearLayout) findViewById(R.id.settings_cache);
		cache.setOnClickListener(this);
		suggesstion=(LinearLayout) findViewById(R.id.settings_suggesstion);
		suggesstion.setOnClickListener(this);
		about=(LinearLayout) findViewById(R.id.settings_about);
		about.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.iv_top_back:
			finish();
			break;
		case R.id.settings_user_information:
			if(Datas.isLogin){
				Intent intent0=new Intent (SettingsActivity.this,UserInformationActivity.class);
				startActivity(intent0);
			}else{
				Toast.makeText(SettingsActivity.this, "请登录后再进行操作！", Toast.LENGTH_SHORT).show();
			}
			
			break;
		case R.id.settings_address:
			if(Datas.isLogin){
				Intent intent1=new Intent (SettingsActivity.this,AddressActivity.class);
				startActivity(intent1);
			}else{
				Toast.makeText(SettingsActivity.this, "请登录后再进行操作！", Toast.LENGTH_SHORT).show();
			}
			
			break;
		case R.id.settings_cache:
			DataCleanManager.clearAllCache(SettingsActivity.this);
			Toast.makeText(SettingsActivity.this, "缓存已清除", Toast.LENGTH_SHORT).show();
			break;
		case R.id.settings_suggesstion:
			Intent intent2=new Intent (SettingsActivity.this,SuggesstionActivity.class);
			startActivity(intent2);
			break;
		case R.id.settings_about:
			Intent intent3=new Intent (SettingsActivity.this,AboutActivity.class);
			startActivity(intent3);
			break;
	}
	}
}
