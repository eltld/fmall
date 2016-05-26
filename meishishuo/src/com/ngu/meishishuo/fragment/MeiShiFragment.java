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
import com.ngu.meishishuo.adapter.MeiShiAdapter;
import com.ngu.meishishuo.model.MeiShi;
import com.ngu.meishishuo.utils.Constants;
import com.ngu.meishishuo.utils.NetUtil;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MeiShiFragment extends Fragment implements OnItemClickListener{
	private ListView mainListView;
	private List<MeiShi> resultList;
	private ActionBar actionBar;
	private String httpArg="";
	private String rows="10";//每页返回的条数，默认rows=20
	private String page="1";//请求页数，默认page=1
	private String id="0";
	private String name;

	public MeiShiFragment(String id,String name){
		this.id=id;
		this.name=name;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			httpArg="id="+id+"&rows="+rows+"&page="+page;
			actionBar=getActivity().getActionBar();
			actionBar.setTitle(name);
			View view= inflater.inflate(R.layout.fragment_meishi, container, false);
			mainListView=(ListView)view.findViewById(R.id.fragment_meishi_listview);
			mainListView.setOnItemClickListener(this);
			if(NetUtil.isNetworkAvailable(getActivity())){
				new MeiShiAsyncTask().execute(Constants.listUrl,httpArg);
			}else{
				Toast.makeText(getActivity(), "网络不可用，请检查网络设置！", Toast.LENGTH_SHORT).show();
			}
			return view;
	}

	//listview点击事件监听
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if(NetUtil.isNetworkAvailable(getActivity())){
			Intent intent=new Intent(getActivity(),DetailActivity.class);
			intent.putExtra("ID", resultList.get(position).getId());
			startActivity(intent);
		}else{
			Toast.makeText(getActivity(), "网络不可用，请检查网络设置！", Toast.LENGTH_SHORT).show();
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
			super.onPreExecute();
			dialog = ProgressDialog.show(getContext(), "提示", "正在加载. . ."); 
		}
		
		@Override
		protected void onPostExecute(List<MeiShi> result) {
			
			super.onPostExecute(result);
			dialog.dismiss();
			resultList=result;
			mainListView.setAdapter(new MeiShiAdapter(getContext(),resultList));
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			
			super.onProgressUpdate(values);
			
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
					item.setKeywords(jsonObject.getString("keywords"));
					item.setCount(jsonObject.getString("count"));
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
