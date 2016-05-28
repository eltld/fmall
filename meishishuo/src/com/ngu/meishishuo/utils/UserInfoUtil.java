package com.ngu.meishishuo.utils;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserInfoUtil {
	public static final String USERNAME="username";//用户名
	public static final String PASSWORD="password";//密码
	public static final String MAIL="mail";//邮箱
	public static final String NAME="name";//昵称
	public static final String SIGNATURE="signature";//个性签名

	/**
	 * 保存用户名密码
	 * @param context 上下文环境
	 * @param userinfo 
	 * @return true if success else false
	 */
	public static boolean saveUser(Context context,Map<String,String> userinfo){
		SharedPreferences prefs = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
		Editor edit=prefs.edit();
		edit.putString(USERNAME, userinfo.get(USERNAME));
		edit.putString(PASSWORD, userinfo.get(PASSWORD));
		return edit.commit();
	}
		/**
		 * 保存用户昵称
		 * @param context
		 * @param userinfo
		 * @return true if success else false
		 */
		public static boolean saveName(Context context,String name){
			SharedPreferences prefs = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
			Editor edit=prefs.edit();
			edit.putString(NAME, name);
			return edit.commit();
		}
		/**
		 * 保存用户邮箱
		 * @param context
		 * @param userinfo
		 * @return true if success else false
		 */
		public static boolean saveMail(Context context,String mail){
			SharedPreferences prefs = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
			Editor edit=prefs.edit();
			edit.putString(MAIL, mail);
			return edit.commit();
		}
		/**
		 * 保存用户个性签名
		 * @param context
		 * @param userinfo
		 * @return true if success else false
		 */
		public static boolean saveSignature(Context context,String signature){
			SharedPreferences prefs = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
			Editor edit=prefs.edit();
			edit.putString(SIGNATURE, signature);
			return edit.commit();
		}
		
	/**
	 * 获取用户个人信息
	 * @param context
	 * @return
	 */
	public static Map<String ,String> getUserInfo(Context context){
		SharedPreferences prefs = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
		String username=prefs.getString(USERNAME, null);
		String password=prefs.getString(PASSWORD, null);
		String mail=prefs.getString(MAIL, null);
		String name=prefs.getString(NAME, null);
		String signature=prefs.getString(SIGNATURE, null);
		Map<String,String> userInfo=new HashMap<String, String>();
		userInfo.put(USERNAME, username);
		userInfo.put(PASSWORD, password);
		userInfo.put(MAIL, mail);
		userInfo.put(NAME, name);
		userInfo.put(SIGNATURE, signature);
		return userInfo;
	}
}
