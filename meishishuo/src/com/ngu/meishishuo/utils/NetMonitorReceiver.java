package com.ngu.meishishuo.utils;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author zhoufeng06@qq.com
 * @time 2016年5月30日-下午10:40:12
 * 监听网络状态变化
 * 网络连接状态变更广播监听器，其感兴趣action为ConnectivityManager.CONNECTIVITY_ACTION，
 * 主要用于实时更新网络连接状态。 注意添加权限android.permission.ACCESS_NETWORK_STATE。
 */

public class NetMonitorReceiver extends BroadcastReceiver
{
	public static final String ACTIVITY_NETWORK_TYPE_WIFI = "WIFI";// 当前正在使用WIFI作为数据传输
	public static final String ACTIVITY_NETWORK_TYPE_MOBILE = "MOBILE";// 当前正在使用GPRS作为数据传输
	public static final String ACTIVITY_NETWORK_TYPE_NONE = "NONE";// 当前没有正在使用的数据连接
	public static boolean wifiNetworkIsConnected = false;// WIFI连接状态
	public static boolean mobileNetworkIsConnected = false;// GPRS连接状态
	public static String activeNetworkType = ACTIVITY_NETWORK_TYPE_NONE;// 当前正在使用的数据传输类型
	private OnNetworkChangedListener listener;

	public NetMonitorReceiver()
	{
		super();
	}

	public NetMonitorReceiver(OnNetworkChangedListener listener)
	{
		super();
		this.listener = listener;
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
		if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction()))
		{
			refreshNetworkState(context);
			if (listener != null)
				listener.onNetworkChanged(wifiNetworkIsConnected,
						mobileNetworkIsConnected, activeNetworkType);
		}
	}

	/**
	 * 更新网络连接状态
	 * @param context
	 */
	private void refreshNetworkState(Context context)
	{
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connManager.getActiveNetworkInfo();
		// 更新当前数据传输类型
		if (activeNetworkInfo != null)
			activeNetworkType = activeNetworkInfo.getTypeName();
		else
			activeNetworkType = ACTIVITY_NETWORK_TYPE_NONE;
		NetworkInfo wifiNetworkInfo = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo mobileNetworkInfo = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		// 更新WIFI连接状态
		if (wifiNetworkInfo.isConnected())
			wifiNetworkIsConnected = true;
		else
			wifiNetworkIsConnected = false;
		// 更新GPRS连接状态
		if (mobileNetworkInfo.isConnected())
			mobileNetworkIsConnected = true;
		else
			mobileNetworkIsConnected = false;
	}

	/**
	 * 网络状态变更事件
	 */
	public interface OnNetworkChangedListener
	{
		/**
		 * 网络状态变更时被调用
		 * 
		 * @param wifiNetworkIsConnected
		 *            WIFI是否连接
		 * @param mobileNetworkIsConnected
		 *            GPRS是否连接
		 * @param activeNetworkType
		 *            当前数据传输类型，见ACTIVITY_NETWORK_TYPE_
		 */
		public void onNetworkChanged(boolean wifiNetworkIsConnected,
				boolean mobileNetworkIsConnected, String activeNetworkType);
	}
}