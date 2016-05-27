package com.ngu.meishishuo.activity;

import java.util.List;

import com.ngu.meishishuo.R;
import com.ngu.meishishuo.adapter.CollectionAdapter;
import com.ngu.meishishuo.model.MeiShi;
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
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author zhoufeng06@qq.com
 * @time 2016年4月27日-下午3:43:45
 */
public class CollectionActivity extends Activity {
	
	private ActionBar actionBar;
	private List<MeiShi> list;
	private MeiShiDao dao;
	private ListView mListView;
	private CollectionAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection);
		initView();
		dao=new MeiShiDao(getApplicationContext(),MeiShiDao.DATABASE_NAME);
		list=dao.queryAll(MeiShiDao.COLLECTION_TABLE);
		adapter = new CollectionAdapter(list,CollectionActivity.this,dao);
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
		
	}
	private void initEvent(){
		mListView.setOnItemClickListener(new MyOnItemClickListener());
		//多选模式
		mListView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
			
			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				// 
				return false;
			}
			
			@Override
			public void onDestroyActionMode(ActionMode mode) {
				// 
				
			}
			
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				// 
				MenuInflater inflater=mode.getMenuInflater();
				inflater.inflate(R.menu.menu_collection, menu);
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
				
			}
		});
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
}
