package com.example.well.luochen.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.well.luochen.R;

/**
 * Created by Well on 2016/7/26.
 */

public class GlideImageUtils {

    public static void  loadImageToImageView(Context context, String url, ImageView imageView){
        Glide.with(context).load(url).dontAnimate().error(R.drawable.icon_moren).diskCacheStrategy(DiskCacheStrategy.SOURCE).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                LogUtils.logError("加载失败" + " e=" + e.toString() + " model=" + model);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        }).into(imageView);
    }
    public static void  loadImageToMusic(Context context, String url, ImageView imageView){
        Glide.with(context).load(url).dontAnimate().error(R.drawable.login_register_bg).diskCacheStrategy(DiskCacheStrategy.SOURCE).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                LogUtils.logError("加载失败" + " e=" + e.toString() + " model=" + model);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        }).into(imageView);
    }
}
