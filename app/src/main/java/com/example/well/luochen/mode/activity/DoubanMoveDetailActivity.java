package com.example.well.luochen.mode.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.example.well.luochen.R;
import com.example.well.luochen.mode.fragment.DouBanMoveFragment;
import com.example.well.luochen.utils.GlideUtils;
import com.example.well.luochen.utils.LogUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

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

    @AfterViews
    void initAfterViews() {
        String url = getIntent().getStringExtra(DouBanMoveFragment.extre);
        LogUtils.logError("url= "+url);
        GlideUtils.displayImageView(this, url, backdrop, R.drawable.load_failed);
        collapsing_toolbar.setTitle("罗晨");
//        tl_me.inflateMenu(R.menu.menu_toolbar_meimage);
//        tl_me.setNavigationIcon(R.mipmap.logo);

    }
}
