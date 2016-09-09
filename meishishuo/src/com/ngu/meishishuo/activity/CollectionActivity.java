package com.ngu.meishishuo.activity;

import java.util.List;

import com.ngu.meishishuo.R;
import com.ngu.meishishuo.adapter.CollectionAdapter;
import com.ngu.meishishuo.bean.Collection;
import com.ngu.meishishuo.utils.MeiShiDao;
import com.ngu.meishishuo.utils.NetUtil;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;

/**
 * @author zhoufeng06@qq.com
 * @time 2016年4月27日-下午3:43:45
 */
public class CollectionActivity extends Activity {
	
	private ActionBar actionBar;
	private List<Collection> list;
	private MeiShiDao dao;
	private ListView mListView;
	private CollectionAdapter adapter;
	private RelativeLayout rl_info;//
	private LinearLayout ll_all;// 
	private CheckBox cb;//全选
	private TextView tv_info;// 
	private int sum=0;//选中数目
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection);
		initView();
		dao=new MeiShiDao(CollectionActivity.this);
		list=dao.queryAllCollection();
		adapter = new CollectionAdapter(list,CollectionActivity.this);
		mListView.setAdapter(adapter);
		initEvent();
	}
	private void initView(){
		actionBar=getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		//不显示图标
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setTitle("我的收藏");
		mListView=(ListView)findViewById(R.id.id_collection_lv);
		rl_info=(RelativeLayout) findViewById(R.id.collection_rl_info);
		ll_all=(LinearLayout) findViewById(R.id.collection_ll_chooseall);
		cb=(CheckBox) findViewById(R.id.chk_chooseall);
		tv_info=(TextView) findViewById(R.id.collection_textview_info);
		
	}
	private void initEvent(){
		final myModeListener mListener=new myModeListener();
		mListView.setOnItemClickListener(new MyOnItemClickListener());
		//全选
		ll_all.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 
				if(cb.isChecked()){
					cb.setChecked(false);
				}else{
					cb.setChecked(true);
				}
			}
		});
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//
				if(isChecked){ 
					mListener.selectAll();
				}else{
					mListener.unSelectAll();
				}
			}
		});
		//多选模式
		mListView.setMultiChoiceModeListener(mListener);
	}
	
	//listview的item点击事件
	private class MyOnItemClickListener implements OnItemClickListener
	{
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if(NetUtil.isNetworkAvailable(CollectionActivity.this)){
				Intent intent=new Intent(CollectionActivity.this,DetailActivity.class);
				intent.putExtra("ID", list.get(position).getId());
				startActivity(intent);
			}else{
				Toast.makeText(CollectionActivity.this, "网络不可用，请检查网络设置！", Toast.LENGTH_SHORT).show();
			}
			
		}
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 
		return super.onCreateOptionsMenu(menu);
	}
	/**
	 * @author zhoufeng06@qq.com
	 * @time 2016年5月31日-下午9:35:52
	 * actionmode监听
	 */
	private class myModeListener implements MultiChoiceModeListener{
		
		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			// 
			return false;
		}
		
		@Override
		public void onDestroyActionMode(ActionMode mode) {
			//
			sum=0;
			rl_info.setVisibility(View.GONE);
		}
		
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			// 
			MenuInflater inflater=mode.getMenuInflater();
			inflater.inflate(R.menu.menu_collection, menu);
			rl_info.setVisibility(View.VISIBLE);
			return true;
		}
		
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			// 
			switch(item.getItemId()){
				case R.id.menu_delete:
					for(int i=adapter.getCount()-1;i>=0;i--)
					{
						if(mListView.isItemChecked(i)){
							dao.delete(list.get(i).get_id(),MeiShiDao.COLLECTION_TABLE);
							list.remove(i);
						}
					}
					mode.finish();
					adapter.notifyDataSetChanged();
					
					return true;
				default:
					return true;
			}
		}
		
		@Override
		public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
			// 
			if(checked){
				sum+=1;
			}else{
				sum-=1;
			}
			tv_info.setText("已选中"+sum+"项");
		}
		/**
		 * 全选item
		 */
		private void selectAll(){
			for(int i= 0; i< adapter.getCount(); i++){
				if(!mListView.isItemChecked(i))
					mListView.setItemChecked(i, true);
					
            }
		}
		/**
		 * 全不选
		 */
		private void unSelectAll(){
			for(int i= 0; i< adapter.getCount(); i++){
				if(mListView.isItemChecked(i))
					mListView.setItemChecked(i, false);
					
            }
		}
	}
}
