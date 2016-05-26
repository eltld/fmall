package com.ngu.meishishuo.utils;

import java.util.ArrayList;
import java.util.List;

import com.ngu.meishishuo.model.Comment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class CommentDao {
		private static MyHelper helper=null;
		//数据库名
		public static final String DATABASE_NAME="comments.db";
		//评论
		public static final String COMMENTS_TABLE="comments";
		//创建数据库的SQL语句
		private static final String COMMENTS_TABLE_CREATE="CREATE TABLE "+COMMENTS_TABLE+"(_id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20),time VARCHAR(10),content VARCHAR(20))";
		public  CommentDao(Context context,String databaseName) {
			helper=new MyHelper(context,databaseName);
		}
		public void insert (Comment item)
		{
			//获取一个可写的sqldatabase对象
			SQLiteDatabase db=helper.getWritableDatabase();
			ContentValues values=new ContentValues();
			values.put("name", item.getName());
			values.put("time", item.getTime());
			values.put("content", item.getContent());
			long _id=db.insert(COMMENTS_TABLE, null, values);
			item.set_id(_id);
			db.close();
		}
		public int delete(long _id)
		{
			SQLiteDatabase db=helper.getWritableDatabase();
			int count=db.delete(COMMENTS_TABLE, "_id=?",new String[] {_id+""});
			db.close();
			return count;
		}
		public int update(Comment item)
		{
			
			SQLiteDatabase db=helper.getWritableDatabase();
			ContentValues values=new ContentValues();
			values.put("name",item.getName());
			values.put("time", item.getTime());
			values.put("content", item.getContent());
			int count=db.update(COMMENTS_TABLE, values, "_id=?", new String[]{item.get_id()+""});
			db.close();
			return count;
		}
		//
		public List<Comment> queryAll(String tableName)
		{
			SQLiteDatabase db=helper.getWritableDatabase();
			Cursor c=db.query(tableName, null, null, null, null, null,null); 
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
					db.execSQL(COMMENTS_TABLE_CREATE);
				
			}
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				
			}
		}
}
