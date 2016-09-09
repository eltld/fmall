package com.example.tmall.classify;

import com.example.tmall.R;
import com.example.tmall.utils.NetManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 分类界面
 *
 */
public class ClassifyFragment extends Fragment implements OnItemClickListener {
	private NetManager netMagager;
	private String [] classifyList;
	private int [] classifyImage={R.drawable.gehuhuazhuang,R.drawable.neiyipeishi,R.drawable.yiyaobaojian,R.drawable.pinpainanzhuang,
			R.drawable.shepinlipin,R.drawable.jiajujiancai,R.drawable.jiayongdianqi,R.drawable.jujiashenghuo,
			R.drawable.huwaiyundong,R.drawable.shoujishuma,R.drawable.muyingtongzhuang,R.drawable.qicheyongpin,
			R.drawable.chaoliunvhzuang,R.drawable.wanjuyueqi,R.drawable.diannaobangong,R.drawable.jiushuiyinliao,
			R.drawable.zhongbiaozhubao,R.drawable.xiexuexiangbao,R.drawable.shipinshengxian};
	private ListView listView;
	private ClassifyAdapter mAdapter;
	private TextView textView;
	private ImageView imageView;
	private int mPosition=0;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
    	View view=inflater.inflate(R.layout.classify_fragment, null);
    	initView(view);
		return view;
	}

	private void initView(View view){
		netMagager=new NetManager(getActivity());
		textView=(TextView) view.findViewById(R.id.classify_textview);
		imageView=(ImageView) view.findViewById(R.id.classify_imageview);
		listView=(ListView) view.findViewById(R.id.classify_listview);
		
		classifyList=new String[]{"个护化妆","内衣配饰","医药保健","品牌男装","奢品礼品","家居建材","家用电器","居家生活","户外运动","手机数码","母婴童装","汽车用品","潮流女装","玩具乐器","电脑办公","酒水饮料","钟表珠宝","鞋靴箱包","食品生鲜"};
		textView.setText(classifyList[0]);
		imageView.setBackgroundResource(classifyImage[0]);
		
		mAdapter=new ClassifyAdapter(classifyList, getActivity());
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(this);
		
		//
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if(netMagager.isOpenNetwork()){
					Intent intent=new Intent(getActivity(),ProductsActivity.class);
					intent.putExtra(CLASSIFY,classifyList[mPosition]);
					startActivity(intent);
				}else{
					Toast.makeText(getActivity(), "没有网络啊！", Toast.LENGTH_SHORT).show();
				}
			}
		});
    }
	
	public static final String CLASSIFY="classify_name";
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mAdapter.setSelectedIndex(position);
		textView.setText(classifyList[position]);
		imageView.setBackgroundResource(classifyImage[position]);
		mPosition=position;
	}
}
