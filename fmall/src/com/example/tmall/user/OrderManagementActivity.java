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
 * ��Ŀ����Activity�����е�Fragment��Ƕ�������
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
		// ��ʼ������Ԫ��
		initViews();
		// ��һ������ʱѡ�е�0��tab
		setTabSelection(2);
	}

	/**
	 * �������ȡ��ÿ����Ҫ�õ��Ŀؼ���ʵ���������������úñ�Ҫ�ĵ���¼���
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
			// ѡ�е�1��tab
			setTabSelection(0);
			break;
		case R.id.daifahuo_layout:
			// ѡ�е�2��tab
			setTabSelection(1);
			break;
		case R.id.daishouhuo_layout:
			// ѡ�е�3��tab
			setTabSelection(2);
			break;
		case R.id.daipingjia_layout:
			//ѡ�е�4��tab
			setTabSelection(3);
			break;
		default:
			break;
		}
	}

	/**
	 * ���ݴ����index����������ѡ�е�tabҳ��
	 * 
	 * @param index
	 *            ÿ��tabҳ��Ӧ���±ꡣ0��ʾ�����1��ʾ��������2��ʾ���ջ���3��ʾ�����ۡ�
	 */
	private void setTabSelection(int index) {
		// ÿ��ѡ��֮ǰ��������ϴε�ѡ��״̬
		clearSelection();
		// ����һ��Fragment����
		transaction = getSupportFragmentManager().beginTransaction();
				
		// �����ص����е�Fragment���Է�ֹ�ж��Fragment��ʾ�ڽ����ϵ����
		hideFragments(transaction);
		switch (index) {
		case 0:
			// ������˴�����tabʱ���ı�ؼ���ͼƬ��������ɫ
			daifukuanImage.setImageResource(R.drawable.user_daifukuan_selected);
			daifukuanText.setTextColor(Color.parseColor("#ff5000"));
			if (DaifukuanFragment == null) {
				// ���DaifukuanFragmentΪ�գ��򴴽�һ������ӵ�������
				DaifukuanFragment = new DaifukuanFragment();
				transaction.add(R.id.content, DaifukuanFragment);
			} else {
				// ���DaifukuanFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(DaifukuanFragment);
			}
			break;
		case 1:
			// ������˴�����tabʱ���ı�ؼ���ͼƬ��������ɫ
			daifahuoImage.setImageResource(R.drawable.user_daifahuo_selected);
			daifahuoText.setTextColor(Color.parseColor("#ff5000"));
			if (DaifahuoFragment == null) {
				// ���Ϊ�գ��򴴽�һ������ӵ�������
				DaifahuoFragment = new DaifahuoFrament();
				transaction.add(R.id.content, DaifahuoFragment);
			} else {
				// �����Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(DaifahuoFragment);
			}
			break;
		case 2:
			// ������˴��ջ�tabʱ���ı�ؼ���ͼƬ��������ɫ
			daishouhuoImage.setImageResource(R.drawable.user_daishouhuo_selected);
		    daishouhuoText.setTextColor(Color.parseColor("#ff5000"));
			if (DaishouhuoFragment == null) {
				// ���Ϊ�գ��򴴽�һ������ӵ�������
				DaishouhuoFragment = new DaishouhuoFragment();
				transaction.add(R.id.content, DaishouhuoFragment);
			} else {
				// �����Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(DaishouhuoFragment);
			}
			break;
		case 3:
		
			// ������˴�����tabʱ���ı�ؼ���ͼƬ��������ɫ
			daipingjiaImage.setImageResource(R.drawable.user_daipingjia_selected);
			daipingjiaText.setTextColor(Color.parseColor("#ff5000"));
			if (DaipingjiaFragment == null) {
				// ���Ϊ�գ��򴴽�һ������ӵ�������
				DaipingjiaFragment = new DaipingjiaFragment();
				transaction.add(R.id.content, DaipingjiaFragment);
			} else {
				// �����Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(DaipingjiaFragment);
			}
			break;
		}
		transaction.commit();
	}

	/**
	 * ��������е�ѡ��״̬��
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
	 * �����е�Fragment����Ϊ����״̬��
	 * 
	 * @param transaction
	 *            ���ڶ�Fragmentִ�в���������
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