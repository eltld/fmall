package com.ngu.meishishuo.fragment;

import com.ngu.meishishuo.R;
import com.ngu.meishishuo.utils.UserInfoUtil;

import android.app.ActionBar;
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

public class RegisterFragment extends Fragment {
	private Button btn_register;
	private EditText et_mail,et_password;
	private ActionBar actionBar;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// 
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		actionBar=getActivity().getActionBar();
		actionBar.setTitle("注册");
		View view= inflater.inflate(R.layout.fragment_register, container, false);
		et_mail=(EditText) view.findViewById(R.id.et_mail);
		et_password=(EditText) view.findViewById(R.id.et_psw);
		btn_register=(Button) view.findViewById(R.id.btn_register);
		IntiEvent();
		return view;
	}
	
	//事件监听
	private void IntiEvent(){
		btn_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//
				String username,password;
				username=et_mail.getText().toString().trim();
				password=et_password.getText().toString().trim();
				if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password))
				{
					Toast.makeText(getActivity(), "用户名或密码不能为空！", Toast.LENGTH_SHORT).show();
				}
				else{
					UserInfoUtil.saveUserInfo(getActivity(), username, password);
					Toast.makeText(getActivity(), "注册成功！", Toast.LENGTH_SHORT).show();
					LoginFragment fragment=new LoginFragment();
					getFragmentManager().beginTransaction().replace(R.id.login_container,fragment).commit();
				}
			}
		});
	}
}