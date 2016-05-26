package com.ngu.meishishuo.activity;

import com.ngu.meishishuo.R;
import com.ngu.meishishuo.fragment.ClassifyFragment;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author zhoufeng06@qq.com
 * @time 2016年4月23日-上午10:41:38
 * 菜谱分类列表
 */
public class ClassifyActivity extends FragmentActivity{
	
	private ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_container);
		initView();
		
	}
	
	public void initView(){
		
		actionBar=getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("健康食谱分类");
		FragmentTransaction beginTransaction=getSupportFragmentManager().beginTransaction();
		ClassifyFragment fragment=new ClassifyFragment();
		beginTransaction.replace(R.id.login_container, fragment).commit();
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
		@Override
	public boolean onCreateOptionsMenu(Menu menu) {
			//
		return super.onCreateOptionsMenu(menu);
	}	
	
	@Override
	protected void onDestroy() {
		// 
		super.onDestroy();
		
	}	
	
	
}
