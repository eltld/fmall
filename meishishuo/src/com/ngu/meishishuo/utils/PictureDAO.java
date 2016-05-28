package com.ngu.meishishuo.utils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PictureDAO extends SQLiteOpenHelper {
	/**数据库版本号**/
	private final static int DATABASE_VERSION = 1;
	/**数据库名**/
	private final static String DB_NAME = "image.db";
	/**表名**/
	private String TABLE_IMAGE = "image_data";
	public String T_ID = "_id";//字段名
	public String T_BLOB = "T_BLOB";//字段名
	/**创建表SQL 字符串**/
	private String TABLE_IMAGE_CREATE = "Create table " + TABLE_IMAGE + "(" + T_ID
	+ " INTEGER DEFAULT '1' NOT NULL PRIMARY KEY AUTOINCREMENT ,"
	+ T_BLOB + " BLOB );";

	/**构造方法**/
	public PictureDAO(Context context) {
		super(context, DB_NAME, null, DATABASE_VERSION);
	}
	
	public PictureDAO(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	
	/**
	 * 保存bitmap到数据库
	 * @param bmp
	 * @return
	 */
	public Long saveBitmap(Bitmap bmp) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		Long id = null;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		/**
		* JPEG 与 PNG 的是区别在于 JPEG是有损数据图像，PNG使用从LZ77派生的无损数据压缩算法。
		* 这里建议使用PNG格式保存
		* 100 表示的是质量为100%，改变成你所需要的百分比质量。
		* compress()方法是将Bitmap压缩成指定格式和质量的输出流
		*/
		bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
		values.put(T_BLOB, os.toByteArray());//以字节形式保存
		id = db.insert(TABLE_IMAGE, null, values);//保存数据
		db.close();		
		return id;
	}
	
	/**
	 * 获取bitmap
	 * @return
	 */
	public Bitmap getBitmap() {
		Bitmap bmp=null;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_IMAGE, null, null, null, null, null, null);
		if(cursor!=null){
			while (cursor.moveToNext()){
				/**得到Bitmap字节数据**/
				byte[] bytes = cursor.getBlob(1);
				/**
				* 根据Bitmap字节数据转换成 Bitmap对象
				* BitmapFactory.decodeByteArray()方法对字节数据，从0到字节的长进行解码，生成Bitmap对像。
				**/
				bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
				
			}
		}
		cursor.close();
		db.close();
		return bmp;
	}
	
	/**
	 * 
	 * @param item
	 * @param tableName
	 * @return
	 */
	public int update(Bitmap bmp)
	{
		
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues values=new ContentValues();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
		values.put(T_BLOB, os.toByteArray());
		int count=db.update(TABLE_IMAGE, values, "_id=?", new String[]{"1"});
		db.close();
		return count;
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_IMAGE_CREATE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = " drop table " + TABLE_IMAGE;
		db.execSQL(sql);
		onCreate(db);
	}


}