package com.example.tmall.classify;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.tmall.R;
import com.example.tmall.R.id;
import com.example.tmall.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class CommentActivity extends Activity implements OnClickListener{
	private EditText submit_evaluation;
	private int id;//用户id，用来保存评价
	private TextView Back;//返回
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment_activity);		
		submit_evaluation = (EditText) findViewById(R.id.submit_evaluation);
		Back=(TextView) findViewById(R.id.iv_top_back);
		Back.setOnClickListener(this);
	}
	
	 public void submit(View view) {

		 	String evaluation = submit_evaluation.getText().toString().trim();
	        
	        if (TextUtils.isEmpty(evaluation) ) {
	            Toast.makeText(this, "亲，您还没对宝贝进行评价", Toast.LENGTH_SHORT).show();
	        } else {
	            // 模拟http请求，提交数据到服务器
	            String path = "http://123.456.789/web/LoginServlet?username="
	                    + id + "&evaluation=" + evaluation;
	            try {
	                URL url = new URL(path);
	                // 2.建立一个http连接
	                HttpURLConnection conn = (HttpURLConnection) url
	                        .openConnection();
	                // 3.设置一些请求方式
	                conn.setRequestMethod("GET");// 注意GET单词字幕一定要大写
	                conn.setRequestProperty(
	                        "User-Agent",
	                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");

	                int code = conn.getResponseCode(); // 服务器的响应码 200 OK //404 页面找不到
	                                                    // // 503服务器内部错误
	                if (code == 200) {
	                    InputStream is = conn.getInputStream();
	                    // 把is的内容转换为字符串
	                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	                    byte[] buffer = new byte[1024];
	                    int len = -1;
	                    while ((len = is.read(buffer)) != -1) {
	                        bos.write(buffer, 0, len);
	                    }
	                    String result = new String(bos.toByteArray());
	                    is.close();
	                    Toast.makeText(this, result, 0).show();

	                } else {
	                    Toast.makeText(this, "请求失败，失败原因: " + code, 0).show();
	                }

	            } catch (Exception e) {
	                e.printStackTrace();
	                Toast.makeText(this, "请求失败，请检查logcat日志控制台", 0).show();
	            }

	        }

	    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_top_back:
			CommentActivity.this.finish();
			break;

		default:
			break;
		}
	}

}
