package com.ngu.meishishuo.activity;

import com.ngu.meishishuo.R;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author zhoufeng06@qq.com
 * @time 2016年5月27日-下午2:49:59
 * 意见反馈
 */
public class FeedbackActivity extends Activity {
	private ActionBar actionBar;
	private Button btn_ok;//提交按钮
	private EditText et_message;//反馈信息
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		initView();
	}
	//初始化界面
	private void initView(){
		actionBar=getActionBar();
		//显示返回
		actionBar.setDisplayHomeAsUpEnabled(true);
		//不显示图标
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setTitle("意见反馈");
		btn_ok=(Button) findViewById(R.id.feedback_btn_ok);
		et_message=(EditText) findViewById(R.id.feedback_et_message);
		btn_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//
				if(TextUtils.isEmpty(et_message.getText().toString().trim())){
					Toast.makeText(FeedbackActivity.this, "请输入反馈意见",Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(FeedbackActivity.this, "感谢您的反馈！",Toast.LENGTH_SHORT).show();
					finish();
				}
			}
		});
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// 
		switch (item.getItemId()) {
	 	case android.R.id.home://点击左上角图标返回
	 		finish();
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//创建选项菜单 
	
		return super.onCreateOptionsMenu(menu);
	
	}
	
}
