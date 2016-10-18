package com.example.well.luochen.mode.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;

import com.example.well.luochen.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Well on 2016/10/13.
 */

@EActivity(R.layout.activity_image_me)
public class MeImageActivity extends BaseActivity {
    @ViewById
    Toolbar tl_me;
    @ViewById
    CollapsingToolbarLayout collapsing_toolbar;


    @AfterViews
    void initAfterViews() {
        collapsing_toolbar.setTitle("罗晨");
        tl_me.inflateMenu(R.menu.menu_toolbar_meimage);
        tl_me.setNavigationIcon(R.mipmap.logo);

    }

}
