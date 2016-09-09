package com.example.tmall.user;

import com.example.tmall.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/*
 * ´ýÊÕ»õ¶©µ¥
 */
public class DaishouhuoFragment extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.order_daishouhuo,
				container, false);
		return messageLayout;
	}

}
