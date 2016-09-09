package com.ngu.meishishuo.adapter;

import java.util.List;

import com.ngu.meishishuo.R;
import com.ngu.meishishuo.bean.MeiShi;
import com.ngu.meishishuo.utils.AllUrl;
import com.ngu.meishishuo.utils.SettingsUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;

//gridview 数据适配器
public  class MainGridViewAdapter extends BaseAdapter{
		private List<MeiShi> list;
		private Context context;
		private DisplayImageOptions options;
		private boolean noImage=false;//无图模式
		
		public MainGridViewAdapter(Context context,List<MeiShi> list){
			this.list=list;
			this.context=context;
			noImage=SettingsUtil.get(context, SettingsUtil.NO_IMAGE);
			options = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.loading)
					.showImageForEmptyUri(R.drawable.loading)
					.showImageOnFail(R.drawable.loading)
					.cacheInMemory(true)
					.cacheOnDisk(false)
					.considerExifParams(true)
					.bitmapConfig(Bitmap.Config.RGB_565)
					.build();
		}
		@Override
		public int getCount() {
			// 
			if(null!=list)
			{
				return list.size();
			}
			else{
				return 0;
			}
		}
	
		@Override
		public Object getItem(int position) {
			// 
			if(list!=null)
			{
				return list.get(position);
			}
			else{
				return null;
			}
		}
	
		@Override
		public long getItemId(int position) {
			// 
			return position;
		}
	
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 
			ViewHolder hold;
			if (convertView == null) {
				hold = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.fragment_main_item, null);
				convertView.setTag(hold);
			}else {
				hold=(ViewHolder) convertView.getTag();
			}
			hold.imageView=(ImageView) convertView.findViewById(R.id.item_main_imageview);
			hold.textView=(TextView) convertView.findViewById(R.id.item_main_textview);
			//异步加载图片
			String url=AllUrl.imageUrl+list.get(position).getImg()+AllUrl.imageSize;
			if(!noImage){
				ImageLoader.getInstance()
				.displayImage(url, hold.imageView, options, new SimpleImageLoadingListener() {
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
			hold.textView.setText(list.get(position).getName());
		
			return convertView;
	}
		private class ViewHolder{
			public ImageView imageView;
			public TextView textView;
		}
	}