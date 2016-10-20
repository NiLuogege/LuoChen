package com.example.well.luochen.mode.activity;

import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.well.luochen.R;
import com.example.well.luochen.mode.fragment.DouBanMoveFragment;
import com.example.well.luochen.utils.GlideUtils;
import com.example.well.luochen.utils.Kit;
import com.example.well.luochen.utils.LogUtils;
import com.example.well.luochen.utils.jsoup.JsoupUtil;
import com.example.well.luochen.utils.jsoup.MoveDetail;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.List;

/**
 * Created by Well on 2016/10/19.
 */
@EActivity(R.layout.activity_doubanmovedetail)
public class DoubanMoveDetailActivity extends BaseActivity {

    @ViewById
    Toolbar tl_me;
    @ViewById
    CollapsingToolbarLayout collapsing_toolbar;
    @ViewById
    ImageView backdrop;
    @ViewById
    TextView tv_info,tv_intro,tv_name,tv_rating;
    @ViewById
    LinearLayout ll;
    @ViewById
    RatingBar rb;
    private MoveDetail mMoveDetail;

    @AfterViews
    void initAfterViews() {
        String url_pic = getIntent().getStringExtra(DouBanMoveFragment.extre_url_pic);
        final String url_detail = getIntent().getStringExtra(DouBanMoveFragment.extre_url_detail);
        GlideUtils.displayImageView(this, url_pic, backdrop, R.drawable.load_failed);
        tl_me.setNavigationIcon(R.mipmap.back);
        tl_me.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setSharedElementReturnTransition(new Fade(Fade.IN));
                }
                ActivityCompat.finishAfterTransition(DoubanMoveDetailActivity.this);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mMoveDetail = JsoupUtil.getInstance().getDoubanMoveDetail(url_detail);
                    LogUtils.logError("doubanMoveDetail=" + mMoveDetail.toString());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(null==DoubanMoveDetailActivity.this&&DoubanMoveDetailActivity.this.isDestroyed()){
                                return;
                            }
                            showView();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void showView() {
        if(null!=mMoveDetail.imageList&&mMoveDetail.imageList.size()>0){
            GlideUtils.displayImageView(DoubanMoveDetailActivity.this, mMoveDetail.imageList.get(0), backdrop, R.drawable.load_failed);
        }
        collapsing_toolbar.setTitle(mMoveDetail.name);
        tv_info.setText(mMoveDetail.info);
        tv_intro.setText(mMoveDetail.intro);
        tv_name.setText(mMoveDetail.name);
        tv_rating.setText(mMoveDetail.ratingNum+"");
        rb.setRating(mMoveDetail.ratingNum*5/10);

        List<String> imageList = mMoveDetail.imageList;

        if(null!=imageList&&imageList.size()>0){
            setImage(imageList);
        }
    }

    private void setImage(List<String> imageList) {
        for (int i = 0; i <imageList.size() ; i++) {

            CardView cardView = new CardView(DoubanMoveDetailActivity.this);
            cardView.setCardElevation(Kit.dp2px(3,DoubanMoveDetailActivity.this.getResources()));
            cardView.setCardBackgroundColor(R.color.color_4);
            cardView.setRadius(Kit.dp2px(10,DoubanMoveDetailActivity.this.getResources()));
            CardView.LayoutParams layoutParams = new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,(int) Kit.dp2px(8,DoubanMoveDetailActivity.this.getResources()),0, (int) Kit.dp2px(8,DoubanMoveDetailActivity.this.getResources()));
            cardView.setLayoutParams(layoutParams);

            ImageView imageView = new ImageView(DoubanMoveDetailActivity.this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            GlideUtils.displayImageView(DoubanMoveDetailActivity.this, mMoveDetail.imageList.get(i), imageView, R.drawable.load_failed);

            cardView.addView(imageView);
            ll.addView(cardView);

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                getWindow().setSharedElementReturnTransition(new Fade(Fade.IN));
            }
            ActivityCompat.finishAfterTransition(DoubanMoveDetailActivity.this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
