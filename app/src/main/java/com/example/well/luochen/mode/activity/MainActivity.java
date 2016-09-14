package com.example.well.luochen.mode.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.example.well.luochen.R;
import com.example.well.luochen.mode.fragment.ContentFragment_;
import com.example.well.luochen.mode.fragment.LeftFragment_;
import com.example.well.luochen.utils.ToastUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.sharesdk.framework.ShareSDK;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewById
    DrawerLayout dl_main;
    @ViewById
    FrameLayout fl_left;

    private long exitTime = 0l;

    public ContentFragment_ mContentFragment;


    @AfterViews
    void initAfterViews() {
        ShareSDK.initSDK(MainActivity.this, "151e371cdec24");

        initFragment();

    }


    private void initFragment() {
        LeftFragment_ leftFragment = new LeftFragment_();
        mContentFragment = new ContentFragment_();
        FragmentTransaction ftLeft = getSupportFragmentManager().beginTransaction();
        ftLeft.replace(R.id.fl_left, leftFragment).commit();
        FragmentTransaction ftContent = getSupportFragmentManager().beginTransaction();
        ftContent.replace(R.id.fl_content, mContentFragment).commit();
    }

    public void showLeft() {
        dl_main.openDrawer(Gravity.LEFT);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.show(this, "再按一次退出");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}



