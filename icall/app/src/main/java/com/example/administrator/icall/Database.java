package com.example.administrator.icall;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Database {
	private SQLiteDatabase _db;
	public static Database   INSTANCE;
	public Database() {
		super();
	}
	public static void instance(String databaseFilename){
		INSTANCE=new Database();
		INSTANCE.setupDB(databaseFilename);
	}
	public void setupDB(String databaseFilename) {
		_db= SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
	}
	
	public void setupDB(Context context,String dbPath,String dbName,int db_id) {
		String databaseFilename = dbPath + dbName;
		File dir = new File(dbPath);
		if (!dir.exists()) {
			dir.mkdir();
		}
		try {
				ILog.print("create database");
				InputStream is = context.getResources().openRawResource(db_id);
				FileOutputStream fos = new FileOutputStream(databaseFilename);
				byte[] buffer = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.flush();
				fos.close();
				is.close();
		} catch (Exception e) {
			ILog.e(e);
		}
		_db= SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
	}
	public boolean tableExists(String tableName){
		String sql= "SELECT 1 FROM sqlite_master where type='table' and name='"+tableName+"'";
		return query(sql).size()>0;
	}
	public List<Map<String,String>>   query(String sql){
		List<Map<String,String>> lst=new ArrayList<Map<String,String>>();
		Cursor cursor =null;
		try {
			cursor=_db.rawQuery(sql, null);
			if(cursor != null){  
				String[] columns=cursor.getColumnNames();
				while(cursor.moveToNext()){  
					HashMap<String,String> map=new HashMap<String,String>(); 
					for (int i = 0; i < columns.length; i++) {
						map.put(columns[i], cursor.getString(i));
					}
					lst.add(map);
				}  
			}  
		} catch (Exception e) {
			ILog.e(e);
		}finally{
			if(cursor!=null){
				cursor.close();
			}
		}
		return lst;
	}
	public String[]   querySingleArray(String sql){
		Cursor cursor =null;
		try {
			cursor=_db.rawQuery(sql, null);
			if(cursor != null){
				String[] result=new String[cursor.getCount()];
				int i=0;
				while(cursor.moveToNext()){
					result[i++]=cursor.getString(0);
				} 
				return result;
			}
			
		} catch (Exception e) {
			ILog.e(e);
		}finally{
			if(cursor!=null){
				cursor.close();
			}
		}
		return new String[0];
	}
	public String queryString(String sql){
		Cursor cursor =null;
		try {
			cursor=_db.rawQuery(sql, null);
			if(cursor != null){
				while(cursor.moveToNext()){
					String s= cursor.getString(0);
					return s==null?"":s;
				} 
			}
			
		} catch (Exception e) {
			ILog.e(e);
		}finally{
			if(cursor!=null){
				cursor.close();
			}
		}
		return "";
	}
	public boolean exists(String sql) throws Exception{
		Cursor cursor =null;
		try {
			cursor=_db.rawQuery(sql, null);
			return cursor != null&&cursor.getCount()>0;
		} catch (Exception e) {
			throw(e);
		}finally{
			if(cursor!=null){cursor.close();}
		}
	}
	public void execute(final String sql)  {
		_db.execSQL(sql);
	}

}
