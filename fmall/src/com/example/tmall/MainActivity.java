package com.example.tmall;

import org.afinal.simplecache.ACache;

import com.example.tmall.cart.CartFragment;
import com.example.tmall.classify.ClassifyFragment;
import com.example.tmall.home.HomeFragment;

import com.example.tmall.user.UserFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ����������ײ�Ŀ��Activity�����е�Fragment���������ڴ�Activity�����ڵ�
 * 
 */
public class MainActivity extends FragmentActivity implements OnClickListener  {

	/** ��ҳ */
	private HomeFragment homeFragment;
	private LinearLayout ll_home;
	private ImageView iv_home;
	private TextView tv_home;
	/** ���� */
	private ClassifyFragment classifyFragment;
	private LinearLayout ll_classify;
	private ImageView iv_classify;
	private TextView tv_classify;
	/** ���ﳵ */
	private CartFragment cartFragment;
	private LinearLayout ll_cart;
	private ImageView iv_cart;
	private TextView tv_cart;
	/** �ҵ� */
	private UserFragment userFragment;
	private LinearLayout ll_user;
	private ImageView iv_user;
	private TextView tv_user;
	
	private FragmentTransaction transaction;
	private ACache mCache;//���ݻ���
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		initView();
		setTabSelection(0);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//��������
		//mCache.
	}
	
	private void initView() {
		mCache = ACache.get(MainActivity.this);
		
		ll_home = (LinearLayout) findViewById(R.id.ll_home);
		ll_home.setOnClickListener(this);
		iv_home = (ImageView) findViewById(R.id.iv_home);
		tv_home = (TextView) findViewById(R.id.tv_home);
		
		ll_classify = (LinearLayout) findViewById(R.id.ll_classify);
		ll_classify.setOnClickListener(this);
		iv_classify = (ImageView) findViewById(R.id.iv_classify);
		tv_classify = (TextView) findViewById(R.id.tv_classify);
		
		ll_cart = (LinearLayout) findViewById(R.id.ll_cart);
		ll_cart.setOnClickListener(this);
		iv_cart = (ImageView) findViewById(R.id.iv_cart);
		tv_cart = (TextView) findViewById(R.id.tv_cart);
		
		ll_user = (LinearLayout) findViewById(R.id.ll_user);
		ll_user.setOnClickListener(this);
		iv_user = (ImageView) findViewById(R.id.iv_user);
		tv_user = (TextView) findViewById(R.id.tv_user);
		
	}

	

	/** ���ذ�ť�ļ��� */
	private long exitTime = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();                                
	            exitTime = System.currentTimeMillis();   
	        } else {
	            finish();
	            System.exit(0);
	        }
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.ll_home:
			// ѡ�е�1��tab
			setTabSelection(0);
			break;
		case R.id.ll_classify:
			// ѡ�е�2��tab
			setTabSelection(1);
			break;
		case R.id.ll_cart:
			// ѡ�е�3��tab
			setTabSelection(2);
			break;
		case R.id.ll_user:
			//ѡ�е�4��tab
			setTabSelection(3);
			break;
		default:
			break;
		}
		
	}

	private void setTabSelection(int index) {
		// ÿ��ѡ��֮ǰ��������ϴε�ѡ��״̬
		clearSelection();
		// ����һ��Fragment����
		transaction = getSupportFragmentManager().beginTransaction();
		transaction.setCustomAnimations(R.anim.cu_push_right_in, R.anim.cu_push_left_out);
		// �����ص����е�Fragment���Է�ֹ�ж��Fragment��ʾ�ڽ����ϵ����
		hideFragments(transaction);
		switch (index) {
		case 0:
			
			iv_home.setImageResource(R.drawable.home_fill);
			tv_home.setTextColor(Color.parseColor("#e0620d"));
			if (homeFragment == null) {
				
				homeFragment = new HomeFragment();
				transaction.add(R.id.show_layout, homeFragment);
			} else {
				
				transaction.show(homeFragment);
			}
			break;
		case 1:
			// ������˴�����tabʱ���ı�ؼ���ͼƬ��������ɫ
			iv_classify.setImageResource(R.drawable.classify_fill);
			tv_classify.setTextColor(Color.parseColor("#e0620d"));
			if (classifyFragment == null) {
				
				classifyFragment = new ClassifyFragment();
				transaction.add(R.id.show_layout, classifyFragment);
			} else {
				
				transaction.show(classifyFragment);
			}
			break;
		case 2:
			// ������˴��ջ�tabʱ���ı�ؼ���ͼƬ��������ɫ
			iv_cart.setImageResource(R.drawable.cart_fill);
			tv_cart.setTextColor(Color.parseColor("#e0620d"));
			if (cartFragment == null) {
				
				cartFragment = new CartFragment();
				transaction.add(R.id.show_layout, cartFragment);
			} else {
				
				transaction.show(cartFragment);
			}
			break;
		case 3:
		
			// ������˴�����tabʱ���ı�ؼ���ͼƬ��������ɫ
			iv_user.setImageResource(R.drawable.people_fill);
			tv_user.setTextColor(Color.parseColor("#e0620d"));
			if (userFragment == null) {
				
				userFragment = new UserFragment();
				transaction.add(R.id.show_layout, userFragment);
			} else {
				
				transaction.show(userFragment);
			}
			break;
		}
		transaction.commit();
	}

	private void clearSelection() {
		iv_home.setImageResource(R.drawable.home);
		tv_home.setTextColor(Color.parseColor("#9e9e9e"));
		iv_cart.setImageResource(R.drawable.cart);
		tv_cart.setTextColor(Color.parseColor("#9e9e9e"));
		iv_classify.setImageResource(R.drawable.classify);
		tv_classify.setTextColor(Color.parseColor("#9e9e9e"));
		iv_user.setImageResource(R.drawable.people);
		tv_user.setTextColor(Color.parseColor("#9e9e9e"));
	}

	private void hideFragments(FragmentTransaction transaction) {
		if (homeFragment != null) {
			transaction.hide(homeFragment);
		}
		if (classifyFragment != null) {
			transaction.hide(classifyFragment);
		}
		if (cartFragment != null) {
			transaction.hide(cartFragment);
		}
		if (userFragment != null) {
			transaction.hide(userFragment);
		}
	}
}
