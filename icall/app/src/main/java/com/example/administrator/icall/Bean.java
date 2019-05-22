package com.example.administrator.icall;

import java.util.List;
import java.util.Map;

import android.content.ContextWrapper;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;


public class Bean {
	
	public Bean() {
		super();
	}
	public static List lstBlock;
	public static final int BLOCK_ALL=0;
	public static final int BLOCK_UNSAVED=1;
	public static final int BLOCK_LIST=2;
	public static final int BLOCK_NONE=3;
	public static int blockMode=BLOCK_NONE;
	public static boolean isBlock(ContextWrapper context,String phoneNo){
		if(phoneNo==null||phoneNo.length()==0)return false;
		if(blockMode==BLOCK_ALL) return true;
		else if(blockMode==BLOCK_UNSAVED) {
			return !isNumberInContactList(context,phoneNo);
		}
		else if(blockMode==BLOCK_LIST) {
			for (int i = 0; i <lstBlock.size() ; i++) {
				Map map=(Map)lstBlock.get(i);
				if(map.get("phone_no").equals(phoneNo)){
					Log.i("xxxxxx","in block list" );
					return true;
				}
			}
			return false;
		}
		return false;
	}
	public static List loadList() {
		String sql="select * from t_phone";
		List lst=Database.INSTANCE.query(sql);
		lstBlock=lst;
		return lst;
	}
	public static boolean isNumberInContactList(ContextWrapper context, String paramString)
	{
		try
		{
			Uri localUri = Uri.parse("content://com.android.contacts/data/phones/filter/" + paramString);
			Cursor localCursor = context.getContentResolver().query(localUri, new String[] { "display_name" }, null, null, null);
			String str = null;
			if (localCursor != null)
			{
				boolean bool = localCursor.moveToFirst();
				str = null;
				if (bool)
					str = localCursor.getString(0);
			}
			if (localCursor != null)
				localCursor.close();
			if (str != null){
				Log.i("xxxxxx","in contact list" );
				return true;
			}

		}
		catch (Exception localException)
		{
			localException.printStackTrace();
		}
		return false;
	}
	public static boolean addNumber(String phoneNo) throws Exception {
		if(!Database.INSTANCE.exists("select 1 from t_phone where phone_no='"+phoneNo+"'")){
			Database.INSTANCE.execute("insert into t_phone(phone_no) values ('"+phoneNo+"')");
			loadList();
			return true;
		}else{
			return false;
		}
	}
	public static boolean editNumber(String id,String phoneNo) throws Exception {
		if(!Database.INSTANCE.exists("select 1 from t_phone where id<> "+id+" and phone_no='"+phoneNo+"'")){
			Database.INSTANCE.execute("update t_phone set phone_no='"+phoneNo+"' where id="+id);
			return true;
		}else{
			return false;
		}
	}
	public static void delNumber(String id) {
		try {
			Database.INSTANCE.execute("delete from t_phone where id="+id+"");
			//loadList();
		} catch (Exception e) {

		}
	}

	public static void createTables() {
		if(!Database.INSTANCE.tableExists("t_phone")){
			Database.INSTANCE.execute("CREATE TABLE \"t_phone\" (  [id] INTEGER NOT NULL ON CONFLICT REPLACE PRIMARY KEY ON CONFLICT REPLACE AUTOINCREMENT,   [phone_no] CHAR)");
		}
	}
}
