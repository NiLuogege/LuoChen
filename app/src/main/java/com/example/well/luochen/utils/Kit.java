package com.example.well.luochen.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

import com.example.well.luochen.MyApplication;

/**
 * Created by Well on 2016/6/21.
 */

public class Kit {
    public static float dp2px(float dp, Resources resources) {

        float density = resources.getDisplayMetrics().density;
        // CommonKit.saveLog("density:" + density);
        // Log.e("dp2px", "density:" + density);
        if (density > 0.0f) {
            return density * dp;
            // if (density <= 2.0f) {
            // return density * dp;
            // }
            // else {
            // density = density / 2;
            //
            // return density * dp;
            //
            // //CommonKit.saveLog("dm.widthPixels:" + dm.widthPixels);
            // //return ((float)dm.widthPixels / 1024.0f) * dp;
            // }

            // float widthPixels = dm.widthPixels;
            //
            // return (widthPixels / 1024.0f) * dp;
        } else {
            return dp;
        }
    }

    public static Point getDisplayScreenMetrics() {
        DisplayMetrics dm = new DisplayMetrics();
        dm = MyApplication.getInstance().getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        return new Point(screenWidth, screenHeight);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
//					System.out.println(i + "===状态==="
//							+ info[i].getState());
//					System.out.println(i + "===类型==="
//							+ info[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
//						Log.i("NetWorkState", "Availabel");
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
