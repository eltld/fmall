package com.ngu.meishishuo.activity;

import java.util.ArrayList;
import java.util.List;

import com.ngu.meishishuo.R;
import com.ngu.meishishuo.adapter.LeftMenuAdapter;
import com.ngu.meishishuo.bean.LeftMenu;
import com.ngu.meishishuo.fragment.ClassifyFragment;
import com.ngu.meishishuo.fragment.MainFragment;
import com.ngu.meishishuo.fragment.RankFragment;
import com.ngu.meishishuo.fragment.TopicFragment;
import com.ngu.meishishuo.utils.ImageDao;
import com.ngu.meishishuo.utils.SettingsUtil;
import com.ngu.meishishuo.utils.UserInfoUtil;
import com.ngu.meishishuo.view.MyDialog;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class MainActivity extends FragmentActivity implements OnItemClickListener{
	private DrawerLayout leftDrawerLayout;
	private ListView leftListView;
	private RelativeLayout rl_head;
	private TextView tv_name;
	private ImageView iv_head;//头像
	private ActionBarDrawerToggle mDrawerToggle; //actionbar抽屉开关 
	private ActionBar actionBar;
	private RadioGroup rg_bottom;//底部标签
	private RadioButton rb_main,rb_find,rb_rank,rb_topic;//精选，发现，排行 
	private ImageDao dao;
	private MainFragment mainfragment;//精选
	private ClassifyFragment classifyfragment;//发现
	private RankFragment rankfragment;//排行
	private TopicFragment topicfragment;//话题
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initEvent();
	}
	
	private void  initView() {
		dao=new ImageDao(this);

		//底部标签
		rg_bottom=(RadioGroup) findViewById(R.id.radiogroup_bottom);
		rb_main=(RadioButton) findViewById(R.id.rb_main);
		rb_find=(RadioButton) findViewById(R.id.rb_find);
		rb_rank=(RadioButton) findViewById(R.id.rb_rank);
		rb_topic=(RadioButton) findViewById(R.id.rb_topic);
		actionBar=getActionBar();
		// 开启ActionBar上APP ICON的功能：点击打开和点击关闭drawer
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
		actionBar.setTitle("首页");
		//
		FragmentManager fragmentmanager=getSupportFragmentManager();
		FragmentTransaction beginTransaction = fragmentmanager.beginTransaction();
		mainfragment=(MainFragment) fragmentmanager.findFragmentByTag("tab1");
		if(mainfragment==null){
			mainfragment=new MainFragment();
			beginTransaction.add(R.id.main_content, mainfragment,"tab1");
			rb_main.setTextColor(getResources().getColor(R.color.actionbar_color));
			beginTransaction.commit();
		}	
		
		//
		leftDrawerLayout=(DrawerLayout) findViewById(R.id.drawerlayout);
		rl_head=(RelativeLayout)leftDrawerLayout.findViewById(R.id.rl_head);
		tv_name=(TextView) rl_head.findViewById(R.id.tv_head);
		iv_head=(ImageView) rl_head.findViewById(R.id.iv_head);
		//如果已经登录，将用户名设置为当前登录的用户名
		if(SettingsUtil.get(MainActivity.this, SettingsUtil.IS_LOGIN)){
			tv_name.setText(UserInfoUtil.getUserInfo(MainActivity.this).get(UserInfoUtil.USERNAME));
			//从数据库中读取图片数据
			if(dao.getBitmap()!=null){
				iv_head.setImageBitmap(dao.getBitmap());
			}else{
				iv_head.setImageResource(R.drawable.myhead);
			}
		}
		//左菜单数据适配
		leftListView=(ListView)leftDrawerLayout.findViewById(R.id.left_listview);
		//初始化左菜单数据
		List<LeftMenu> menuItemList=new ArrayList<LeftMenu>();
		menuItemList.add(new LeftMenu(R.drawable.menu_message, this.getString(R.string.LeftMenu_Message)));
		menuItemList.add(new LeftMenu(R.drawable.menu_collection,this.getString(R.string.LeftMenu_MyCollection)));
		menuItemList.add(new LeftMenu(R.drawable.menu_theme, this.getString(R.string.LeftMenu_Theme)));
		menuItemList.add(new LeftMenu(R.drawable.menu_setting, this.getString(R.string.LeftMenu_Settings)));
		menuItemList.add(new LeftMenu(R.drawable.menu_exit, this.getString(R.string.LeftMenu_Exit)));
		leftListView.setAdapter(new LeftMenuAdapter(this, menuItemList));

	}
	private void initEvent()
	{	
		//radiogroup标签切换
		rg_bottom.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				//
				FragmentManager fragmentmanager=getSupportFragmentManager();
				FragmentTransaction beginTransaction = fragmentmanager.beginTransaction();
				//
				
				switch (checkedId) {
				case R.id.rb_main://精选
					//设置文字颜色
					rb_main.setTextColor(getResources().getColor(R.color.actionbar_color));
					rb_find.setTextColor(getResources().getColor(R.color.main_radiobutton_textcolor));
					rb_rank.setTextColor(getResources().getColor(R.color.main_radiobutton_textcolor));
					rb_topic.setTextColor(getResources().getColor(R.color.main_radiobutton_textcolor));

					//
					mainfragment=(MainFragment) fragmentmanager.findFragmentByTag("tab1");
			
					beginTransaction.show(mainfragment);
					if(classifyfragment!=null){
						beginTransaction.hide(classifyfragment);
					}
					if(rankfragment!=null){
						beginTransaction.hide(rankfragment);
					}
					if(topicfragment!=null){
						beginTransaction.hide(topicfragment);
					}
					beginTransaction.commit();
					break;

				case R.id.rb_find://发现
					//设置文字颜色
					rb_main.setTextColor(getResources().getColor(R.color.main_radiobutton_textcolor));
					rb_find.setTextColor(getResources().getColor(R.color.actionbar_color));
					rb_rank.setTextColor(getResources().getColor(R.color.main_radiobutton_textcolor));
					rb_topic.setTextColor(getResources().getColor(R.color.main_radiobutton_textcolor));

					classifyfragment=(ClassifyFragment) fragmentmanager.findFragmentByTag("tab2");
					if(classifyfragment==null){
						classifyfragment=new ClassifyFragment();
						beginTransaction.add(R.id.main_content, classifyfragment,"tab2");
						
					}
					//
					beginTransaction.show(classifyfragment);
					if(mainfragment!=null){
						beginTransaction.hide(mainfragment);
					}
					if(rankfragment!=null){
						beginTransaction.hide(rankfragment);
					}
					if(topicfragment!=null){
						beginTransaction.hide(topicfragment);
					}
					beginTransaction.commit();
					break;
				case R.id.rb_rank://排行
					//设置文字颜色
					rb_main.setTextColor(getResources().getColor(R.color.main_radiobutton_textcolor));
					rb_find.setTextColor(getResources().getColor(R.color.main_radiobutton_textcolor));
					rb_rank.setTextColor(getResources().getColor(R.color.actionbar_color));
					rb_topic.setTextColor(getResources().getColor(R.color.main_radiobutton_textcolor));
					
					//
					rankfragment=(RankFragment) fragmentmanager.findFragmentByTag("tab3");
					if(rankfragment==null){
						rankfragment=new RankFragment();
						beginTransaction.add(R.id.main_content, rankfragment,"tab3");
						
					}
					//显示rankfragment
					beginTransaction.show(rankfragment);
					if(mainfragment!=null){
						beginTransaction.hide(mainfragment);
					}
					if(classifyfragment!=null){
						beginTransaction.hide(classifyfragment);
					}
					if(topicfragment!=null){
						beginTransaction.hide(topicfragment);
					}
					beginTransaction.commit();
					break;
				case R.id.rb_topic://话题
					//设置文字颜色
					rb_main.setTextColor(getResources().getColor(R.color.main_radiobutton_textcolor));
					rb_find.setTextColor(getResources().getColor(R.color.main_radiobutton_textcolor));
					rb_rank.setTextColor(getResources().getColor(R.color.main_radiobutton_textcolor));
					rb_topic.setTextColor(getResources().getColor(R.color.actionbar_color));
					//
					topicfragment=(TopicFragment) fragmentmanager.findFragmentByTag("tab4");
					if(topicfragment==null){
						topicfragment=new TopicFragment();
						beginTransaction.add(R.id.main_content, topicfragment,"tab4");
						
					}
					//显示topicfragment
					beginTransaction.show(topicfragment);
					if(mainfragment!=null){
						beginTransaction.hide(mainfragment);
					}
					if(classifyfragment!=null){
						beginTransaction.hide(classifyfragment);
					}
					if(rankfragment!=null){
						beginTransaction.hide(rankfragment);
					}
					beginTransaction.commit();
					break;
				default:
					break;
				}
				
			}
		});
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
		if(requestCode==1){//从LoginActivity返回
			if(resultCode==1){//设置有返回结果
				if(data!=null){
					String username=data.getStringExtra("username");
					tv_name.setText(username);
					if(username.equals("请登录")){//设置头像时判断，退出登录
						iv_head.setImageResource(R.drawable.menu_head);
					}else{//登录
						//从数据库中读取图片数据
						if(dao.getBitmap()!=null){
							iv_head.setImageBitmap(dao.getBitmap());
						}else{
							//设置默认头像
							iv_head.setImageResource(R.drawable.myhead);
						}
					}
				}
			}else{//没有返回结果
				if(SettingsUtil.get(MainActivity.this, SettingsUtil.IS_LOGIN))
				{	//从数据库中读取图片数据
					if(dao.getBitmap()!=null){
						iv_head.setImageBitmap(dao.getBitmap());
					}else{
						//设置默认头像
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
        		Intent intent0=new Intent(MainActivity.this,SearchActivity.class);
        		startActivity(intent0);
        		break;
        	case R.id.menu_add:
        		//上传菜谱
        		Intent intent1=new Intent(MainActivity.this,AddMeiShiActivity.class);
        		startActivity(intent1);
        		break;
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
				leftDrawerLayout.closeDrawer(Gravity.START);
				startActivity(intent0);
				
			break;
			case 1://我的收藏
				Intent intent1=new Intent(MainActivity.this,CollectionActivity.class);
				leftDrawerLayout.closeDrawer(Gravity.START);
				startActivity(intent1);
				
				break;
			case 2://主题换肤
				Toast.makeText(MainActivity.this, "敬请期待. . .",Toast.LENGTH_SHORT).show();
				leftDrawerLayout.closeDrawer(Gravity.START);
				break;
			case 3://设置
				Intent intent3=new Intent(MainActivity.this,SettingsActivity.class);
				leftDrawerLayout.closeDrawer(Gravity.START);
				startActivity(intent3);
				
				break;
			case 4://退出
				showExitDialog();
				break;
		}
	
	}
	//物理按键点击事件
	public boolean onKeyDown(int keyCode,KeyEvent event){
				
		
		if(keyCode==KeyEvent.KEYCODE_BACK) {//back
			if(leftDrawerLayout.isDrawerOpen(Gravity.START)) {
				leftDrawerLayout.closeDrawer(Gravity.START);
				return true;
			}else{
				 moveTaskToBack(true); //后台运行 
		         return true;  
			}
				
		}
		return super.onKeyDown(keyCode, event);
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