package com.ngu.meishishuo.fragment;

import java.util.HashMap;
import java.util.Map;

import com.ngu.meishishuo.R;
import com.ngu.meishishuo.activity.LoginActivity;
import com.ngu.meishishuo.utils.SettingsUtil;
import com.ngu.meishishuo.utils.UserInfoUtil;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment {
	private ActionBar actionBar;
	private Button registerButton,loginButton;
	private EditText et_username,et_password;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// 
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// 
		actionBar=getActivity().getActionBar();
		actionBar.setTitle("登录");
		View view= inflater.inflate(R.layout.fragment_login, container, false);
		registerButton=(Button) view.findViewById(R.id.btn_register_now);
		loginButton=(Button) view.findViewById(R.id.btn_login);
		et_password=(EditText) view.findViewById(R.id.et_password);
		et_username=(EditText) view.findViewById(R.id.et_name);
		String username=UserInfoUtil.getUserInfo(getActivity()).get(UserInfoUtil.USERNAME);
		if(!"".equals(username)){
			et_username.setText(username);
		}
		InitEvent();
		return view;
	}
	//控件监听
	private void InitEvent(){
		
		loginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 
				String username,password;
				username=et_username.getText().toString().trim();
				password=et_password.getText().toString().trim();
				if(loginSuccess(username,password)){
					SettingsUtil.set(getActivity(), SettingsUtil.IS_LOGIN, true);
					//将登录用户名回传给MainActivity
					Intent intent=new Intent();
					intent.putExtra("username", username);
					getActivity().setResult(1,intent);//resultCode=1
					//退出当前activity
					getActivity().finish();
				}
			}
		});
		registerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 
				RegisterFragment fragment=new RegisterFragment();
				getFragmentManager().beginTransaction().replace(R.id.login_container,fragment).commit();
			}
		});
	}
	
	//登录检查
	private boolean loginSuccess(String username,String password){
		Map<String ,String> userInfo=new HashMap<String,String>();
		userInfo=UserInfoUtil.getUserInfo(getActivity());
		if(username.equals(userInfo.get(UserInfoUtil.USERNAME))&&password.equals(userInfo.get(UserInfoUtil.PASSWORD))){
			Toast.makeText(getActivity(), "登录成功！", Toast.LENGTH_SHORT).show();
			return true;
		}
		else {
			if(!username.equals(userInfo.get(UserInfoUtil.USERNAME))){
				Toast.makeText(getActivity(), "用户名不存在！", Toast.LENGTH_SHORT).show();
				return false;
			}
			if(!password.equals(userInfo.get(UserInfoUtil.PASSWORD))){
				Toast.makeText(getActivity(), "密码有误！", Toast.LENGTH_SHORT).show();
				return false;
			}
			if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
				Toast.makeText(getActivity(), "用户名或密码不能为空！", Toast.LENGTH_SHORT).show();
				return false;
			}
			return false;
		}
	}
}
