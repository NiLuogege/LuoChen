package com.example.well.luochen.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class ToastUtils {
	
	public static String lastMessage = "";
	public static long lastTime = 0L;
	public static final long ShowInterval = 5 * 1000L;
	
    public static void show(Context context, int resId) {
        show(context, context.getResources().getText(resId), Toast.LENGTH_LONG);
    }

    public static void show(Context context, int resId, int duration) {
        show(context, context.getResources().getText(resId), duration);
    }

    public static void show(Context context, CharSequence content) {
        show(context, content, Toast.LENGTH_LONG);
    }
    
    public static void show(Context context, String content) {
    	if (TextUtils.isEmpty(content)) {
//    		show(context, R.string.notice_net_connect_not_effective, Toast.LENGTH_LONG);
    	}
    	else {
    		show(context, content, Toast.LENGTH_LONG);
    	}
    }

    public static void show(Context context, CharSequence content, int duration) {
    	String message = content.toString();
    	if (lastMessage.equals(message)) {
    		if (lastTime + ShowInterval >= System.currentTimeMillis()) {
    			return;
    		}
    	}
        Toast.makeText(context, content, duration).show();
        lastMessage = message;
        lastTime = System.currentTimeMillis();
    }

    public static void show(Context context, int resId, Object... args) {
        show(context, (String)context.getText(resId), Toast.LENGTH_LONG);
    }

    public static void show(Context context, String format, Object... args) {
        show(context, String.format(format, args), Toast.LENGTH_LONG);
    }

    public static void show(Context context, int resId, int duration, Object... args) {
        show(context, (String)context.getText(resId), duration);
    }

    public static void show(Context context, String format, int duration, Object... args) {
        show(context, String.format(format, args), duration);
    }
    
}
