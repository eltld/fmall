package com.example.tmall.classify;

import java.io.File;
import java.io.IOException;

import com.example.tmall.Datas;
import com.example.tmall.OrderActivity;
import com.example.tmall.R;
import com.example.tmall.bean.Product;
import com.example.tmall.constant.HttpUrl;
import com.example.tmall.view.DetailPopupWindow;
import com.example.tmall.view.DetailPopupWindow.OnWindowClickListener;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends Activity implements OnClickListener,OnWindowClickListener{
	private DetailPopupWindow popupWindow;
	private TextView Back;//返回
	private TextView buyNow;//立即购买
	private TextView putToCart;//加入购物车
	private TextView share;//分享
	private LinearLayout comment;//评论
	//商品信息
	private TextView tv_name,tv_msg,tv_price,tv_stock,tv_old;
	private ImageView iv_img;
	private Product product;
	private String img;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_activity);
		initView();
	}
	
	private void initView(){
		Intent intent=getIntent();
		product=(Product) intent.getSerializableExtra("product");
		
		popupWindow=new DetailPopupWindow(this,product);
		popupWindow.setOnWindowClickListener(this);
		
		Back=(TextView) findViewById(R.id.iv_top_back);
		Back.setOnClickListener(this);
		buyNow=(TextView) findViewById(R.id.detail_tv_buynow);
		buyNow.setOnClickListener(this);
		putToCart=(TextView) findViewById(R.id.detail_tv_puttocart);
		putToCart.setOnClickListener(this);
		share=(TextView) findViewById(R.id.tv_detail_share);
		share.setOnClickListener(this);
		//
		
		String name=product.getProName();
		String msg=product.getProMsg();
		String price=String.valueOf(product.getProPrice());
		String stock=String.valueOf(product.getProStock());
		img=product.getProImg();
		
		tv_name=(TextView) findViewById(R.id.detail_tv_name);
		tv_name.setText(name);
		tv_msg=(TextView) findViewById(R.id.detail_tv_message);
		tv_msg.setText(msg);
		tv_price=(TextView) findViewById(R.id.detail_tv_price);
		tv_price.setText("￥"+price);
		tv_stock=(TextView) findViewById(R.id.detail_tv_stock);
		tv_stock.setText("库存："+stock);
		iv_img=(ImageView) findViewById(R.id.detail_iv_img);
		Picasso.with(DetailActivity.this).load(HttpUrl._HTTP+img).into(iv_img);
		iv_img.setOnClickListener(this);
		tv_old=(TextView) findViewById(R.id.detail_tv_old);
		//中划线
		tv_old.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
		comment=(LinearLayout) findViewById(R.id.detail_ll_comment);
		comment.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		
			case R.id.detail_iv_img:
				Intent intent=new Intent(DetailActivity.this,BigPictureActivity.class);
				intent.putExtra("imageUrl", img);
				startActivity(intent);
				break;
			case R.id.detail_tv_puttocart:
				if(Datas.cartProducts.contains(product)){
					Toast.makeText(DetailActivity.this, "已经加入购物车了！", Toast.LENGTH_SHORT).show();
				}else{
					Datas.cartProducts.add(product);
					Toast.makeText(DetailActivity.this, "成功加入购物车！", Toast.LENGTH_SHORT).show();
				}
				
				break;
			case R.id.detail_tv_buynow:
				if(Datas.isLogin){
					popupWindow.showAsDropDown(v);
				}else{
					Toast.makeText(DetailActivity.this, "请登录后再进行操作！", Toast.LENGTH_SHORT).show();
				}
				
	
				break;
			case R.id.iv_top_back:
				finish();
				break;
			case R.id.tv_detail_share:
				shareMessag("分享", "购物",product.getProName(), "");
				break;
			case R.id.detail_ll_comment:
				Intent intent1=new Intent(DetailActivity.this,CommentActivity.class);
				startActivity(intent1);
				break;
		}
	}
	
	
	/** 
     * 分享功能 
     *  
     * @param context 
     *            上下文 
     * @param activityTitle 
     *            Activity的名字 
    * @param msgTitle 
     *            消息标题 
     * @param msgText 
    *            消息内容 
     * @param imgPath 
     *            图片路径，不分享图片则传null 
     */  
    public void shareMessag(String activityTitle, String msgTitle, String msgText,  
            String imgPath) {  
        Intent intent = new Intent(Intent.ACTION_SEND);  
        if (imgPath == null || imgPath.equals("")) {  
            intent.setType("text/plain"); // 纯文本  
        } else {  
            File f = new File(imgPath);  
            if (f != null && f.exists() && f.isFile()) {  
                intent.setType("image/jpg");  
              Uri u = Uri.fromFile(f);  
                intent.putExtra(Intent.EXTRA_STREAM, u);  
            }  
        }  
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);  
        intent.putExtra(Intent.EXTRA_TEXT, msgText);  
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
        startActivity(Intent.createChooser(intent, activityTitle));  
    }

	@Override
	public void onClickOK() {
		//底部弹窗
		int count=product.getProCount();
		double price=product.getProPrice()*count;
		Datas.ensureOrderProducts.add(product);
		Intent intent=new Intent(DetailActivity.this,OrderActivity.class);
		intent.putExtra(OrderActivity.TOTALPRICE, ""+price);
		startActivity(intent);
	}  
}
