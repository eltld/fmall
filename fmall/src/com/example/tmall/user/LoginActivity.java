package com.example.tmall.user;

import java.io.IOException;

import com.example.tmall.Datas;
import com.example.tmall.R;
import com.example.tmall.R.id;
import com.example.tmall.R.layout;
import com.example.tmall.constant.HttpUrl;
import com.example.tmall.http.OkHttpUtil;

import android.app.Activity;
import android.content.Intent;
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

public class LoginActivity extends Activity implements OnClickListener{

	private Button login;
	private TextView register;
	private EditText et_name,et_password;
	private TextView Back;//返回
	private String username;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		
		initView();
		
	}
	
	private void initView(){
		Back=(TextView) findViewById(R.id.iv_top_back);
		Back.setOnClickListener(this);
		et_name=(EditText) findViewById(R.id.login_input_name);
		et_password=(EditText) findViewById(R.id.login_input_password);
		register=(TextView)findViewById(R.id.tv_register);
		login=(Button) findViewById(R.id.login_btn);
		login.setOnClickListener(this);
	    register.setOnClickListener(this);
	    et_name.setText("zhoufeng");
	    et_password.setText("123456");
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.login_btn:
			username=et_name.getText().toString().trim();
			String password=et_password.getText().toString().trim();
			if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
				Toast.makeText(LoginActivity.this ,"用户名或密码不能为空！", Toast.LENGTH_SHORT).show();
			}else{
				new LoginTask().execute(username,password);
			}

			break;
		case R.id.tv_register:
			Intent intent2=new Intent(LoginActivity.this,RegisterActivity.class);
			startActivity(intent2);
			break;
		case R.id.iv_top_back:
			finish();
			break;
		}
	}
	
	private final String SUCCESS="login_success";
	private final String FAIL="login_fail";
	/**
	 * 登录操作
	 * @param userName
	 * @param passWord
	 */
	private String doLogin(String userName,String passWord){
		String HttpArg;
		String result=FAIL;
		HttpArg="?username="+userName+"&password="+passWord;
		Request request=new Request.Builder()
                .get()
                .tag(this)
                .url(HttpUrl.LOGIN_URl+HttpArg)
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
	
	private class  LoginTask extends AsyncTask<String,Integer,String>{

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			login.setClickable(false);
		}

		@Override
		protected void onPostExecute(String result) {

			super.onPostExecute(result);
			if(result.equals(SUCCESS)){
				Toast.makeText(LoginActivity.this ,"登录成功！", Toast.LENGTH_SHORT).show();
				Datas.currentUser.setUserName(username);
				Datas.isLogin=true;
				LoginActivity.this.finish();
			}else{
				Toast.makeText(LoginActivity.this ,"抱歉，出了点问题！", Toast.LENGTH_SHORT).show();
			}
			login.setClickable(true);
			
		}

		@Override
		protected void onProgressUpdate(Integer... values) {

			super.onProgressUpdate(values);
		}

		@Override
		protected String doInBackground(String... params) {

			return doLogin(params[0], params[1]);
		}
		
	}

}
