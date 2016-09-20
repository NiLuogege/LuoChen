package com.example.well.luochen.utils;

import android.util.Log;

import com.example.well.luochen.MyApplication;
import com.example.well.luochen.MyContacts;

/***
 * 日志工具
 * @author Luo
 *
 */
public class LogUtils {
	private static String Tag = MyApplication.getInstance().getPackageName();
	private static boolean showLog = MyContacts.showLog;
	
	public static void logDug(String msg){
		if(!showLog) return;
		Log.d(Tag, msg);
	}
	
	public static void logError(String msg){
		if(!showLog) return;
		if (null==msg) return;
		Log.e(Tag, msg);
	}
	
	public static void logDug(String tag,String msg){
		if(!showLog) return;
		Log.d(tag, msg);
	}
	
	public static void logError(String tag,String msg){
		if(!showLog) return;
		Log.e(tag, msg);
	}

}
