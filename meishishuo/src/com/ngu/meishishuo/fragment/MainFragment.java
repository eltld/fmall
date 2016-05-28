package com.ngu.meishishuo.fragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ngu.meishishuo.R;
import com.ngu.meishishuo.activity.DetailActivity;
import com.ngu.meishishuo.adapter.MainGridViewAdapter;
import com.ngu.meishishuo.customview.HeaderGridView;
import com.ngu.meishishuo.customview.MyImageTopView;
import com.ngu.meishishuo.model.MeiShi;
import com.ngu.meishishuo.utils.AllUrl;
import com.ngu.meishishuo.utils.NetUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainFragment extends Fragment implements OnItemClickListener{
	private MyImageTopView mTopView;// 自定义控件，用于显示上方图片的容器
	private LinearLayout mBottomView;// 显示下方圆圈的容器（线性布局）
	private HeaderGridView mHeadGridView;
	private List<MeiShi> resultList;
	private String httpArg="id=0&rows=8&page=1";
	private int[] imgIds = new int[] { R.drawable.meishi_jiaozi, R.drawable.meishi_kongxincai, R.drawable.meishi_nuomijuan,R.drawable.meishi_tudousi};
	public ImageView[] imgViews = new ImageView[imgIds.length];//下方圆点的个数
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// 
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// 
		View view=inflater.inflate(R.layout.fragment_main, container,false);
		mHeadGridView=(HeaderGridView) view.findViewById(R.id.mGridView);
		View headerView=inflater.inflate(R.layout.fragment_main_header,container,false);
		mBottomView = (LinearLayout) headerView.findViewById(R.id.mBottomView);
		mTopView = (MyImageTopView) headerView.findViewById(R.id.mTopView);
		initBottom();// 初始化底部的圆圈，默认第一个为选中
		mTopView.initImages(imgIds);//初始化要显示的图片
		mTopView.setBottomImageViews(imgViews);//
		mHeadGridView.addHeaderView(headerView);
		mHeadGridView.setOnItemClickListener(this);
		if(NetUtil.isNetworkAvailable(getActivity())){
			new MeiShiAsyncTask().execute(AllUrl.listUrl,httpArg);
		}
		else{
			Toast.makeText(getActivity(), "网络不可用，请检查网络设置！", Toast.LENGTH_SHORT).show();
		}
		return view;
	}
	
	public void initBottom() {// 初始化底部的小圆圈，并为圆圈添加单击事件处理	
		for (int i = 0; i < imgViews.length; i++) {
			imgViews[i] = new ImageView(getActivity());//创建ImageView显示圆圈
			if (i == 0) {//默认选中第一个
				imgViews[i].setImageResource(R.drawable.circle_choosed);
			} else {
				imgViews[i].setImageResource(R.drawable.circle_unchoosed);
			}
			imgViews[i].setPadding(15, 0, 0, 0);//设置圆圈之间的边距
			imgViews[i].setId(i);//为每个圆圈添加ID
			imgViews[i].setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					resetImg();//使所有的圆圈都不选中
					((ImageView) v).setImageResource(R.drawable.circle_choosed);//将当前圆圈设为选中状态
					mTopView.scrollToImage(v.getId());//使图片切换到当前圆圈所对应的图片
				}
			});
			mBottomView.addView(imgViews[i]);//将所有的圆圈都添加到线性布局中
		}
	}	
	
	public void resetImg() {// 该方法用于将所有的圆圈都显示为未选中状态
		for (int i = 0; i < imgViews.length; i++) {
			imgViews[i].setImageResource(R.drawable.circle_unchoosed);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		//
		if(position<2){
			
		}
		else{
			if(NetUtil.isNetworkAvailable(getActivity())){
				Intent intent=new Intent(getActivity(),DetailActivity.class);
				intent.putExtra("ID", resultList.get(position-2).getId());
				startActivity(intent);
			}else{
				Toast.makeText(getActivity(), "网络不可用，请检查网络设置！", Toast.LENGTH_SHORT).show();
			}
		}
	}
	/*
	 * 异步加载数据
	 * 
	 */
	public class MeiShiAsyncTask extends AsyncTask<String, Void, List<MeiShi>>
	{
		private ProgressDialog dialog;
		@Override
		protected void onPreExecute() {
			// 
			dialog = ProgressDialog.show(getContext(), "提示", "正在加载. . .");
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(List<MeiShi> result) {
			super.onPostExecute(result);
			dialog.dismiss();
			resultList=result;
			mHeadGridView.setAdapter(new MainGridViewAdapter(getContext(),resultList));
			}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
			 dialog.setCancelable(true);
		}

		@Override
		protected List<MeiShi> doInBackground(String... params) {
			
			return request(params[0],params[1]);
		}
		/**
		 * 获取json数据
		 * @param urlAll
		 *            :请求接口
		 * @param httpArg
		 *            :参数
		 * @return 返回结果
		 */
		public  List<MeiShi> request(String httpUrl,String httpArg)
		{
			BufferedReader reader = null;
		    String result = null;
		    List<MeiShi> resultList = new ArrayList<MeiShi>();
		    String Url=httpUrl+"?"+httpArg;
		    StringBuffer sbf = new StringBuffer();
		    try {
		        URL url = new URL(Url);
		        HttpURLConnection connection = (HttpURLConnection) url
		                .openConnection();
		        connection.setRequestMethod("GET");
		        // 填入apikey到HTTP header
		        //connection.setRequestProperty("apikey",  "your apikey");
		        connection.connect();
		        InputStream is = connection.getInputStream();
		        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		        String strRead = null;
		        while ((strRead = reader.readLine()) != null) {
		            sbf.append(strRead);
		            sbf.append("\r\n");
		        }
		        reader.close();
		        result = sbf.toString();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    try {
		    	MeiShi item;
				JSONObject jsonObject=new JSONObject(result);
				JSONArray jsonArray=jsonObject.getJSONArray("tngou");
				for(int i=0;i<jsonArray.length();i++)
				{
					item=new MeiShi();
					jsonObject =jsonArray.getJSONObject(i);					
					item.setName(jsonObject.getString("name"));
					item.setImg(jsonObject.getString("img"));
					//item.setKeywords(jsonObject.getString("keywords"));
					//item.setCount(jsonObject.getString("count"));
					item.setId(jsonObject.getString("id"));
					resultList.add(item);
				}
		    }catch(Exception e){
		    	e.printStackTrace();
		    }
		    return resultList;
		}
	}
}
