package com.example.tmall.user;

import com.example.tmall.R;
import com.example.tmall.R.id;
import com.example.tmall.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/*
 * 关于我们
 */
public class AboutActivity extends Activity implements OnClickListener{
	private TextView Back;//返回
@Override
protected void onCreate(Bundle savedInstanceState) {

	super.onCreate(savedInstanceState);
	setContentView(R.layout.about_me);
	Back=(TextView) findViewById(R.id.iv_top_back);
	Back.setOnClickListener(this);
}
@Override
public void onClick(View v) {
	
	switch (v.getId()) {
	case R.id.iv_top_back:
		AboutActivity.this.finish();
		break;

	default:
		break;
	}
}
}
