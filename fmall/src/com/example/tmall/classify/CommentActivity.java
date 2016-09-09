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
	private int id;//�û�id��������������
	private TextView Back;//����
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
	            Toast.makeText(this, "�ף�����û�Ա�����������", Toast.LENGTH_SHORT).show();
	        } else {
	            // ģ��http�����ύ���ݵ�������
	            String path = "http://123.456.789/web/LoginServlet?username="
	                    + id + "&evaluation=" + evaluation;
	            try {
	                URL url = new URL(path);
	                // 2.����һ��http����
	                HttpURLConnection conn = (HttpURLConnection) url
	                        .openConnection();
	                // 3.����һЩ����ʽ
	                conn.setRequestMethod("GET");// ע��GET������Ļһ��Ҫ��д
	                conn.setRequestProperty(
	                        "User-Agent",
	                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");

	                int code = conn.getResponseCode(); // ����������Ӧ�� 200 OK //404 ҳ���Ҳ���
	                                                    // // 503�������ڲ�����
	                if (code == 200) {
	                    InputStream is = conn.getInputStream();
	                    // ��is������ת��Ϊ�ַ���
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
	                    Toast.makeText(this, "����ʧ�ܣ�ʧ��ԭ��: " + code, 0).show();
	                }

	            } catch (Exception e) {
	                e.printStackTrace();
	                Toast.makeText(this, "����ʧ�ܣ�����logcat��־����̨", 0).show();
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
