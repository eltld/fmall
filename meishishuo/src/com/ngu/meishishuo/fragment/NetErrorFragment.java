package com.ngu.meishishuo.fragment;

import com.ngu.meishishuo.R;
import com.ngu.meishishuo.utils.NetUtil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class NetErrorFragment extends Fragment {
	private RelativeLayout rl_warning;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// 
		View view=inflater.inflate(R.layout.fragment_neterror, container,false);
		rl_warning=(RelativeLayout) view.findViewById(R.id.rl_warning);
		rl_warning.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 
				if(NetUtil.isNetworkAvailable(getActivity())){
					MainFragment fragment=new MainFragment();
					getFragmentManager().beginTransaction().replace(R.id.main_content,fragment).commit();
				}
			}
		});
		return view;
	}
}
