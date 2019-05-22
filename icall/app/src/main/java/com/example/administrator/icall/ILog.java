package com.example.administrator.icall;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ILog {
	public static  String PACKAGE="com.yh";
	public static void print(String s) {
		Log.i(PACKAGE, s);
	}
	public static void e(Throwable e) {
		Log.e(PACKAGE, "exception", e);
	}
	public static void ShowMsg(Context context,String s){
		Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
	}
}
