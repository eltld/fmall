package com.ngu.meishishuo.activity;

import com.ngu.meishishuo.R;
import com.ngu.meishishuo.bean.Comment;
import com.ngu.meishishuo.bean.Topic;
import com.ngu.meishishuo.utils.MeiShiDao;
import com.ngu.meishishuo.utils.SettingsUtil;
import com.ngu.meishishuo.utils.TimeUtil;
import com.ngu.meishishuo.utils.UserInfoUtil;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author zhoufeng06@qq.com
 * @time 2016年6月2日-下午12:33:24
 * 新建
 */
public class AddTopicActivity extends Activity {
	private ActionBar actionBar;
	private EditText edittext;
	private MeiShiDao dao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addtopic);
		initView();
	}
	private void initView(){
		dao=new MeiShiDao(AddTopicActivity.this);
		actionBar=getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		//不显示图标
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setTitle("说说");
		edittext=(EditText) findViewById(R.id.etMood);
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
	 		//未登录则不能评论
			if(!SettingsUtil.get(AddTopicActivity.this, SettingsUtil.IS_LOGIN)){
				edittext.setText("请登录后发表说说");
			}else{
			String content =edittext.getText().toString();
			if(!TextUtils.isEmpty(content)){
				Topic topic=new Topic();
				topic.setName(UserInfoUtil.getUserInfo(AddTopicActivity.this).get(UserInfoUtil.USERNAME));
				topic.setTime(TimeUtil.getCurrentTime());
				topic.setContent(content);
				dao.insertToTopic(topic);
				edittext.setText("");
				Toast.makeText(AddTopicActivity.this, "发表成功!", Toast.LENGTH_SHORT).show();
				AddTopicActivity.this.finish();
			}else{
				Toast.makeText(AddTopicActivity.this, "输入内容不能为空！", Toast.LENGTH_SHORT).show();
			}
			}
	 		break;
	 		
        }
		return super.onOptionsItemSelected(item);
	}
	
}
