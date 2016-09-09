package com.ngu.meishishuo.activity;

import com.ngu.meishishuo.R;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddMeiShiActivity extends Activity {
	private ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addmeishi);
		initView();
	}
	private void initView(){
		actionBar=getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		//不显示图标
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setTitle("上传菜谱");
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//创建选项菜单 
		MenuInflater inflater = getMenuInflater();  
		inflater.inflate(R.menu.menu_add, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// 
		switch (item.getItemId()) {
	 	case android.R.id.home://点击左上角图标返回
	 		finish();
	 		break;
	 	case R.id.menu_send://发送
	 		Toast.makeText(AddMeiShiActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
	 		AddMeiShiActivity.this.finish();
	 		break;
		}
		return super.onOptionsItemSelected(item);
	}
	
}

