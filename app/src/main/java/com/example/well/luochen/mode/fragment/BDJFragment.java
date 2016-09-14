package com.example.well.luochen.mode.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.well.luochen.R;
import com.example.well.luochen.adapter.BDJAdapter;
import com.example.well.luochen.jcvideoplayer.JCVideoPlayer;
import com.example.well.luochen.mode.activity.MainActivity;
import com.example.well.luochen.net.HttpListener;
import com.example.well.luochen.net.data.BsbdjResponse;
import com.example.well.luochen.net.info.BsbdjListinfo;
import com.example.well.luochen.utils.LogUtils;
import com.example.well.luochen.utils.RequestWhat;
import com.example.well.luochen.utils.Settings;
import com.example.well.luochen.utils.URLUtils;
import com.mob.tools.utils.UIHandler;
import com.yolanda.nohttp.rest.Response;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.themeCustom.SharePopupWindow;

/**
 * Created by Well on 2016/7/14.
 */
@EFragment(R.layout.fragment_bdj)
public class BDJFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, PlatformActionListener, Handler.Callback {
    @ViewById
    ListView lv_main;
    @ViewById
    SwipeRefreshLayout srl;
    @ViewById
    public
    LinearLayout ll_root;


    private final int REFRESH_COMPLETE = 0X110;
    public ArrayList<BsbdjListinfo> data = new ArrayList<>();
    private BDJAdapter mAdapter;
    private int currentPage = 1;//当前页数
    public boolean isLoadMore = false;
    public MainActivity mActivity;
    public SharePopupWindow mShare;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:

                    isLoadMore = false;//标记为刷新动作
                    currentPage = 0;
                    getBsbdqj();
                    srl.setRefreshing(false);
                    break;

            }
        }
    };


    @AfterViews
    void initAfterViews() {
        mActivity = (MainActivity) getActivity();
        initView();
        initData();
        initListener();

    }

    private void initView() {
        View footerView = View.inflate(mActivity, R.layout.footview, null);
        lv_main.addFooterView(footerView);
    }

    private boolean isfistshow = true;

    private void initListener() {
        srl.setOnRefreshListener(this);
        srl.setColorSchemeColors(ContextCompat.getColor(mActivity, R.color.colorAccent));
    }

    private void initData() {
        ArrayList<BsbdjListinfo> contentlist = (ArrayList<BsbdjListinfo>) mACache.getAsObject(Settings.Cache_BDJ);
        if (null != contentlist && contentlist.size() > 0) {
            LogUtils.logError("contentlist=" + contentlist.toString());
            isLoadMore = false;
            data.clear();
            data.addAll(contentlist);
            initListView();
        } else {
            getBsbdqj();
        }
    }

    @Click
    void rl_title() {
        lv_main.setSelection(0);
    }


    public void getBsbdqj() {
        String url = String.format(URLUtils.URL_BSBDQJ + "?showapi_appid=20775&page=%s&showapi_sign=d159d4a9edd649c9b669386b0170babc", currentPage + 1);
        LogUtils.logError("url=" + url);
        mActivity.requestGet(RequestWhat.What_1, url, BsbdjResponse.class, new HttpListener<BsbdjResponse>() {

            @Override
            public void onSucceed(int what, Response<BsbdjResponse> response) {
                if (response.isSucceed()) {
                    if (isLoadMore)//上拉加载更多
                    {
                        data.addAll(response.get().showapi_res_body.pagebean.contentlist);

                    } else//下拉刷新
                    {
                        ArrayList<BsbdjListinfo> contentlist = response.get().showapi_res_body.pagebean.contentlist;
                        data.clear();
                        data.addAll(contentlist);
                        mACache.put(Settings.Cache_BDJ, contentlist);
                    }
                    currentPage = response.get().showapi_res_body.pagebean.currentPage;

                    LogUtils.logError("isLoadMore=" + isLoadMore + " currentPage=" + currentPage + "" + " data.size=" + data.size());

                    initListView();

                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                Log.e("onFailed11", "onFailed");
            }

            @Override
            public void onFinish() {

            }


        }, true);
    }

    private void initListView() {
        LogUtils.logError("mAdapter=" + mAdapter + " isLoadMore=" + isLoadMore);
        if (mAdapter == null) {
            mAdapter = new BDJAdapter(this);
            lv_main.setAdapter(mAdapter);
        } else {
            if (isLoadMore)//上拉加载更多
            {
                mAdapter.notifyDataSetChanged();
            } else {
                lv_main.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mShare != null) {
            mShare.dismiss();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.logError("onDestroy" + " onDestroy=");
        ShareSDK.stopSDK(mActivity);
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Message msg = new Message();
        msg.arg1 = 1;
        msg.arg2 = i;
        msg.obj = platform;
        UIHandler.sendMessage(msg, BDJFragment.this);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Message msg = new Message();
        msg.what = 1;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Message msg = new Message();
        msg.what = 0;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        int what = msg.what;
        if (what == 1) {
            Toast.makeText(mActivity, "分享失败", Toast.LENGTH_SHORT).show();

        }
        if (mShare != null) {
            mShare.dismiss();
        }
        return false;
    }


}
