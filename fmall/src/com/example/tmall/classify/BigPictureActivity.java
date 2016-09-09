package com.example.tmall.classify;

import com.example.tmall.R;
import com.example.tmall.constant.HttpUrl;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.os.Bundle;
import uk.co.senab.photoview.PhotoView;

public class BigPictureActivity extends Activity{
	private PhotoView pv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bigpicture_activity);
		pv=(PhotoView) findViewById(R.id.detail_photoview);
		String imageUrl=getIntent().getStringExtra("imageUrl");
		Picasso.with(BigPictureActivity.this).load(HttpUrl._HTTP+imageUrl).into(pv);
	}
}
