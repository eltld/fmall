package com.ngu.meishishuo.app;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.ActionBar;
import android.app.Application;
import android.content.Context;

/**
 * @author zhoufeng06@qq.com
 * @time 2016年5月13日-下午3:34:38
 */
public class MyApp extends Application {
	@Override  
	public void onCreate() {  
		super.onCreate();
		initImageLoader(getApplicationContext());
	}
	/*
	 * 初始化imageloader
	 */
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		config.denyCacheImageMultipleSizesInMemory();
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		config.diskCacheSize(10 * 1024 * 1024); // 10 MiB
		config.tasksProcessingOrder(QueueProcessingType.LIFO);

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config.build());
	}
}
