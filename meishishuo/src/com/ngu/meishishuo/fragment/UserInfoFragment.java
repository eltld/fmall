package com.ngu.meishishuo.fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;

import com.ngu.meishishuo.R;
import com.ngu.meishishuo.activity.MainActivity;
import com.ngu.meishishuo.utils.SettingsUtil;
import com.ngu.meishishuo.utils.UserInfoUtil;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class UserInfoFragment extends Fragment {
	private ActionBar actionBar;
	private RelativeLayout head; 
	private ImageView headImage;//头像
	private RelativeLayout name;//昵称
	private RelativeLayout mail;//邮箱
	private RelativeLayout signture;//个性签名
	private Button logout;//退出登录
	private byte[] bitmapToBytes;
	//設定圖片的儲存位置，以及檔名
    private  File tmpFile = new File(Environment.getExternalStorageDirectory(), "image.jpg");
    private Uri outputFileUri = Uri.fromFile(tmpFile);
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// 
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		actionBar=getActivity().getActionBar();
		actionBar.setTitle("个人信息");
		View view= inflater.inflate(R.layout.fragment_userinfo, container, false);
		InitView(view);
		InitEvent();
		return view;
	}
	//初始化控件
	private void InitView(View view){
		head=(RelativeLayout) view.findViewById(R.id.userinfo_rl_head);
		headImage=(ImageView) view.findViewById(R.id.userinfo_imageview_head);
		name=(RelativeLayout) view.findViewById(R.id.userinfo_rl_name);
		mail=(RelativeLayout) view.findViewById(R.id.userinfo_rl_mail);
		signture=(RelativeLayout) view.findViewById(R.id.userinfo_rl_signature);
		logout=(Button) view.findViewById(R.id.userinfo_button_logout);
	}
	//初始化控件点击事件监听
	private void InitEvent(){
		head.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 
				new  AlertDialog.Builder(getActivity())                   
				.setItems(new  String[] {"拍照", "选择图片" }, 
				  new  DialogInterface.OnClickListener() {  
				     public   void  onClick(DialogInterface dialog,  int  which) {
				    	if(which==0){
				    		//拍照
			                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
			                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE); 
			              
			                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
			                startActivityForResult(intent, 1);
			                
				    	}
				    	else if(which==1){
				    		//选择照片			                
			                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);  
			                startActivityForResult(intent, 2);  
				    	}
				        dialog.dismiss();  
				     }  
				  }  
				)   
				.show();  
			}
		});
		logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//退出登录
				SettingsUtil.set(getActivity(), SettingsUtil.IS_LOGIN, false);
				Intent intent=new Intent();
				intent.putExtra("username", "请登录");
				getActivity().setResult(1,intent);//resultCode=1
				//退出当前activity
				getActivity().finish();
			}
		});
		name.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//
				Toast.makeText(getActivity(), "敬请期待. . .",Toast.LENGTH_SHORT).show();
			}
		});
		mail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//
				Toast.makeText(getActivity(), "敬请期待. . .",Toast.LENGTH_SHORT).show();
			}
		});
		signture.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//
				Toast.makeText(getActivity(), "敬请期待. . .",Toast.LENGTH_SHORT).show();
			}
		});
	}
	//
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		//
		super.onActivityResult(requestCode, resultCode, data);
		//拍照
		if (requestCode == 1 && resultCode == getActivity().RESULT_OK){
			Bitmap bmp = BitmapFactory.decodeFile(outputFileUri.getPath()); //利用BitmapFactory去取得剛剛拍照的圖像
			headImage.setImageBitmap(bmp);
		}
		//选择图片
		 else if (requestCode == 2 && resultCode == getActivity().RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getActivity().getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			Bitmap image=BitmapFactory.decodeFile(picturePath);
			headImage.setImageBitmap(image);
			bitmapToBytes=Bitmap2Bytes(image);
		}
		
	}
	/**
	 * bitmap to byte[]
	 * @param bitmap 
	 * @return byte[]
	 */
	private byte[] Bitmap2Bytes(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}
}
