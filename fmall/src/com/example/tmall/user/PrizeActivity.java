package com.example.tmall.user;

import java.util.Random;

import com.example.tmall.R;
import com.example.tmall.R.id;
import com.example.tmall.R.layout;
import com.example.tmall.utils.DensityUtil;
import com.example.tmall.view.Circleview;

import android.app.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class PrizeActivity extends Activity {
	/** Called when the activity is first created. */
	private TextView tv_back;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prize);
		FrameLayout layout = (FrameLayout) findViewById(R.id.Lottery);
		int screnWidth = getWindowManager().getDefaultDisplay().getWidth();
		/**
		 * 添加随机数，制定奖项数量为上限，一般抽奖中什么都是服务器返回的，可以在请求服务器接口成功在制定转盘转到那个奖项
		 */
		final Random random = new Random();
		final Circleview claert = new Circleview(this, screnWidth);
		layout.addView(claert, new LayoutParams(LayoutParams.FILL_PARENT,
				DensityUtil.dip2px(this, 300)));
		tv_back=(TextView) findViewById(R.id.iv_top_back);
		tv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PrizeActivity.this.finish();
			}
		});

		findViewById(R.id.begin_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int place = random.nextInt(7);
				claert.setStopPlace(place);
				claert.setStopRoter(false);
				
			}
		});
		findViewById(R.id.end_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				claert.setStopRoter(true);
			}
		});
	}
}