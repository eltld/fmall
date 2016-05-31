package com.ngu.meishishuo.activity;

import com.ngu.meishishuo.R;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

public class MessageActivity extends Activity {
	private TabHost tabHost;
	private ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		initView();
	}
	private void initView(){
		actionBar=getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		//不显示图标
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setTitle("我的消息");
		 tabHost = (TabHost) this.findViewById(R.id.message_TabHost);  
         tabHost.setup();  
           
         tabHost.addTab(tabHost.newTabSpec("tab_1")  
                 .setContent(R.id.LinearLayout1).setIndicator("评论"));  
 
         tabHost.addTab(tabHost.newTabSpec("tab_2")  
                 .setContent(R.id.LinearLayout2).setIndicator("通知"));  
         tabHost.setCurrentTab(1);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
	 	case android.R.id.home://点击左上角图标返回
	 		finish();
	 		break;
	 		
        }
		return super.onOptionsItemSelected(item);
	}
}
