package com.ngu.meishishuo.utils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.provider.BaseColumns;

public class PictureDAO {
	private static MyHelper helper=null;
	//数据库名
	public static final String DATABASE_NAME="pictures.db";
	//评论
	public static final String PICTURES_TABLE="picture";
	//创建数据库的SQL语句
	private static final String PICTURES_TABLE_CREATE="Create table " + PICTURES_TABLE + "(" + BaseColumns._ID
	        + " integer primary key autoincrement," + "headImage"
	        + " blob not null)";
	public  PictureDAO(Context context,String databaseName) {
		helper=new MyHelper(context,databaseName);
	}
	public void insert (Bitmap bitmap)
	{
		//获取一个可写的sqldatabase对象
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("headImage", Bitmap2Bytes(bitmap));
		long _id=db.insert(PICTURES_TABLE, null, values);
		db.close();
	}
	
	 /* bitmap to byte[]
	  * @param bitmap 
	  * @return byte[]
	  */
	private byte[] Bitmap2Bytes(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}
	private class MyHelper extends SQLiteOpenHelper {

		public MyHelper(Context context,String databaseName)
		{
			super(context, databaseName,null,2);

		}
		//数据库第一次创建时调用该方法
		public void onCreate(SQLiteDatabase db)
		{
				db.execSQL(PICTURES_TABLE_CREATE);
			
		}
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
		}
	}
}