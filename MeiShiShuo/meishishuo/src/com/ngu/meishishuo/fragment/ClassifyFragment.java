package com.ngu.meishishuo.fragment;

import java.util.ArrayList;
import java.util.List;


import com.ngu.meishishuo.R;
import com.ngu.meishishuo.activity.ClassifyActivity;
import com.ngu.meishishuo.adapter.ClassifyAdapter;
import com.ngu.meishishuo.bean.MeiShi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

/**
 * @author zhoufeng06@qq.com
 * 分类
 */
public class ClassifyFragment extends Fragment implements OnItemClickListener {
	private ListView pListView,cListView;
	private List<List<MeiShi>> cListList;
	private List<MeiShi> pList,cList;
	private ClassifyAdapter pAdapter;
	private ChildClassifyAdpter cAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// 
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// 
		View view =inflater.inflate(R.layout.fragment_classify, container,false);
		pListView=(ListView) view.findViewById(R.id.classify_parent_lv);
		cListView=(ListView) view.findViewById(R.id.classify_lv);
		initData();
		//
		pAdapter=new ClassifyAdapter(getContext(), pList);
		pListView.setAdapter(pAdapter);
		pListView.setOnItemClickListener(this);
		//
		cList=new ArrayList<MeiShi>();
		cAdapter=new ChildClassifyAdpter(getActivity(), cList);
		cListView.setAdapter(cAdapter);
		initChild(0);
		return view;
	}
	
	//父分类条目点击事件
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		//
		pAdapter.setNowSelectedIndex(position);
		initChild(position);
		
	}
	
	private void initChild(int position) {
		//先清除先前增加的子分类
		if(cList!=null){
			cList.clear();
		}
		cList.addAll(cListList.get(position));
		
        cAdapter.notifyDataSetChanged();
        cListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            cAdapter.setNowSelectedIndex(position);
	      	Intent intent=new Intent(getActivity(),ClassifyActivity.class);
	      	intent.putExtra("ID", cList.get(position).getId());
	      	intent.putExtra("NAME", cList.get(position).getName());
	      	startActivity(intent);
          }
      });
       
   }
	
	//初始化分类列表数据
	private void initData(){
		//父分类列表
		pList=new ArrayList<MeiShi>();
		pList.add(new MeiShi("1","美容"));
		pList.add(new MeiShi("10","减肥"));
		pList.add(new MeiShi("15","保健养生"));
		pList.add(new MeiShi("52","人群"));
		pList.add(new MeiShi("62","时节"));
		pList.add(new MeiShi("68","餐时"));
		pList.add(new MeiShi("82","调养"));
		pList.add(new MeiShi("132","其他"));
		//子分类列表
		cListList=new ArrayList<List<MeiShi>>();
		cList=new ArrayList<MeiShi>();
		cList.add(new MeiShi("2","养颜"));
		cList.add(new MeiShi("4","美白"));
		cList.add(new MeiShi("7","祛痘"));
		cList.add(new MeiShi("8","润肤"));
		cList.add(new MeiShi("9","保湿"));
		cListList.add(cList);
		cList=new ArrayList<MeiShi>();
		cList.add(new MeiShi("11","瘦身"));
		cList.add(new MeiShi("12","瘦脸"));
		cList.add(new MeiShi("13","瘦腿"));
		cList.add(new MeiShi("14","丰胸"));
		cListList.add(cList);
		cList=new ArrayList<MeiShi>();
		cList.add(new MeiShi("16","补钙"));
		cList.add(new MeiShi("19","润肺"));
		cList.add(new MeiShi("21","养胃"));
		cList.add(new MeiShi("37","护眼"));
		cList.add(new MeiShi("42","解暑"));
		cList.add(new MeiShi("43","清热"));
		cListList.add(cList);
		cList=new ArrayList<MeiShi>();
		cList.add(new MeiShi("53","宝宝"));
		cList.add(new MeiShi("54","女性"));
		cList.add(new MeiShi("58","考生"));
		cList.add(new MeiShi("59","白领"));
		cList.add(new MeiShi("61","老年人"));
		cList.add(new MeiShi("57","男性"));
		cListList.add(cList);
		cList=new ArrayList<MeiShi>();
		cList.add(new MeiShi("63","春天"));
		cList.add(new MeiShi("64","夏天"));
		cList.add(new MeiShi("65","秋天"));
		cList.add(new MeiShi("66","冬天"));
		cList.add(new MeiShi("67","节日"));
		cListList.add(cList);
		cList=new ArrayList<MeiShi>();
		cList.add(new MeiShi("69","早餐"));
		cList.add(new MeiShi("70","晚餐"));
		cList.add(new MeiShi("71","夜宵"));
		cList.add(new MeiShi("72","睡前"));
		cList.add(new MeiShi("73","饭前"));
		cList.add(new MeiShi("74","空腹"));
		cListList.add(cList);
		cList=new ArrayList<MeiShi>();
		cList.add(new MeiShi("85","疲劳"));
		cList.add(new MeiShi("86","上火"));
		cList.add(new MeiShi("94","压力大"));
		cList.add(new MeiShi("96","消化不良"));
		cList.add(new MeiShi("91","缺钙"));
		cList.add(new MeiShi("90","气虚"));
		cListList.add(cList);
		cList=new ArrayList<MeiShi>();
		cList.add(new MeiShi("145","中暑"));
		cList.add(new MeiShi("142","解酒"));
		cList.add(new MeiShi("144","湿热"));
		cListList.add(cList);
	}
	//子分类列表数据适配器
	public class ChildClassifyAdpter extends BaseAdapter {

	    private Context context;
	    private List<MeiShi> cList;
	    private int nowSelectedIndex = 0;

	    public ChildClassifyAdpter(Context context, List<MeiShi> cList) {
	        this.context = context;
	        this.cList = cList;
	    }

	    @Override
	    public int getCount() {
	        return cList.size();
	    }

	    @Override
	    public Object getItem(int position) {
	        return cList.get(position);
	    }

	    @Override
	    public long getItemId(int position) {
	        return position;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        if (convertView == null) {
	            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_classify_item, null);
	        }
	        TextView tv_list_item = (TextView) convertView.findViewById(R.id.classify_item_tv);
	        tv_list_item.setText(cList.get(position).getName().toString());

	        if (position == nowSelectedIndex) {
	            tv_list_item.setTextColor(0xFFB3EE3A);
	        } else {
	            tv_list_item.setTextColor(0xFF525252);
	        }
	        return  convertView;
	    }

	    public int getNowSelectedIndex() {
	        return nowSelectedIndex;
	    }

	    public void setNowSelectedIndex(int nowSelectedIndex) {
	        this.nowSelectedIndex = nowSelectedIndex;
	        this.notifyDataSetChanged();//及时通知显示
	    }
	}
}
