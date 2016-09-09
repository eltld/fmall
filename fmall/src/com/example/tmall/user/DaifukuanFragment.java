package com.example.tmall.user;

import java.util.List;

import com.example.tmall.R;
import com.example.tmall.view.MyListView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DaifukuanFragment extends Fragment {
	
	private MyListView listView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.order_daifukuan,
				container, false);
		listView=(MyListView) view.findViewById(R.id.lv_order_products_list);
		return view;
	}
	
	
}
