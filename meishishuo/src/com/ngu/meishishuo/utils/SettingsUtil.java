package com.ngu.meishishuo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SettingsUtil {
	public static final String NO_IMAGE = "no_image";	// 无图模式
	public static final String IS_LOGIN ="is_login";//是否登录
	public static final String ACTIONBAR_COLOR="actionbar_color";//主题颜色
	/**
	 * 获取配置
	 * @param context
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static boolean get(Context context, String name) {
		SharedPreferences prefs = context.getSharedPreferences("settings",Context.MODE_PRIVATE);
		boolean value = prefs.getBoolean(name, false);
		return value;
	}
	
	/**
	 * 保存用户配置
	 * @param context
	 * @param name
	 * @param value
	 * @return
	 */
	public static boolean set(Context context, String name, boolean value) {
		SharedPreferences prefs = context.getSharedPreferences("settings",Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putBoolean(name, value);
		return editor.commit();
	}
}
