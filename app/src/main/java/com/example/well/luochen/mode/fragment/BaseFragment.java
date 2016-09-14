package com.example.well.luochen.mode.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.well.luochen.R;
import com.example.well.luochen.mode.activity.MainActivity;
import com.example.well.luochen.utils.ACache;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Well on 2016/7/18.
 */

public class BaseFragment extends Fragment {
    private MainActivity mMainActivity;
    public ACache mACache = null;
    private Context mContext;
    private static ConnectivityManager mConnectivityManager;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMainActivity = (MainActivity) getActivity();
        mContext = mMainActivity;
        mACache = ACache.get(mMainActivity);

        mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 判断是否是WiFi模式
     */
    public static boolean isWifi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)//版本是21
        {
            Network[] allNetworks = mConnectivityManager.getAllNetworks();
            if (null != allNetworks) {
                for (int i = 0; i < allNetworks.length; i++) {
                    Network network = allNetworks[i];
                    NetworkInfo networkInfo = mConnectivityManager.getNetworkInfo(network);
                    String typeName = networkInfo.getTypeName();
                    if (TextUtils.equals("WIFI", typeName)) {
                        if (networkInfo.getState() == NetworkInfo.State.CONNECTED || networkInfo.getState() == NetworkInfo.State.CONNECTING) {
                            return true;
                        }
                    }
                }
            }
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            NetworkInfo wifi = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifi.getState() == NetworkInfo.State.CONNECTED || wifi.getState() == NetworkInfo.State.CONNECTING) {
                return true;
            }
        }
        return false;
    }

    /**
     * 启动分享页面
     */
    protected void showShare() {
        ShareSDK.initSDK(mContext);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(mContext);
    }

    protected void setTabVisible(Boolean flag) {
        if (flag) {
            mMainActivity.mContentFragment.ntb_vertical.setVisibility(View.VISIBLE);
        } else {
            mMainActivity.mContentFragment.ntb_vertical.setVisibility(View.GONE);
        }
    }


}
