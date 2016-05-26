package com.ngu.meishishuo.activity;

import com.ngu.meishishuo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		 /** 
	     * 延迟多少秒进入主界面 
	     */  
	        new Handler().postDelayed(new Runnable() {  
	  
	            @Override  
	            public void run() {  
	                Intent intent = new Intent(WelcomeActivity.this,  
	                       MainActivity.class);  
	                startActivity(intent);  
	                WelcomeActivity.this.finish();  
	            }  
	        }, 3000);  
	    }  
}

