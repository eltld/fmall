package com.example.tmall;

import java.util.ArrayList;

import com.example.tmall.adapter.BasePagerAdapter;
import com.example.tmall.utils.SharedConfig;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

//��һ�����е�����ҳ����
public class WelcomeActivity extends Activity implements OnPageChangeListener,OnClickListener {
	private Context context;
	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;
	private LinearLayout indicatorLayout;
	private ArrayList<View> views;
	private ImageView[] indicators = null;
	private int[] images;
	private ImageView lastImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		context = this;
		// ���������ݷ�ʽ
		//new CreateShut(this);
		// ��������ͼƬ
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!  ������������ͼƬ ָʾ����page�Զ����
		images = new int[] { R.drawable.welcome_01, R.drawable.welcome_02,
				R.drawable.welcome_03 };
		initView();
	}

	// ��ʼ����ͼ
	private void initView() {
		// ʵ������ͼ�ؼ�
		viewPager = (ViewPager) findViewById(R.id.viewpage);
		indicatorLayout = (LinearLayout) findViewById(R.id.indicator);
		views = new ArrayList<View>();
		indicators = new ImageView[images.length]; // ����ָʾ�������С
		for (int i = 0; i < images.length; i++) {
			// ѭ������ͼƬ
			ImageView imageView = new ImageView(context);
			imageView.setBackgroundResource(images[i]);
			views.add(imageView);
			// ѭ������ָʾ��
			indicators[i] = new ImageView(context);
			indicators[i].setBackgroundResource(R.drawable.indicators_default);
			if (i == 0) {
				indicators[i].setBackgroundResource(R.drawable.indicators_now);
			}
			if(i==2){
				//���һ��
				lastImageView=imageView;
				
			}
			indicatorLayout.addView(indicators[i]);
		}
		pagerAdapter = new BasePagerAdapter(views);
		viewPager.setAdapter(pagerAdapter); // ����������
		viewPager.setOnPageChangeListener(this);
		lastImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences shared = new SharedConfig(WelcomeActivity.this).GetConfig();
				Editor editor = shared.edit();
				editor.putBoolean("First", false);
				editor.commit();
				startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
				overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
				WelcomeActivity.this.finish();
			}
		});
	}
	//��ť�ĵ���¼�
	@Override
	public void onClick(View v) {
		
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	// ����viewpage
	@Override
	public void onPageSelected(int arg0) {
		// ��ʾ���һ��ͼƬʱ��ʾ��ť
		/*if (arg0 == indicators.length - 1) {
			startButton.setVisibility(View.VISIBLE);
		} else {
			startButton.setVisibility(View.INVISIBLE);
		}*/
		// ����ָʾ��ͼƬ
		for (int i = 0; i < indicators.length; i++) {
			indicators[arg0].setBackgroundResource(R.drawable.indicators_now);
			if (arg0 != i) {
				indicators[i]
						.setBackgroundResource(R.drawable.indicators_default);
			}
		}
	}
}
