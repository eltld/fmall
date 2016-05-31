package com.ngu.meishishuo.utils;

import java.util.ArrayList;
import java.util.List;

import com.ngu.meishishuo.model.Collection;
import com.ngu.meishishuo.model.Comment;
import com.ngu.meishishuo.model.MeiShi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库访问
 * @author zhoufeng06@qq.com
 * @time 2016年5月29日-下午8:39:59
 */
public class MeiShiDao {
	private MyHelper helper=null;
	//数据库名
	public static final String DATABASE_NAME="meishi.db";
	//分类
	public static final String CLASSIFY_TABLE="classifys";
	//创建分类表的SQL语句
	private static final String CLASSIFY_TABLE_CREATE="CREATE TABLE "+CLASSIFY_TABLE+"(_id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20),id VARCHAR(10))";
	//收藏
	public static final String 	COLLECTION_TABLE="collections";
	//创建收藏表的SQL语句
	private static final String COLLECTION_TABLE_CREATE="CREATE TABLE "+COLLECTION_TABLE+"(_id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20),description VARCHAR(20),time VARCHAR(20),id VARCHAR(10))";
	//评论
	public static final String COMMENT_TABLE="comments";
	//创建评论表的SQL语句
	private static final String COMMENT_TABLE_CREATE="CREATE TABLE "+COMMENT_TABLE+"(_id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20),time VARCHAR(10),content VARCHAR(20))";
	
	//构造函数
	public MeiShiDao(Context context){
		helper=new MyHelper(context);
	}
	
	/**
	 * 增加数据到分类表
	 * @param meishi 菜谱名
	 */
	public void insertToClassify (MeiShi meishi)
	{
		//获取一个可写的sqldatabase对象
		SQLiteDatabase db=helper.getWritableDatabase();
		//增加数据时判断数据在数据库中是否存在
		if((!find(meishi.getName(),CLASSIFY_TABLE,db))){
			ContentValues values=new ContentValues();
			values.put("name", meishi.getName());
			values.put("id", meishi.getId());
			long _id=db.insert(CLASSIFY_TABLE, null, values);
			meishi.set_id(_id);
		}
		db.close();
	}
	/**
	 * 更新分类表中的数据
	 * @param meishi 菜谱
	 * @return
	 */
	public int updateClassify(MeiShi meishi)
	{
		
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("name",meishi.getName());
		values.put("id", meishi.getId());
		int count=db.update(CLASSIFY_TABLE, values, "_id=?", new String[]{meishi.get_id()+""});
		db.close();
		return count;
	}
	/**
	 * 查询分类表中的数据
	 * @return 
	 */
	public List<MeiShi> queryAllClassify()
	{
		SQLiteDatabase db=helper.getWritableDatabase();
		Cursor c=db.query(CLASSIFY_TABLE, null, null, null, null, null,null); 
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
	/**
	 * 增加到收藏表
	 * @param colle 收藏
	 */
	public void insertToCollection (Collection colle)
	{
		//获取一个可写的sqldatabase对象
		SQLiteDatabase db=helper.getWritableDatabase();
		//增加数据时判断数据在数据库中是否存在
		if((!find(colle.getName(),COLLECTION_TABLE,db))){
			ContentValues values=new ContentValues();
			values.put("name", colle.getName());
			values.put("time", colle.getTime());
			values.put("id", colle.getId());
			values.put("description", colle.getDescription());
			long _id=db.insert(COLLECTION_TABLE, null, values);
			colle.set_id(_id);
		}
		db.close();
	}
	/**
	 * 更新收藏表中的数据
	 * @param colle 收藏
	 * @return
	 */
	public int updateCollection(Collection colle)
	{
		
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("name",colle.getName());
		values.put("time", colle.getTime());
		values.put("id", colle.getId());
		values.put("description", colle.getDescription());
		int count=db.update(COLLECTION_TABLE, values, "_id=?", new String[]{colle.get_id()+""});
		db.close();
		return count;
	}
	/**
	 * 查询收藏表中的数据
	 * @param tableName 
	 * @return
	 */
	public List<Collection> queryAllCollection()
	{
		SQLiteDatabase db=helper.getWritableDatabase();
		Cursor c=db.query(COLLECTION_TABLE, null, null, null, null, null,null); 
		List<Collection> list=new ArrayList<Collection>();
		Collection item;
		while(c.moveToNext())
		{
			//
			item=new Collection();
			long _id=c.getLong(c.getColumnIndex("_id"));
			String name=c.getString(c.getColumnIndex("name"));
			String time=c.getString(c.getColumnIndex("time"));
			String id=c.getString(c.getColumnIndex("id"));
			String description=c.getString(c.getColumnIndex("description"));
			item.set_id(_id);
			item.setName(name);
			item.setTime(time);
			item.setId(id);
			item.setDescription(description);;
			list.add(item);
		}
		c.close();
		db.close();
		return list;
	}
	/**
	 * 增加一条数据到评论表
	 * @param com 评论
	 */
	public void insertToComment (Comment com)
	{
		//获取一个可写的sqldatabase对象
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("name", com.getName());
		values.put("time", com.getTime());
		values.put("content", com.getContent());
		long _id=db.insert(COMMENT_TABLE, null, values);
		com.set_id(_id);
		db.close();
	}
	/**
	 * 更新评论表中的数据
	 * @param comm 评论
	 * @return
	 */
	public int updateComment(Comment comm)
	{
		
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("name",comm.getName());
		values.put("time", comm.getTime());
		values.put("content", comm.getContent());
		int count=db.update(COMMENT_TABLE, values, "_id=?", new String[]{comm.get_id()+""});
		db.close();
		return count;
	}
	/**
	 * 查询评论表中的数据
	 * @return
	 */
	public List<Comment> queryAllComment()
	{
		SQLiteDatabase db=helper.getWritableDatabase();
		Cursor c=db.query(COMMENT_TABLE, null, null, null, null, null,null); 
		List<Comment> list=new ArrayList<Comment>();
		Comment item;
		while(c.moveToNext())
		{
			//
			item=new Comment();
			long _id=c.getLong(c.getColumnIndex("_id"));
			String name=c.getString(c.getColumnIndex("name"));
			String time=c.getString(c.getColumnIndex("time"));
			String content=c.getString(c.getColumnIndex("content"));
			item.set_id(_id);
			item.setName(name);
			item.setTime(time);
			item.setContent(content);
			list.add(item);
		}
		c.close();
		db.close();
		return list;
	}
	/**
	 * 根据id删除对应数据
	 * @param _id 数据库中id
	 * @param tableName 表名
	 * @return
	 */
	public int delete(long _id,String tableName)
	{
		SQLiteDatabase db=helper.getWritableDatabase();
		int count=db.delete(tableName, "_id=?",new String[] {_id+""});
		db.close();
		return count;
	}
	/**
	 * 查询一条数据是是否存在数据库中
	 * @param name 查询数据名
	 * @param tableName 表名
	 * @param db 数据库名
	 * @return true if find in database else false
	 */
	private boolean find(String name,String tableName,SQLiteDatabase db){
			
		Cursor cursor=db.query(tableName, null,"name=?",new String[]{name},null,null,null);
		boolean result=cursor.moveToNext();
		cursor.close();
		return result;
	}
	
	private class MyHelper extends SQLiteOpenHelper {

		public MyHelper(Context context)
		{
			super(context, DATABASE_NAME,null,2);

		}
		//数据库第一次创建时调用该方法
		public void onCreate(SQLiteDatabase db)
		{
				db.execSQL(CLASSIFY_TABLE_CREATE);
				db.execSQL(COLLECTION_TABLE_CREATE);
				db.execSQL(COMMENT_TABLE_CREATE);
			
		}
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
		}
	}
	
}
