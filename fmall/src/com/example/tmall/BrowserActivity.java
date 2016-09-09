package com.example.tmall;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class BrowserActivity extends Activity implements OnClickListener{

	private WebView web;
	private ProgressBar loadProgress;
	private String homeStr = "http://www.baidu.com";
	private TextView tv_title;
	private TextView tv_back;
	
	//-------------------------------------------
	public static final String BROWSERURL="_url";
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.browser_activity);
        Intent intent = getIntent();
        String url=intent.getStringExtra(BROWSERURL);
        if(url!=null&&!url.equals("")){
        	homeStr=url;
        }
        init();
    }
	
    //------------------------------------------
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	 if (web != null) {
    	    	try {
    	    		web.destroy();
    	    	} catch (Exception e) {
    	    		e.printStackTrace();
    	    	}
    	    }
    }
   
    
	//-------------------------------------------
	/**
	 * 基本数据初始化
	 */
	private void init(){
		tv_title=(TextView) findViewById(R.id.tv_top_title);
		tv_back=(TextView) findViewById(R.id.iv_top_back);
		tv_back.setOnClickListener(this);
		
		web = (WebView)findViewById(R.id.web);
		WebSettings ws = web.getSettings();
		//是否允许脚本支持
		ws.setJavaScriptEnabled(true);
		ws.setJavaScriptCanOpenWindowsAutomatically(true);
		ws.setSaveFormData(false);
		ws.setSavePassword(false);
		ws.setAppCacheEnabled(true);
		ws.setAppCacheMaxSize(10240);
//		ws.setCacheMode(WebSettings.LOAD_NO_CACHE);
		//是否允许缩放
//		ws.setBuiltInZoomControls(true);
		web.setWebViewClient(wvc);
		web.setWebChromeClient(wcc);
		
		web.setOnTouchListener(touchListener);
		
		conn(homeStr);
		
		loadProgress=(ProgressBar) findViewById(R.id.top_progressbar);
		
	}
	
	//-------------------------------------------
	/**
	 * 触摸监听
	 */
	OnTouchListener touchListener = new OnTouchListener() {
		
		public boolean onTouch(View v, MotionEvent event) {
			switch(v.getId()){
			case R.id.web:
				web.requestFocus();
				break;
			}
			return false;
		}
	};
    
    
    //-------------------------------------------
    
    WebViewClient wvc = new WebViewClient(){
    	
    	public boolean shouldOverrideUrlLoading(WebView view, String url) {
    		
    		view.loadUrl(url);
    		
    		return true;
    	};
    };
    
    
    //-------------------------------------------
    
    WebChromeClient wcc = new WebChromeClient(){

		public void onRequestFocus(WebView view) {
			super.onRequestFocus(view);
			view.requestFocus();
			
		}
		
		public void onReceivedTitle(WebView view, String title) {
			tv_title.setText(title);
		};
    	
    	
    };
    
    
    
    //-------------------------------------------
    /**
     * 访问url
     * @param urlStr
     */
    private void conn(String urlStr){
    	String url = "";
    	if(urlStr.contains("http://")){
    		url = urlStr;
    	}else{
    		url = "http://"+urlStr;
    	}
    	web.loadUrl(url);
    }
    
    //-------------------------------------------
    /**
     * 后退
     */
    private void goBack(){
    	if(web.canGoBack()){
    		web.goBack();
    	}else{
    		Toast.makeText(this, "已经是第一页",Toast.LENGTH_SHORT).show();
    	}
    }
    
    //-------------------------------------------
    /**
     * 前进
     */
    private void goForward(){
    	if(web.canGoForward()){
    		web.goForward();
    	}else{
    		Toast.makeText(this, "已经是最后一页",Toast.LENGTH_SHORT).show();
    	}
    }
   
    
	//-------------------------------------------
	public void onClick(View v) {
		
		switch (v.getId()) {	
		case R.id.iv_top_back:
			finish();
			break;

		}
	}

    
}

