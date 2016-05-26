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
import com.ngu.meishishuo.adapter.ClassifyAdapter;
import com.ngu.meishishuo.model.MeiShi;
import com.ngu.meishishuo.utils.Constants;
import com.ngu.meishishuo.utils.MeiShiDao;
import com.ngu.meishishuo.utils.NetUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ClassifyFragment extends Fragment implements OnItemClickListener {
	private GridView mGridView;
	private MeiShiDao dao;
	private List<MeiShi> classifyList;
	private String httpArg="id=0";//请求参数
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// 
		super.onCreate(savedInstanceState);
		dao=new MeiShiDao(getActivity(), MeiShiDao.DATABASE_NAME);
		classifyList=dao.queryAll(MeiShiDao.CLASSIFY_TABLE);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// 
		View view =inflater.inflate(R.layout.fragment_classify, container,false);
		mGridView=(GridView) view.findViewById(R.id.classify_gv);
		//设置点击事件监听
		mGridView.setOnItemClickListener(this);
		if(classifyList.isEmpty()){//本地为空则从网络加载
			if(NetUtil.isNetworkAvailable(getActivity())){
				//网络可用，则建立异步任务加载数据
				new ClassifyAsyncTask().execute(Constants.classifyUrl,httpArg);
			}else{
				Toast.makeText(getActivity(), "网络不可用，请检查网络设置！", Toast.LENGTH_SHORT).show();
			}
		}else{
			mGridView.setAdapter(new ClassifyAdapter(getActivity(),classifyList));
		}
		return view;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		//分类列表点击事件监听
		if(NetUtil.isNetworkAvailable(getActivity())){
			FragmentTransaction beginTransaction=getFragmentManager().beginTransaction();
			MeiShiFragment fragment=new MeiShiFragment(classifyList.get(position).getId(),classifyList.get(position).getName());
			beginTransaction.replace(R.id.login_container, fragment).commit();
		}else{
			Toast.makeText(getActivity(), "网络不可用，请检查网络设置！", Toast.LENGTH_SHORT).show();
		}
	}
	/*
	 * 异步任务获取分类列表
	 */
	public class ClassifyAsyncTask extends AsyncTask<String,Void,List<MeiShi> >{

		@Override
		protected List<MeiShi> doInBackground(String... params) {
			
				return request(params[0],params[1]);
		}

		@Override
		protected void onPostExecute(List<MeiShi> result) {
			
			classifyList=result;
			//保存到本地
			for(int i=0;i<result.size();i++)
			{
				dao.insert(result.get(i), MeiShiDao.CLASSIFY_TABLE);
			}
			mGridView.setAdapter(new ClassifyAdapter(getActivity(),result));
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			
			super.onProgressUpdate(values);
		}
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
				item.setId(jsonObject.getString("id"));
				resultList.add(item);
			}
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    return resultList;
	}
}
