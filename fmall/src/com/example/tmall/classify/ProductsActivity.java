package com.example.tmall.classify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.tmall.R;
import com.example.tmall.R.id;
import com.example.tmall.R.layout;
import com.example.tmall.bean.Product;
import com.example.tmall.constant.HttpUrl;
import com.example.tmall.http.OkHttpUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.Request;
import okhttp3.Response;
/*
 * 商品列表
 */
public class ProductsActivity extends Activity implements OnClickListener{
	
	private TextView tv_title;
	private TextView back;
	private ProgressBar progress;
	
	private SwipeRefreshLayout refreshLayout;
	private RecyclerView recyclerView;
	private ProductsAdapter mAdapter;
	private List<Product> productsList=new ArrayList<Product>();
	//
	private String classify=null;
	private String sql_query=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.products_activity);
		Intent intent=getIntent();
		classify=intent.getStringExtra(ClassifyFragment.CLASSIFY);
		sql_query="?sql=SELECT * FROM shangpin WHERE shangpin.lb = '"+classify+"'";
		initView();
	}
	private void initView() {
		progress=(ProgressBar) findViewById(R.id.loading_list_progress);
		tv_title=(TextView)findViewById(R.id.tv_top_title);
		tv_title.setText(classify);
		back=(TextView) findViewById(R.id.iv_top_back);
		back.setOnClickListener(this);
		refreshLayout=(SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
		refreshLayout.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				refreshLayout.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						refreshLayout.setRefreshing(false);
						Toast.makeText(ProductsActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
					}
				}, 2000);
				
			}
		});
		recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
		 recyclerView.setHasFixedSize(true);
		//设置layoutManager
		GridLayoutManager  layoutManager=new GridLayoutManager(ProductsActivity.this,2);
        recyclerView.setLayoutManager(layoutManager);
        //
      	mAdapter=new ProductsAdapter(productsList, ProductsActivity.this);
      	recyclerView.setAdapter(mAdapter);
        //
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        //
        recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.addOnItemTouchListener(new RecyclerTouchListener(ProductsActivity.this, recyclerView, new ClickListener() {
			
			@Override
			public void onLongClick(View view, int position) {
				
			}
			
			@Override
			public void onClick(View view, int position) {
				// 
				Intent intent=new Intent(ProductsActivity.this,DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("product", productsList.get(position));
				intent.putExtras(bundle);
				startActivity(intent);
				
			}
		}));
		
		new ProductListTask().execute(HttpUrl.LIST_URL+sql_query);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_top_back://返回
			finish();
			break;

		default:
			break;
		}
	}
	
	private class ProductListTask extends AsyncTask<String,Integer,List<Product>>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progress.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onPostExecute(List<Product> result) {
			super.onPostExecute(result);
			mAdapter.notifyDataSetChanged();
			progress.setProgress(100);
			progress.setVisibility(View.INVISIBLE);
			
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			progress.setProgress(values[0]);
		}

		@Override
		protected List<Product> doInBackground(String... params) {
			String result="";
			Request request=new Request.Builder()
	                .get()
	                .tag(this)
	                .url(params[0])
	                .build();
			Response response = null;
			try {
				response = OkHttpUtil.getOkHttpClient().newCall(request).execute();
				if (response.isSuccessful()) {
					result=response.body().string();
				} else {
					throw new IOException("Unexpected code " + response);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			publishProgress(40);
			try {
				Product product ;
				JSONArray jsonArray = new JSONArray(result);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					product = new Product();
					product.setId(jsonObject.getInt("spID"));
					product.setProClassify(jsonObject.getString("lb"));
					product.setProImg(jsonObject.getString("picpath"));
					product.setProMsg(jsonObject.getString("spms"));
					product.setProName(jsonObject.getString("spname"));
					product.setProPrice(jsonObject.getDouble("spjg"));
					product.setProStock(jsonObject.getInt("spkc"));
					productsList.add(product);
					publishProgress(i);
				}
				publishProgress(80);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return productsList;
		}
	}

}