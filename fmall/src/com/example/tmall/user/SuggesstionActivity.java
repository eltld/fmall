package com.example.tmall.user;

import com.example.tmall.R;
import com.example.tmall.R.id;
import com.example.tmall.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SuggesstionActivity extends Activity implements OnClickListener{
	private TextView Back;//·µ»Ø
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.suggestion);
		Back=(TextView) findViewById(R.id.iv_top_back);
		Back.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_top_back:
			SuggesstionActivity.this.finish();
			break;

		default:
			break;
		}
	}
}
