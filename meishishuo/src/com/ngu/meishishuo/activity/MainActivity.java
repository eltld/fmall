package com.ngu.meishishuo.activity;

import java.util.ArrayList;
import java.util.List;

import com.ngu.meishishuo.R;
import com.ngu.meishishuo.adapter.LeftMenuAdapter;
import com.ngu.meishishuo.customview.MyDialog;
import com.ngu.meishishuo.fragment.MainFragment;
import com.ngu.meishishuo.fragment.MeiShiFragment;
import com.ngu.meishishuo.fragment.NetErrorFragment;
import com.ngu.meishishuo.model.LeftMenu;
import com.ngu.meishishuo.utils.MeiShiDao;
import com.ngu.meishishuo.utils.NetUtil;
import com.ngu.meishishuo.utils.SettingsUtil;
import com.ngu.meishishuo.utils.UserInfoUtil;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnItemClickListener{
	private DrawerLayout leftDrawerLayout;
	private ListView leftListView;
	private RelativeLayout rl_head;
	private TextView tv_name;
	private ImageView iv_head;//头像
	ActionBarDrawerToggle mDrawerToggle; //actionbar抽屉开关 
	private ActionBar actionBar;
	private SearchView searchView; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initEvent();
	}
	
	private void  initView() {
		actionBar=getActionBar();
		// 开启ActionBar上APP ICON的功能：点击打开和点击关闭drawer
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("首页");
		FragmentTransaction beginTransaction=getSupportFragmentManager().beginTransaction();
		if(NetUtil.isNetworkAvailable(MainActivity.this)){
			MainFragment fragment=new MainFragment();
			beginTransaction.replace(R.id.main_container, fragment).commit();
		}else{
			NetErrorFragment fragment=new NetErrorFragment();
			beginTransaction.replace(R.id.main_container, fragment).commit();
		}
		leftDrawerLayout=(DrawerLayout) findViewById(R.id.drawerlayout);
		rl_head=(RelativeLayout)leftDrawerLayout.findViewById(R.id.rl_head);
		tv_name=(TextView) rl_head.findViewById(R.id.tv_head);
		iv_head=(ImageView) rl_head.findViewById(R.id.iv_head);
		//如果已经登录，将用户名设置为当前登录的用户名
		if(SettingsUtil.get(MainActivity.this, SettingsUtil.IS_LOGIN)){
			tv_name.setText(UserInfoUtil.getUserInfo(MainActivity.this).get(UserInfoUtil.USERNAME));
			iv_head.setImageResource(R.drawable.myhead);
		}
		//左菜单数据适配
		leftListView=(ListView)leftDrawerLayout.findViewById(R.id.left_listview);
		//初始化左菜单数据
		List<LeftMenu> menuItemList=new ArrayList<LeftMenu>();
		menuItemList.add(new LeftMenu(R.drawable.menu_message, this.getString(R.string.LeftMenu_Message)));
		menuItemList.add(new LeftMenu(R.drawable.menu_classify, this.getString(R.string.LeftMenu_Classify)));
		menuItemList.add(new LeftMenu(R.drawable.menu_collection,this.getString(R.string.LeftMenu_MyCollection)));
		menuItemList.add(new LeftMenu(R.drawable.menu_theme, this.getString(R.string.LeftMenu_Theme)));
		menuItemList.add(new LeftMenu(R.drawable.menu_setting, this.getString(R.string.LeftMenu_Settings)));
		menuItemList.add(new LeftMenu(R.drawable.menu_exit, this.getString(R.string.LeftMenu_Exit)));
		leftListView.setAdapter(new LeftMenuAdapter(this, menuItemList));

	}
	private void initEvent()
	{	
		//左菜单登录点击事件
		rl_head.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 
				Intent intent=new Intent(MainActivity.this,LoginActivity.class);
				startActivityForResult(intent, 1);//requestCode=1;
			}
		});
		//
		//actionbar设置drawer的开关监听 
		mDrawerToggle = new ActionBarDrawerToggle(this, 
		        								leftDrawerLayout,
		        								R.drawable.ic_drawer,
		        								R.string.drawer_open,
		        								R.string.drawer_close)
		{
			public void onDrawerClosed(View view) {
				//抽屉完全关闭时调用
				super.onDrawerClosed(view);
				actionBar.setTitle("首页");
			}
			public void onDrawerOpened(View drawerView) {
				//抽屉完全打开时调用
				super.onDrawerOpened(drawerView);
				actionBar.setTitle("菜单");
			}
		};
		leftDrawerLayout.setDrawerListener(mDrawerToggle); 
		leftListView.setOnItemClickListener(this);
	}
	//接收loginActivity返回的数据
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, requestCode, data);
		if(data!=null){
			if(resultCode==1){
				if(requestCode==1){
					String username=data.getStringExtra("username");
					tv_name.setText(username);
					if(username.equals("请登录")){
						iv_head.setImageResource(R.drawable.menu_head);
					}else{
					iv_head.setImageResource(R.drawable.myhead);
					}
				}
			}
		}
	};
	//activity创建完成以后 
	//该方法会自动和actionBar关联, 将开关的图片显示在了action上，如果不设置，也可以有抽屉的效果，不过是默认的图标  
    @Override  
    protected void onPostCreate(Bundle savedInstanceState) {  
        super.onPostCreate(savedInstanceState);  
        mDrawerToggle.syncState();
    }  
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();  
 	    inflater.inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}
    //处理抽屉的打开关闭效果
    @Override  
    public boolean onOptionsItemSelected(MenuItem item) {  
        if (mDrawerToggle.onOptionsItemSelected(item)) {
          return true;
        }
        switch(item.getItemId()){
        	case R.id.menu_search:
        		//搜索
        		Intent intent=new Intent(MainActivity.this,SearchActivity.class);
        		startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }  

    //抽屉菜单点击事件监听
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// 
		switch(position){
			case 0://我的消息
				Intent intent0=new Intent(MainActivity.this,MessageActivity.class);
				startActivity(intent0);
			break;
			case 1://分类
				Intent intent1=new Intent(MainActivity.this,ClassifyActivity.class);
				startActivity(intent1);
				break;
				
			case 2://我的收藏
				Intent intent2=new Intent(MainActivity.this,CollectionActivity.class);
				startActivity(intent2);
				break;
			case 3://主题换肤
				Toast.makeText(MainActivity.this, "敬请期待. . .",Toast.LENGTH_SHORT).show();
				break;
			case 4://设置
				Intent intent4=new Intent(MainActivity.this,SettingsActivity.class);
				startActivity(intent4);
				break;
			case 5://退出
				showExitDialog();
				break;
		}
	
	}
	//物理按键点击事件
	public boolean onKeyDown(int keyCode,KeyEvent event){
				
		if (keyCode == KeyEvent.KEYCODE_MENU) {//menu
			if(leftDrawerLayout.isDrawerOpen(Gravity.START)) {
				leftDrawerLayout.closeDrawer(Gravity.START);
			}
			else{
				leftDrawerLayout.openDrawer(Gravity.START);
			}
		}
		else if(keyCode==KeyEvent.KEYCODE_BACK) {//back
			if(leftDrawerLayout.isDrawerOpen(Gravity.START)) {
				leftDrawerLayout.closeDrawer(Gravity.START);
			}
			else{
				showExitDialog();
			}
				
		}
		return true;
	}
	//显示退出对话框
	public void showExitDialog(){
		MyDialog.Builder builder = new MyDialog.Builder(MainActivity.this);
		builder.setMessage("确定要退出吗？");
		builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				finish();
				
			}
		});

		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();
	}
}