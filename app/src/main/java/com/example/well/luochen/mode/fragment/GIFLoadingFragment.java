package com.example.well.luochen.mode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.well.luochen.R;
import com.example.well.luochen.utils.LogUtils;

/**
 * Created by Well on 2016/10/21.
 */

public class GIFLoadingFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gifloading_main, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv);
        Glide.with(this).load(R.drawable.longmao).asGif().listener(new RequestListener<Integer, GifDrawable>() {
            @Override
            public boolean onException(Exception e, Integer model, Target<GifDrawable> target, boolean isFirstResource) {
                e.printStackTrace();
                return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, Integer model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        }).into(imageView);
        return view;
    }
}
