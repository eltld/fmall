package com.ngu.meishishuo.activity;

import com.ngu.meishishuo.R;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * @author zhoufeng06@qq.com
 * @time 2016年4月27日-下午9:47:48
 */
public class AboutActivity extends Activity {
	private ActionBar actionBar;
	private RelativeLayout rl_team,rl_version,rl_data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		actionBar=getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		//显示图标
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setTitle("关于");
		rl_team=(RelativeLayout) findViewById(R.id.about_rl_team);
		rl_version=(RelativeLayout) findViewById(R.id.about_rl_version);
		rl_data=(RelativeLayout) findViewById(R.id.about_rl_data);
		rl_team.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 
				Toast.makeText(AboutActivity.this, "zhoufeng_zhangjiaqi_qiuhuanchao", Toast.LENGTH_SHORT).show();
			}
		});
		rl_data.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 
				Toast.makeText(AboutActivity.this, "感谢tngou.net！", Toast.LENGTH_SHORT).show();
			}
		});
		rl_version.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 
				Toast.makeText(AboutActivity.this, "已经是最新版本了！", Toast.LENGTH_SHORT).show();
			}
		});
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
		//创建选项菜单 
		return super.onCreateOptionsMenu(menu);
	}
}
