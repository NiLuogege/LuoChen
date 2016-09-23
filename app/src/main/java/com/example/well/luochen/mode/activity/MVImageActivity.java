package com.example.well.luochen.mode.activity;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.well.luochen.R;
import com.example.well.luochen.net.HttpListener;
import com.example.well.luochen.net.data.MVImageResponse;
import com.example.well.luochen.net.info.MVImageBody;
import com.example.well.luochen.net.info.MVImageInfo;
import com.example.well.luochen.utils.GlideUtils;
import com.example.well.luochen.utils.RequestWhat;
import com.example.well.luochen.utils.SpacesItemDecoration;
import com.example.well.luochen.utils.URLUtils;
import com.example.well.luochen.view.AutoSwipeRefreshLayout;
import com.example.well.luochen.view.PinchImageView;
import com.yolanda.nohttp.rest.Response;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;

/**
 * Created by Well on 2016/9/21.
 */
@EActivity(R.layout.activity_image_mv)
public class MVImageActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @ViewById
    RecyclerView list_rv;
    @ViewById
    ImageView iv_bg;
    @ViewById
    PinchImageView iv_fg;
    @ViewById
    AutoSwipeRefreshLayout asrl;

    private int page = 1;
    private ArrayList<MVImageInfo> mNewslist;
    private MVImageAdapter mAdapter;
    private float mRawX;
    private float mRawY;

    @AfterViews
    void initAfterView() {
        initOwnView();
        initData();
    }

    private void initOwnView() {
        app.setSystemBar(this, R.color.color_bg_mv);
        list_rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        list_rv.addItemDecoration(new SpacesItemDecoration(10));
        iv_bg.setImageResource(R.mipmap.bg);
        asrl.setOnRefreshListener(this);
        asrl.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent));
    }

    private void initData() {
        asrl.autoRefresh();
        refreshMVData();
    }

    private void refreshMVData() {
        page = 1;
        String url = String.format(URLUtils.URL_MV + "?showapi_appid=20775&num=50&page=%s&showapi_sign=d159d4a9edd649c9b669386b0170babc", page);
        requestGet(RequestWhat.What_3, url, MVImageResponse.class, new HttpListener<MVImageResponse>() {
            @Override
            public void onSucceed(int what, Response<MVImageResponse> response) {
                MVImageResponse mvImageResponse = response.get();
                if (null != mvImageResponse) {
                    MVImageBody showapi_res_body = mvImageResponse.showapi_res_body;
                    if (null != showapi_res_body) {
                        int code = showapi_res_body.code;
                        if (code == 200)//访问成功
                        {
                            mNewslist = showapi_res_body.newslist;
//                            MVImageInfo mvImageInfo = mNewslist.get(1);
//                            GlideUtils.displayImageView(MVImageActivity.this,mvImageInfo.picUrl,iv_bg,R.drawable.he);

                            if (null == mAdapter) {
                                mAdapter = new MVImageAdapter();
                                SlideInLeftAnimationAdapter adapter = new SlideInLeftAnimationAdapter(mAdapter);
                                adapter.setFirstOnly(false);
                                adapter.setDuration(800);
                                adapter.setInterpolator(new OvershootInterpolator(.5f));
                                list_rv.setAdapter(adapter);
                            } else {
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish() {
                if (null != asrl)
                    asrl.setRefreshing(false);
            }
        }, false);
    }

    private void loadMoreMVData() {
        asrl.setRefreshing(true);
        page += 1;
        String url = String.format(URLUtils.URL_MV + "?showapi_appid=20775&num=50&page=%s&showapi_sign=d159d4a9edd649c9b669386b0170babc", page);
        requestGet(RequestWhat.What_3, url, MVImageResponse.class, new HttpListener<MVImageResponse>() {
            @Override
            public void onSucceed(int what, Response<MVImageResponse> response) {
                MVImageResponse mvImageResponse = response.get();
                if (null != mvImageResponse) {
                    MVImageBody showapi_res_body = mvImageResponse.showapi_res_body;
                    if (null != showapi_res_body) {
                        int code = showapi_res_body.code;
                        if (code == 200)//访问成功
                        {
                            mNewslist.addAll(showapi_res_body.newslist);
                            if (null == mAdapter) {
                                mAdapter = new MVImageAdapter();
                                SlideInLeftAnimationAdapter adapter = new SlideInLeftAnimationAdapter(mAdapter);
                                adapter.setFirstOnly(false);
                                adapter.setDuration(800);
                                adapter.setInterpolator(new OvershootInterpolator(.5f));
                                list_rv.setAdapter(adapter);
                            } else {
                                mAdapter.notifyDataSetChanged();
                            }

                        }
                    }
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish() {
                asrl.setRefreshing(false);
            }
        }, false);
    }

    @Click
    void iv_fg() {
        int visibility = iv_fg.getVisibility();
        if (visibility == View.VISIBLE) {
            if (!isDismissImageIng)
                dismissImageView();
        }

    }

    private boolean isDismissImageIng;

    private void dismissImageView() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(300);
        scaleAnimation.setFillAfter(false);
        iv_fg.startAnimation(scaleAnimation);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isDismissImageIng = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isDismissImageIng = false;
                iv_fg.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            int visibility = iv_fg.getVisibility();
            if (visibility == View.VISIBLE) {
                if (!isDismissImageIng)
                    dismissImageView();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mRawX = ev.getRawX();
        mRawY = ev.getRawY();
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onRefresh() {
        refreshMVData();
    }

    public class MVImageAdapter extends RecyclerView.Adapter<MVImageAdapter.ViewHolder> {


        @Override
        public int getItemViewType(int position) {
            if (position == mNewslist.size() - 1 + 1) {//最后一个条目
                return 1;
            } else {
                return 0;
            }
//            return super.getItemViewType(position);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;
            if (viewType == 0) {
                view = View.inflate(MVImageActivity.this, R.layout.item_activity_image_mv, null);
            } else {
                view = View.inflate(MVImageActivity.this, R.layout.footview_recycle, null);
            }
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            if (position != mNewslist.size() - 1 + 1) {
                MVImageInfo mvImageInfo = mNewslist.get(position);
                final String url = mvImageInfo.picUrl;
                GlideUtils.displayImageView(MVImageActivity.this, url, holder.mIv_item, R.drawable.he);

                holder.mIv_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        iv_fg.setVisibility(View.VISIBLE);
                        GlideUtils.displayImageView(MVImageActivity.this, url, iv_fg, R.drawable.he);
                        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, mRawX, mRawY);
                        scaleAnimation.setDuration(500);
                        scaleAnimation.setFillAfter(false);
                        iv_fg.startAnimation(scaleAnimation);
                    }
                });

            } else {//加载更多
                loadMoreMVData();
            }
        }

        @Override
        public int getItemCount() {
            return mNewslist.size() + 1;
        }


        class ViewHolder extends RecyclerView.ViewHolder {

            private ImageView mIv_item;

            public ViewHolder(View itemView) {
                super(itemView);
                mIv_item = (ImageView) itemView.findViewById(R.id.iv_item);
            }
        }
    }
}
