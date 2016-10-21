package com.example.well.luochen.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.example.well.luochen.mode.activity.LoadBigImageActivity_;

/**
 * Created by Well on 2016/10/12.
 */

public class ActivityCompatUtils {
    public static final String EXTRA_IMAGE_RESOUCE_ID = "EXTRA_IMAGE_RESOUCE_ID";
    public static final String TRANSITION_NAME_PHOTO = "share_img";

    public static void start(Activity activity, String resId, ImageView imageView) {
        Intent intent = new Intent(activity, LoadBigImageActivity_.class);
        intent.putExtra(EXTRA_IMAGE_RESOUCE_ID, resId);
LogUtils.logError("activity="+activity+" imageView="+imageView);
        //noinspection unchecked
        Bundle options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                Pair.create((View) imageView, TRANSITION_NAME_PHOTO))
                .toBundle();

        ActivityCompat.startActivity(activity, intent, options);
    }
}
