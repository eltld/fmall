package com.ngu.meishishuo.utils;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserInfoUtil {
	public static final String USERNAME="username";
	public static final String PASSWORD="password";
	public static final String HEADIMAGE="headimage";
	//保存用户名密码到userinfo
	public static boolean saveUserInfo(Context context,String username,String password){
		SharedPreferences prefs = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
		Editor edit=prefs.edit();
		edit.putString(USERNAME, username);
		edit.putString(PASSWORD, password);
		return edit.commit();
	}
	//从userinfo中获取用户名密码
	public static Map<String ,String> getUserInfo(Context context){
		SharedPreferences prefs = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
		String username=prefs.getString(USERNAME, null);
		String password=prefs.getString(PASSWORD, null);
		Map<String,String> userInfo=new HashMap<String, String>();
		userInfo.put(USERNAME, username);
		userInfo.put(PASSWORD, password);
		return userInfo;
	}
}
