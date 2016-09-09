package com.example.tmall.view;

import com.example.tmall.R;
import com.example.tmall.utils.DensityUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.widget.ImageView;

public class Circleview extends ImageView implements Runnable {

	private Bitmap mHourBitmap;

	private boolean bInitComplete = false;
	private boolean stopRoter = true;
	float Angel = 0.0f;
	Matrix matx = new Matrix();
	
	/**
	 * �н����ּ������  maxAngel=ת�����н��ĽǶ�
	 */
	float maxAngel = 0.0f;
	
	/**
	 * ��Ļ�Ŀ��
	 */
	int screnWidth = 0;
	
	/**
	 * ��ʼ�齱�������
	 * @param context
	 * @param width ��Ļ���
	 */
	public Circleview(Context context,int width) {
		super(context);
		this.screnWidth = width;
		init();
		new Thread(this).start();
	}

	public void init() {

		mHourBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.share_lottery_pointer);
		bInitComplete = true;

	}

	public void setRotate_degree(float degree) {
		Angel = degree;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		matx.reset();
		canvas.drawColor(Color.TRANSPARENT);

		if (!bInitComplete) {
			return;
		}
		Paint localPaint = new Paint();
		// ����ȡ�����Ч��
		localPaint.setAntiAlias(true);
		localPaint.setFilterBitmap(true);
		/**
		 * ��ʼ�м�ָ��
		 */
		matx.setTranslate(this.screnWidth/2-mHourBitmap.getWidth()/2, DensityUtil.dip2px(getContext(), 300)/2-mHourBitmap.getHeight()+DensityUtil.dip2px(getContext(), 20));
		/**
		 * �����Ƶ���ת
		 */
		matx.preRotate(Angel, mHourBitmap.getWidth() / 2,mHourBitmap.getHeight() * 4 / 5);

		canvas.drawBitmap(mHourBitmap, matx, localPaint);
	}

	public void run() {
		try {
			while (true) {
				if (!isStopRoter()) {
					if(maxAngel!=0&&Angel>=maxAngel)
					{
						setStopRoter(true);
						maxAngel = 0.0f;
					}
					else
					{
						if(maxAngel-Angel<360)
							setRotate_degree(Angel+=10);
						else
							setRotate_degree(Angel+=15);
						this.postInvalidate();
						Thread.sleep(50);
					}
				}
			}

		} catch (InterruptedException e) {

			e.printStackTrace();
		}

	}
	
	/**
	 * ��ȡ��ǰ�ĽǶȣ�������ֹͣ�Ƕ�
	 * @param palce λ��
	 * @return
	 */

	public void setStopPlace(int place){
		getRoterByPlace(place);
	}
	
	/**
	 * ˳ʱ����ת
	 * 1 = 330-30
	 * 2 = 30-90
	 * 3 = 90-150
	 * 4 = 150-210
	 * 5 = 210-270
	 * 6 = 270-330
	 * @param place
	 * @return
	 */
	void getRoterByPlace(int place){
		float roter = getRoteCenter(place);
		float currentRoter = getCurrentRoter();
		
		//�����ǰ�ĽǶ�С��λ�õĽǶȣ����ʾ��Ҫ��ת���ٽǶ�
		float difRoter = currentRoter - roter;
		//�̶���Ȧ360*3�����ڼ��ϵ�ǰ�ĽǶȲ�
		maxAngel = Angel + 360*2 + 360-difRoter;
	}
	
	/**
	 * �õ�����λ�õĽǶ� -ת��360�� ���ݽ���ȡ���������ƽ��ֵ��������ָ������������м��
	 * @param place
	 * @return
	 */
	 float getRoteCenter(int place){
		float roter = 0.0f;
		switch (place) {
			case 1:
				roter = 0;
				break;
			case 2:
				roter = 60/2 + 30;
				break;
			case 3:
				roter = 60/2 + 90;
				break;
			case 4:
				roter = 60/2 + 150;
				break;
			case 5:
				roter = 60/2 + 210;
				break;
			case 6:
				roter = 60/2 + 270;
				break;
			default:
				break;
		}
		return roter;
	 }
	
	/**
	 * �õ�ת����ʵ�ʽǶ�--����Ƕ�ֵ
	 * @return
	 */
	 float getCurrentRoter(){
		 int current = (int) Angel/360;
			if(0==current)
				return Angel;
			float roter = Angel - 360*current;
			return roter;
	}
	
	public boolean isStopRoter() {
		return stopRoter;
	}

	public void setStopRoter(boolean stopRoter) {
		this.stopRoter = stopRoter;
	}
}