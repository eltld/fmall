package com.example.tmall.home;

import java.util.ArrayList;

import com.example.tmall.BrowserActivity;
import com.example.tmall.R;
import com.example.tmall.adapter.ClassifyGridViewAdapter;
import com.example.tmall.bean.Type;
import com.example.tmall.utils.NetManager;
import com.example.tmall.view.AbOnItemClickListener;
import com.example.tmall.view.AbSlidingPlayView;
import com.squareup.picasso.Picasso;
import com.zxing.activity.CaptureActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ��ҳ
 */
public class HomeFragment extends Fragment implements OnRefreshListener{
	
	private NetManager netMagager;
	//����������
	private EditText tv_top_search;
	//����ľŹ���
	private GridView classifyGridView;
	private ClassifyGridViewAdapter adapter_GridView_classify;
	//��ҳ�ֲ�
	private AbSlidingPlayView viewPager;
	private ArrayList<View> allListView;
	//����ˢ��
	private SwipeRefreshLayout refreshLayout;
	//ɨһɨ
	private ImageView iv_saoyisao,iv_search;
	// ����Ź������Դ�ļ�
	private int[] pic_path_classify = {R.drawable.home_chongzhi,R.drawable.home_juhuasuan, R.drawable.home_haitao,  R.drawable.home_fenlei, R.drawable.home_qingcang, R.drawable.home_tejia, R.drawable.home_zhifubao, R.drawable.home_gengduo };
	private String[] typeName={"���ֵ","�黮��","����","����","���","�ؼ�","��֧��","����"};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_fragment, null);
		initView(view);
		return view;
	}

	private void initView(View view) {
		netMagager=new NetManager(getActivity());
		//����ˢ��
		refreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.home_refreshlayout);
		refreshLayout.setOnRefreshListener(HomeFragment.this);
		//ɨһɨ
		iv_saoyisao=(ImageView) view.findViewById(R.id.saoyisao);
		iv_saoyisao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				Intent openCameraIntent = new Intent(getActivity(),
						CaptureActivity.class);
				startActivityForResult(openCameraIntent, 0);
			}
		});
		//����
		iv_search=(ImageView) view.findViewById(R.id.search_btn);
		tv_top_search=(EditText) view.findViewById(R.id.search_edit);
		iv_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(netMagager.isOpenNetwork()){
					String str = tv_top_search.getText().toString();
					if(!TextUtils.isEmpty(str)){
						Intent intent=new Intent(getActivity(),ProductsSearchActivity.class);
						intent.putExtra("classify_name",str);
						startActivity(intent);
					}else{
						Toast.makeText(getActivity(), "�������ݲ���Ϊ�գ�", Toast.LENGTH_SHORT).show();

					}
				}else{
					Toast.makeText(getActivity(), "û�����簡��", Toast.LENGTH_SHORT).show();
				}
			}
		});
		//����Ź���
		classifyGridView = (GridView) view.findViewById(R.id.gridview_classify);
		classifyGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		Type type;
		ArrayList<Type> list=new ArrayList<Type>();
		for(int i=0;i<8;i++){
			type=new Type();
			type.setId(pic_path_classify[i]);
			type.setTypename(typeName[i]);
			list.add(type);
		}
		adapter_GridView_classify = new ClassifyGridViewAdapter(getActivity(), list);
		classifyGridView.setAdapter(adapter_GridView_classify);
		classifyGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//
				String url="www.feimiao.com";
				switch (position) {
				case 0://��ֵ
					url="h5.m.taobao.com/app/cz/cost.html";
					Intent intent = new Intent(getActivity(), BrowserActivity.class);
					intent.putExtra(BrowserActivity.BROWSERURL, url);
					startActivity(intent);
					break;
				case 1://�ۻ���
					
					break;
				case 2://����
					
					break;
				case 3://����
					
					break;
				case 4://���
					
					break;
				case 5://�ؼ�
					
					break;
				case 6://֧����
					
					break;
				case 7://����
					
					break;
					
				default:
					break;
				}
				
				
			}
		});
		
		//����ֲ�
		viewPager = (AbSlidingPlayView) view.findViewById(R.id.viewPager_menu);
		//���ò��ŷ�ʽΪ˳�򲥷�
		viewPager.setPlayType(1);
		//���ò��ż��ʱ��
		viewPager.setSleepTime(3000);
		initViewPager();
	}

	private void initViewPager() {

		if (allListView != null) {
			allListView.clear();
			allListView = null;
		}
		allListView = new ArrayList<View>();
		
		String[] adUrl={"https://aecpm.alicdn.com/simba/img/TB1tF3PKVXXXXXAXpXXSutbFXXX.jpg",
				"https://img.alicdn.com/tps/TB16FcnKVXXXXaTXpXXXXXXXXXX-520-280.png",
				"https://aecpm.alicdn.com/tps/i1/TB1r4h8JXXXXXXoXXXXvKyzTVXX-520-280.jpg",
				"https://img.alicdn.com/tps/TB1iFEbKVXXXXbLXVXXXXXXXXXX-520-280.jpg",
				"https://aecpm.alicdn.com/tps/i2/TB10vPXKpXXXXacXXXXvKyzTVXX-520-280.jpg"};
		for (int i = 0; i < 5; i++) {
			//����ViewPager�Ĳ���
			View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_imageview, null);
			ImageView imageView = (ImageView) view.findViewById(R.id.pic_item);
			Picasso.with(getActivity()).load(adUrl[i]).into(imageView);
			allListView.add(view);
		}
		
		viewPager.addViews(allListView);
		//��ʼ�ֲ�
		viewPager.startPlay();
		viewPager.setOnItemClickListener(new AbOnItemClickListener() {
			@Override
			public void onClick(int position) {
				//��ת���������
				//Intent intent = new Intent(getActivity(), DetailActivity.class);
				//startActivity(intent);
				Toast.makeText(getActivity(), "�鿴����", Toast.LENGTH_SHORT).show();
			}
		});
	}
	//���ˢ��
	private static final int REFRESH_COMPLETE=1;
	private Handler mHandler = new Handler()  
    {  
        public void handleMessage(android.os.Message msg)  
        {  
            switch (msg.what)  
            {  
	            case REFRESH_COMPLETE:  
	                refreshLayout.setRefreshing(false);
	                Toast.makeText(getActivity(), "ˢ�³ɹ�", Toast.LENGTH_SHORT).show();
	                break;  
  
            }  
        };  
    };  
	@Override
	public void onRefresh() {

		mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);  
	}
	
	

}
