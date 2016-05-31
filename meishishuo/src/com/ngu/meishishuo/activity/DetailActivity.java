package com.ngu.meishishuo.activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.ngu.meishishuo.R;
import com.ngu.meishishuo.adapter.CommentAdapter;
import com.ngu.meishishuo.model.Collection;
import com.ngu.meishishuo.model.Comment;
import com.ngu.meishishuo.model.MeiShi;
import com.ngu.meishishuo.utils.MeiShiDao;
import com.ngu.meishishuo.utils.AllUrl;
import com.ngu.meishishuo.utils.NetUtil;
import com.ngu.meishishuo.utils.SettingsUtil;
import com.ngu.meishishuo.utils.UserInfoUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author zhoufeng06@qq.com
 * @time 2016年4月22日-下午2:27:36
 */
public class DetailActivity extends Activity implements OnItemClickListener{
	private ActionBar actionBar;
	private ViewPager viewpager;//包含详情和评论
	private WebView mWebView;//加载详情内容
	private ListView commentListView;//评论
	private RadioGroup rg_mid;
	private RadioButton rb_detail,rb_comment;//详情和评论标签
	private ImageView meishiImage,send;
	private List<View> viewList ;
	private List<Comment> commentList;
	private CommentAdapter commentAdapter;
	private EditText et_comment;
	private TextView tv_collect;//收藏
	private MeiShiDao dao;//数据库访问对象
	private MeiShi meishi;
	private DisplayImageOptions options;//imageloader选项
	private boolean noImage=false;//无图模式
	private String httpArg="id=";//请求参数
	private String id=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.loading)
				.showImageForEmptyUri(R.drawable.loading)
				.showImageOnFail(R.drawable.loading)
				.cacheInMemory(true)
				.cacheOnDisk(false)
				.considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();
		noImage=SettingsUtil.get(DetailActivity.this, SettingsUtil.NO_IMAGE);
		Intent intent = getIntent();
		id=intent.getStringExtra("ID");
		httpArg+=id;
		initView();
		if(NetUtil.isNetworkAvailable(DetailActivity.this)){
			//网络可用，则建立异步任务加载数据
			new DetailAsyncTask().execute(AllUrl.showUrl,httpArg);
		}else{
			Toast.makeText(DetailActivity.this, "网络不可用！", Toast.LENGTH_SHORT).show();
		}
	}
	@SuppressWarnings("deprecation")
	public void initView(){
		dao=new MeiShiDao(DetailActivity.this);
		actionBar=getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		//不显示图标
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setTitle("详情");
		//
		viewpager=(ViewPager) findViewById(R.id.viewpager_content);
		meishiImage=(ImageView) findViewById(R.id.detail_imageView_meishi);
		rg_mid=(RadioGroup) findViewById(R.id.radiogroup_mid);
		rb_detail=(RadioButton) findViewById(R.id.rb_detail);
		rb_comment=(RadioButton) findViewById(R.id.rb_comment);
		tv_collect=(TextView) findViewById(R.id.detail_btn_collect);
		//详情view
		LayoutInflater inflater=getLayoutInflater();
		View detail_webview=inflater.inflate(R.layout.detail_webview, null);
		mWebView=(WebView)detail_webview.findViewById(R.id.detail_webview);
		//评论view
		View detail_comment=inflater.inflate(R.layout.detail_comment, null);
		commentListView=(ListView) detail_comment.findViewById(R.id.listview_comment);
		send=(ImageView) detail_comment.findViewById(R.id.imageview_comment_send);
		et_comment=(EditText) detail_comment.findViewById(R.id.edittext_comment);
		//评论列表
		commentList=dao.queryAllComment();
		commentAdapter=new CommentAdapter(DetailActivity.this, commentList);
		commentListView.setAdapter(commentAdapter);
		commentListView.setOnItemClickListener(this);
			final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			//评论按钮点击事件监听
			send.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//未登录则不能评论
					if(!SettingsUtil.get(DetailActivity.this, SettingsUtil.IS_LOGIN)){
						et_comment.setText("请登录后评论");
					}else{
					String content =et_comment.getText().toString();
					if(!TextUtils.isEmpty(content)){
						Comment com=new Comment();
						com.setName(UserInfoUtil.getUserInfo(DetailActivity.this).get(UserInfoUtil.USERNAME));
						com.setTime(sdf.format(new Date()).toString());
						com.setContent(content);
						commentList.add(com);
						dao.insertToComment(com);
						commentAdapter.notifyDataSetChanged();
						et_comment.setText("");
					}else{
						Toast.makeText(DetailActivity.this, "输入内容不能为空！", Toast.LENGTH_SHORT).show();
					}
					}
				}
			});
		//
		viewList = new ArrayList<View>();
		viewList.add(detail_webview);
		viewList.add(detail_comment);
		viewpager.setAdapter(new PagerAdapter() {
			
			@Override
			public Object instantiateItem(View container, int position) {
				// 
				((ViewPager) container).addView(viewList.get(position));
				return viewList.get(position);
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// 
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				//
				return viewList.size();
			}
		});
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// 
				 switch (arg0) {
			         case 0:
			             rb_detail.setChecked(true);
			             break;
			         case 1:
			        	 rb_comment.setChecked(true);
			             break;		       
			         default:
			             break;
				 }
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// 
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// 
				
			}
		});
		rg_mid.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// 
				 switch (checkedId) {
		         case R.id.rb_detail:
		             viewpager.setCurrentItem(0);
		             break;
		        case R.id.rb_comment:
		             viewpager.setCurrentItem(1);
		             break;
		    
		        default:
		             break;
				
				 }
			}
		});
		tv_collect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 
				Collection colle=new Collection();
		 		colle.setName(meishi.getName());
		 		colle.setDescription(meishi.getDescription());
		 		colle.setId(meishi.getId());
		 		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		 		colle.setTime(sdf.format(new Date()).toString());
		 		dao.insertToCollection(colle);
		 		Toast.makeText(DetailActivity.this,"\""+colle.getName()+"\"已收藏", Toast.LENGTH_SHORT).show();
		 		
			}
		});
	}
	//评论列表点击操作
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		//
		
		new  AlertDialog.Builder(DetailActivity.this)                   
		.setItems(new  String[] {"回复", "举报" }, 
		  new  DialogInterface.OnClickListener() {  
			public void onClick(DialogInterface dialog, int which) {
				if(which==0){
					Toast.makeText(DetailActivity.this, "敬请期待. . .", Toast.LENGTH_SHORT).show();
				}else if(which==1){
					Toast.makeText(DetailActivity.this, "敬请期待. . .", Toast.LENGTH_SHORT).show();
				}
			}
		}	
		).show();

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// 
		switch (item.getItemId()) {
	 	case android.R.id.home://点击左上角图标返回
	 		finish();
	 		break;
	 	case R.id.menu_share://分享
	 		Intent intent = new Intent(Intent.ACTION_SEND); // 启动分享发送的属性
	 		intent.setType("text/plain"); // 分享发送的数据类型
	 		String msg = actionBar.getTitle().toString();
	 		intent.putExtra(Intent.EXTRA_TEXT, msg); // 分享的内容
	 		startActivity(Intent.createChooser(intent, "选择分享"));// 目标应用选择对话框的标题
        }
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//创建选项菜单 
		MenuInflater inflater = getMenuInflater();  
 	    inflater.inflate(R.menu.menu_detail, menu);
		return super.onCreateOptionsMenu(menu);
	}
	/*
	 * 异步任务获取message,name
	 */
	public class DetailAsyncTask extends AsyncTask<String, Void, MeiShi> {

		@Override
		protected MeiShi doInBackground(String... params) {
			return request(params[0],params[1]);
		}

		@Override
		protected void onPostExecute(MeiShi result) {
			//更新ui
			super.onPostExecute(result);
			meishi=result;
			actionBar.setTitle(result.getName());
			//优先加载缓存
			mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
			mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
			mWebView.loadData(result.getMessage(), "text/html; charset=UTF-8", null);//这种写法可以正确解码
			//加载图片
			if(!noImage){
				ImageLoader.getInstance()
				.displayImage(AllUrl.imageUrl+result.getImg()+AllUrl.imageSize,meishiImage, options, new SimpleImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {
						
					}
	
					@Override
					public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
						
					}
	
					@Override
					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
						
					}
				}, new ImageLoadingProgressListener() {
					@Override
					public void onProgressUpdate(String imageUri, View view, int current, int total) {
						
					}
				});
			}
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			
			super.onProgressUpdate(values);
		}
		/**
		 * 获取json数据
		 * @param urlAll
		 *            :请求接口
		 * @param httpArg
		 *            :参数
		 * @return 返回结果
		 */
		private MeiShi request(String httpUrl,String httpArg)
		{
			BufferedReader reader = null;
		    String result = null;
		    MeiShi item=new MeiShi();
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
		    	try{
					JSONObject jsonObject=new JSONObject(result);
					item.setName(jsonObject.getString("name"));
					item.setId(jsonObject.getString("id"));
					item.setDescription(jsonObject.getString("description"));
					item.setImg(jsonObject.getString("img"));
					item.setMessage(jsonObject.getString("message"));
		    	}catch(Exception e){
		    		e.printStackTrace();
		    	}
		    return item;
		}
	}
	
}
