package com.ngu.meishishuo.activity;

import com.ngu.meishishuo.R;
import com.ngu.meishishuo.fragment.LoginFragment;
import com.ngu.meishishuo.fragment.UserInfoFragment;
import com.ngu.meishishuo.utils.SettingsUtil;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

public class LoginActivity extends FragmentActivity {
	private ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_container);
		initView();
	}
	private void initView(){
		actionBar=getActionBar();
		// 开启ActionBar上APP ICON的功能：点击打开和点击关闭drawer
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("登录");
		FragmentTransaction beginTransaction=getSupportFragmentManager().beginTransaction();
		if(SettingsUtil.get(LoginActivity.this, SettingsUtil.IS_LOGIN)){
			//已经登录，显示个人信息
			UserInfoFragment fragment=new UserInfoFragment();
			beginTransaction.replace(R.id.login_container, fragment).commit();
		}
		else{
			//没有登录，显示登录界面
			LoginFragment fragment=new LoginFragment();
			beginTransaction.replace(R.id.login_container, fragment).commit();
		}
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//创建选项菜单 
		return super.onCreateOptionsMenu(menu);
	}
}
