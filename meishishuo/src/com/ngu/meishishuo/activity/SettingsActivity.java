package com.ngu.meishishuo.activity;

import com.ngu.meishishuo.R;
import com.ngu.meishishuo.utils.SettingsUtil;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SettingsActivity extends Activity {
	private ActionBar actionBar;
	private ToggleButton toggleButton;
	private LinearLayout ll_cache,ll_about,ll_feedback;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		initView();
	}
	
	private void initView(){
		actionBar=getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		//不显示图标
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setTitle("设置");
		toggleButton=(ToggleButton) findViewById(R.id.setting_noimage_tg);
		ll_cache= (LinearLayout) findViewById(R.id.setting_cache_ll);
		ll_about= (LinearLayout) findViewById(R.id.setting_about_ll);
		ll_feedback=(LinearLayout) findViewById(R.id.setting_feedback_ll);
		//清除缓存
		ll_cache.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				// 
				Toast.makeText(SettingsActivity.this, "缓存已清除！", Toast.LENGTH_SHORT).show();
				
			}
		});
		//关于
		ll_about.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 
				Intent intent=new Intent(SettingsActivity.this,AboutActivity.class);
				startActivity(intent);
			}
		});
		//意见反馈
		ll_feedback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 
				Intent intent=new Intent(SettingsActivity.this,FeedbackActivity.class);
				startActivity(intent);
			}
		});
		toggleButton.setChecked(SettingsUtil.get(SettingsActivity.this, SettingsUtil.NO_IMAGE));
		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//
				if(isChecked){
					
					SettingsUtil.set(SettingsActivity.this, SettingsUtil.NO_IMAGE,true);
				}
				else{
					SettingsUtil.set(SettingsActivity.this, SettingsUtil.NO_IMAGE,false);
				}
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// 
		switch (item.getItemId()) {
	 	case android.R.id.home://点击左上角图标返回
	 		finish();
	 		break;
	 		
        }
		return super.onOptionsItemSelected(item);
	}

}
