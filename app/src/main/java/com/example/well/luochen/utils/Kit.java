package com.example.well.luochen.utils;

import android.content.res.Resources;

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
}
