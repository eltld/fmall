package com.example.tmall.http;

import okhttp3.OkHttpClient;


public class OkHttpUtil {
	private static final OkHttpClient okHttpClient = new OkHttpClient();
	
	public static OkHttpClient getOkHttpClient(){
		return okHttpClient;
	}
		
	
}
