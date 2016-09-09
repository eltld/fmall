package com.example.tmall.user;

import java.io.IOException;

import com.example.tmall.R;
import com.example.tmall.R.id;
import com.example.tmall.R.layout;
import com.example.tmall.constant.HttpUrl;
import com.example.tmall.http.OkHttpUtil;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends Activity implements OnClickListener{
	private Button register;
	private TextView Back;//返回
	private EditText et_name,et_password,et_password2;
	 @Override
	 protected void onCreate(Bundle savedInstanceState)
	 {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.register_activity);
		 initView();
	}
	private void initView(){
			Back=(TextView) findViewById(R.id.iv_top_back);
			Back.setOnClickListener(this);
			register=(Button) findViewById(R.id.register_btn);
			register.setOnClickListener(this);
			et_name=(EditText) findViewById(R.id.register_input_name);
			et_password=(EditText) findViewById(R.id.register_input_password);
			et_password2=(EditText) findViewById(R.id.register_input_2password);
	}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.iv_top_back:
				finish();
				break;
			case R.id.register_btn: 
				String username=et_name.getText().toString().trim();
				String password=et_password.getText().toString().trim();
				String password2=et_password2.getText().toString().trim();
				if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)||TextUtils.isEmpty(password2)){
					Toast.makeText(RegisterActivity.this ,"用户名或密码不能为空！", Toast.LENGTH_SHORT).show();
					
				}else{
					if(!password.equals(password2)){
						Toast.makeText(RegisterActivity.this ,"两次输入密码不一致！", Toast.LENGTH_SHORT).show();
					}
					else{
						new RegisterTask().execute(username,password);
					}
				}
				
				break;
			}
		}
		
		private final String SUCCESS="register_success";
		private final String FAIL="register_fail";
		/**
		 * 注册操作
		 * @param userName
		 * @param passWord
		 */
		private String doRegister(String userName,String passWord){
			String HttpArg;
			String result=FAIL;
			HttpArg="?username="+userName+"&password="+passWord;
			Request request=new Request.Builder()
	                .get()
	                .tag(this)
	                .url(HttpUrl.REGISTER_URL+HttpArg)
	                .build();
			Response response = null;
			try {
				response = OkHttpUtil.getOkHttpClient().newCall(request).execute();
				if (response.isSuccessful()) {
					result=response.body().string();
					return result;
				} else {
					throw new IOException("Unexpected code " + response);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}
		
		private class  RegisterTask extends AsyncTask<String,Integer,String>{

			@Override
			protected void onPreExecute() {

				super.onPreExecute();
				register.setClickable(false);
			}

			@Override
			protected void onPostExecute(String result) {

				super.onPostExecute(result);
				if(result.equals(SUCCESS)){
					Toast.makeText(RegisterActivity.this ,"注册成功！", Toast.LENGTH_SHORT).show();
					RegisterActivity.this.finish();
				}else{
					Toast.makeText(RegisterActivity.this ,"抱歉，出了点问题！", Toast.LENGTH_SHORT).show();
				}

				register.setClickable(true);
			}

			@Override
			protected void onProgressUpdate(Integer... values) {

				super.onProgressUpdate(values);
			}

			@Override
			protected String doInBackground(String... params) {

				return doRegister(params[0], params[1]);
			}
			
		}
}