package com.example.well.luochen.mode.activity;

import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.transition.Fade;
import android.view.KeyEvent;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.well.luochen.R;
import com.example.well.luochen.utils.ActivityCompatUtils;
import com.example.well.luochen.utils.ImageHelper;
import com.example.well.luochen.utils.LogUtils;
import com.example.well.luochen.utils.MD5;
import com.example.well.luochen.view.PinchImageView;
import com.example.well.luochen.view.hugeImageView.HugeImageRegionLoader;
import com.example.well.luochen.view.hugeImageView.TileDrawable;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;

/**
 * Created by Well on 2016/7/9.
 */
@EActivity(R.layout.activity_load_big_image)
public class LoadBigImageActivity extends BaseActivity {
//    @Extra
    String url;

    @ViewById
    PinchImageView iv;
    private TileDrawable mTileDrawable;
    private String mMd5String;

    @AfterViews
    void initAfterViews() {
        String url = getIntent().getStringExtra(ActivityCompatUtils.EXTRA_IMAGE_RESOUCE_ID);
        this.url=url;

        LogUtils.logError("url=" + url);
        mMd5String = MD5.encodeMD5String(url);
        File file = new File(ImageHelper.ALBUM_PATH + mMd5String);
        if (file.exists()) {
            loadImage2Local();
            LogUtils.logError("本地有");
        } else {
            loadImage2Net();
            LogUtils.logError("本地没有");
        }


    }

    private void loadImage2Local() {
        iv.post(new Runnable() {
            @Override
            public void run() {
                mTileDrawable = new TileDrawable();
                mTileDrawable.setInitCallback(new TileDrawable.InitCallback() {
                    @Override
                    public void onInit() {
                        iv.setImageDrawable(mTileDrawable);
                    }
                });
                Uri uri = Uri.fromFile(new File(ImageHelper.ALBUM_PATH + mMd5String));
                LogUtils.logError("长片路径" + uri.getAuthority());
                mTileDrawable.init(new HugeImageRegionLoader(LoadBigImageActivity.this, uri), new Point(iv.getWidth(), iv.getHeight()));
            }
        });
    }

    private void loadImage2Net() {
        Glide.with(LoadBigImageActivity.this)
                .load(url)
                .error(R.drawable.load_failed)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        LogUtils.logError("onException");
                        if (null != e)
                            LogUtils.logError("加载失败" + " e=" + e.toString() + " model=" + model);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        LogUtils.logError("onResourceReady");
//                        iv.setImageResource(R.drawable.load_failed);
                        return false;
                    }


                }).into(iv);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setSharedElementReturnTransition(new Fade(Fade.IN));
            }
            ActivityCompat.finishAfterTransition(LoadBigImageActivity.this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
