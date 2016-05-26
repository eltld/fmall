package com.ngu.meishishuo.utils;

import java.util.ArrayList;
import java.util.List;

import com.ngu.meishishuo.model.MeiShi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MeiShiDao {
	private static MyHelper helper=null;
	//数据库名
	public static final String DATABASE_NAME="meishi.db";
	//收藏
	public static final String COLLECTION_TABLE="collections";
	//分类
	public static final String CLASSIFY_TABLE="classifys";
	//创建数据库的SQL语句
	private static final String COLLECTION_DATABASE_CREATE="CREATE TABLE "+COLLECTION_TABLE+"(_id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20),id VARCHAR(10))";
	private static final String CLASSIFY_DATABASE_CREATE="CREATE TABLE "+CLASSIFY_TABLE+"(_id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20),id VARCHAR(10))";
	public  MeiShiDao(Context context,String databaseName) {
		helper=new MyHelper(context,databaseName);
	}
	public void insert (MeiShi item,String tableName)
	{
		//获取一个可写的sqldatabase对象
		SQLiteDatabase db=helper.getWritableDatabase();
		//增加数据时判断数据在数据库中是否存在
		if((!find(item.getName(),tableName,db))){
			ContentValues values=new ContentValues();
			values.put("name", item.getName());
			values.put("id", item.getId());
			long _id=db.insert(tableName, null, values);
			item.set_id(_id);
		}
		db.close();
	}
	public int delete(long _id,String tableName)
	{
		SQLiteDatabase db=helper.getWritableDatabase();
		int count=db.delete(tableName, "_id=?",new String[] {_id+""});
		db.close();
		return count;
	}
	public int update(MeiShi item,String tableName)
	{
		
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("name",item.getName());
		values.put("id", item.getId());
		int count=db.update(tableName, values, "_id=?", new String[]{item.get_id()+""});
		db.close();
		return count;
	}
	//
	public List<MeiShi> queryAll(String tableName)
	{
		SQLiteDatabase db=helper.getWritableDatabase();
		Cursor c=db.query(tableName, null, null, null, null, null,null); 
		List<MeiShi> list=new ArrayList<MeiShi>();
		MeiShi item;
		while(c.moveToNext())
		{
			//
			item=new MeiShi();
			long _id=c.getLong(c.getColumnIndex("_id"));
			String name=c.getString(c.getColumnIndex("name"));
			String id=c.getString(c.getColumnIndex("id"));
			item.set_id(_id);
			item.setId(id);
			item.setName(name);
			list.add(item);
		}
		c.close();
		db.close();
		return list;
	}
	//查询一条数据是是否存在数据库中
	private boolean find(String name,String tableName,SQLiteDatabase db){
		
		Cursor cursor=db.query(tableName, null,"name=?",new String[]{name},null,null,null);
		boolean result=cursor.moveToNext();
		cursor.close();
		return result;
	}
	private class MyHelper extends SQLiteOpenHelper {

		public MyHelper(Context context,String databaseName)
		{
			super(context, databaseName,null,2);

		}
		//数据库第一次创建时调用该方法
		public void onCreate(SQLiteDatabase db)
		{
				db.execSQL(COLLECTION_DATABASE_CREATE);
				db.execSQL(CLASSIFY_DATABASE_CREATE);
			
		}
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
		}
	}
}