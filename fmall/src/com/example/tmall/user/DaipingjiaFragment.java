package com.example.tmall.user;

import com.example.tmall.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/*
 * ´ýÆÀ¼Û¶©µ¥
 */
public class DaipingjiaFragment extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.order_daipingjia,
				container, false);
		return messageLayout;
	}

}
