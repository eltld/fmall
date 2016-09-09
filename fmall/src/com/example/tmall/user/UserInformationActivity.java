package com.example.tmall.user;

import com.example.tmall.Datas;
import com.example.tmall.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/*
 * 个人信息
 */
public class UserInformationActivity extends Activity implements OnClickListener{
	
	private TextView Back;//返回
	private Button btn_logout;
	private TextView tv_name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinformation_activity);
		Back=(TextView) findViewById(R.id.iv_top_back);
		Back.setOnClickListener(this);
		btn_logout=(Button) findViewById(R.id.userinfo_button_logout);
		btn_logout.setOnClickListener(this);
		tv_name=(TextView) findViewById(R.id.userinfo_tv_name);
		tv_name.setText(Datas.currentUser.getUserName());
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_top_back:
			UserInformationActivity.this.finish();
			break;
		case R.id.userinfo_button_logout:
			Datas.isLogin=false;
			Datas.currentUser.setUserName("请登录");
			UserInformationActivity.this.finish();
			break;

		default:
			break;
		}
	}

}
