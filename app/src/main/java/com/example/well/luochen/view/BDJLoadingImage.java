package com.example.well.luochen.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.example.well.luochen.R;

/**
 * Created by Well on 2016/9/29.
 */

public class BDJLoadingImage extends LinearLayout {
    public BDJLoadingImage(Context context) {
        super(context);
        init();
    }

    public BDJLoadingImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BDJLoadingImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        View.inflate(getContext(), R.layout.image_loading,this);
    }
}
