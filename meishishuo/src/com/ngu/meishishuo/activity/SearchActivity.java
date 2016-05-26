package com.ngu.meishishuo.activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ngu.meishishuo.R;
import com.ngu.meishishuo.adapter.SearchAdapter;
import com.ngu.meishishuo.model.MeiShi;
import com.ngu.meishishuo.utils.Constants;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends Activity {
	private ActionBar actionBar;
	private ListView listview;
	private TextView tv_info;
	private EditText edittext;
	private ImageView button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initView();
	}
	private void initView(){
		actionBar=getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("搜索");
		listview=(ListView) findViewById(R.id.search_listview);
		tv_info=(TextView) findViewById(R.id.search_info);
		edittext=(EditText) findViewById(R.id.search_edittext);
		button=(ImageView) findViewById(R.id.search_button);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//
				String query=edittext.getText().toString().trim();
				//必须添加编码,否则http请求会得不到结果
				String httpArg="name="+URLEncoder.encode(query);
				
				if(!TextUtils.isEmpty(query)){
					new MeiShiAsyncTask().execute(Constants.nameUrl,httpArg);
				}else{
					Toast.makeText(SearchActivity.this, "搜索内容不能为空！", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// 
		switch (item.getItemId()) {
	 	case android.R.id.home://点击左上角图标返回
	 		finish();
	 		break;
	 		
        }
		return super.onOptionsItemSelected(item);
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
			dialog = ProgressDialog.show(SearchActivity.this, "提示", "正在加载. . .");
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(List<MeiShi> result) {
			super.onPostExecute(result);
			dialog.dismiss();
			tv_info.setText("共有"+result.size()+"个结果");
			tv_info.setVisibility(View.VISIBLE);
			listview.setAdapter(new SearchAdapter(result,SearchActivity.this));
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
		    httpUrl=httpUrl+"?"+httpArg;
		    StringBuffer sbf = new StringBuffer();
		    try {
		        URL url = new URL(httpUrl);
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
					//item.setImg(jsonObject.getString("img"));
					//item.setKeywords(jsonObject.getString("keywords"));
					item.setCount(jsonObject.getString("count"));
					item.setFood(jsonObject.getString("food"));
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
