package com.example.tmall.user;

import com.example.tmall.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 项目的主Activity，所有的Fragment都嵌入在这里。
 */
public class OrderManagementActivity extends FragmentActivity implements OnClickListener {

	
	private DaifukuanFragment DaifukuanFragment;
	private DaifahuoFrament DaifahuoFragment;
	private DaishouhuoFragment DaishouhuoFragment;
	private DaipingjiaFragment DaipingjiaFragment;
	private View daifukuanLayout;
	private View daifahuoLayout;
	private View daishouhuoLayout;
	private View daipingjiaLayout;
	private ImageView daifukuanImage;
	private ImageView daifahuoImage;
	private ImageView daishouhuoImage;
	private ImageView daipingjiaImage;
	private TextView daifukuanText;
	private TextView daifahuoText;
	private TextView daishouhuoText;
	private TextView daipingjiaText;
	private FragmentTransaction transaction;
	private TextView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ordermanagement_activity);
		// 初始化布局元素
		initViews();
		// 第一次启动时选中第0个tab
		setTabSelection(2);
	}

	/**
	 * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
	 */
	private void initViews() {
		back=(TextView) findViewById(R.id.iv_top_back);
		back.setOnClickListener(this);
		
		daifukuanLayout = findViewById(R.id.daifukuan_layout);
		daifahuoLayout = findViewById(R.id.daifahuo_layout);
		daishouhuoLayout = findViewById(R.id.daishouhuo_layout);
		daipingjiaLayout = findViewById(R.id.daipingjia_layout);
		
		daifukuanImage = (ImageView) findViewById(R.id.daifukuan_image);
		daifahuoImage = (ImageView) findViewById(R.id.daifahuo_image);
		daishouhuoImage = (ImageView) findViewById(R.id.daishouhuo_image);
		daipingjiaImage = (ImageView) findViewById(R.id.daipingjia_image);
		
		daifukuanText = (TextView) findViewById(R.id.daifukuan_text);
		daifahuoText = (TextView) findViewById(R.id.daifahuo_text);
		daishouhuoText = (TextView) findViewById(R.id.daishouhuo_text);
		daipingjiaText = (TextView) findViewById(R.id.daipingjia_text);
		
		daifukuanLayout.setOnClickListener(this);
		daifahuoLayout.setOnClickListener(this);
		daishouhuoLayout.setOnClickListener(this);
		daipingjiaLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_top_back:
			OrderManagementActivity.this.finish();
			break;
		case R.id.daifukuan_layout:
			// 选中第1个tab
			setTabSelection(0);
			break;
		case R.id.daifahuo_layout:
			// 选中第2个tab
			setTabSelection(1);
			break;
		case R.id.daishouhuo_layout:
			// 选中第3个tab
			setTabSelection(2);
			break;
		case R.id.daipingjia_layout:
			//选中第4个tab
			setTabSelection(3);
			break;
		default:
			break;
		}
	}

	/**
	 * 根据传入的index参数来设置选中的tab页。
	 * 
	 * @param index
	 *            每个tab页对应的下标。0表示代付款，1表示代发货，2表示待收货，3表示待评价。
	 */
	private void setTabSelection(int index) {
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		transaction = getSupportFragmentManager().beginTransaction();
				
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
		case 0:
			// 当点击了代付款tab时，改变控件的图片和文字颜色
			daifukuanImage.setImageResource(R.drawable.user_daifukuan_selected);
			daifukuanText.setTextColor(Color.parseColor("#ff5000"));
			if (DaifukuanFragment == null) {
				// 如果DaifukuanFragment为空，则创建一个并添加到界面上
				DaifukuanFragment = new DaifukuanFragment();
				transaction.add(R.id.content, DaifukuanFragment);
			} else {
				// 如果DaifukuanFragment不为空，则直接将它显示出来
				transaction.show(DaifukuanFragment);
			}
			break;
		case 1:
			// 当点击了代发货tab时，改变控件的图片和文字颜色
			daifahuoImage.setImageResource(R.drawable.user_daifahuo_selected);
			daifahuoText.setTextColor(Color.parseColor("#ff5000"));
			if (DaifahuoFragment == null) {
				// 如果为空，则创建一个并添加到界面上
				DaifahuoFragment = new DaifahuoFrament();
				transaction.add(R.id.content, DaifahuoFragment);
			} else {
				// 如果不为空，则直接将它显示出来
				transaction.show(DaifahuoFragment);
			}
			break;
		case 2:
			// 当点击了待收货tab时，改变控件的图片和文字颜色
			daishouhuoImage.setImageResource(R.drawable.user_daishouhuo_selected);
		    daishouhuoText.setTextColor(Color.parseColor("#ff5000"));
			if (DaishouhuoFragment == null) {
				// 如果为空，则创建一个并添加到界面上
				DaishouhuoFragment = new DaishouhuoFragment();
				transaction.add(R.id.content, DaishouhuoFragment);
			} else {
				// 如果不为空，则直接将它显示出来
				transaction.show(DaishouhuoFragment);
			}
			break;
		case 3:
		
			// 当点击了待评价tab时，改变控件的图片和文字颜色
			daipingjiaImage.setImageResource(R.drawable.user_daipingjia_selected);
			daipingjiaText.setTextColor(Color.parseColor("#ff5000"));
			if (DaipingjiaFragment == null) {
				// 如果为空，则创建一个并添加到界面上
				DaipingjiaFragment = new DaipingjiaFragment();
				transaction.add(R.id.content, DaipingjiaFragment);
			} else {
				// 如果不为空，则直接将它显示出来
				transaction.show(DaipingjiaFragment);
			}
			break;
		}
		transaction.commit();
	}

	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {
		daifukuanImage.setImageResource(R.drawable.user_daifukuan);
		daifukuanText.setTextColor(Color.parseColor("#82858b"));
		daifahuoImage.setImageResource(R.drawable.user_daifahuo);
		daifahuoText.setTextColor(Color.parseColor("#82858b"));
		daishouhuoImage.setImageResource(R.drawable.user_daishouhuo);
		daishouhuoText.setTextColor(Color.parseColor("#82858b"));
		daipingjiaImage.setImageResource(R.drawable.user_daipingjia);
		daipingjiaText.setTextColor(Color.parseColor("#82858b"));
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (DaifukuanFragment != null) {
			transaction.hide(DaifukuanFragment);
		}
		if (DaifahuoFragment != null) {
			transaction.hide(DaifahuoFragment);
		}
		if (DaishouhuoFragment != null) {
			transaction.hide(DaishouhuoFragment);
		}
		if (DaipingjiaFragment != null) {
			transaction.hide(DaipingjiaFragment);
		}
	}
}