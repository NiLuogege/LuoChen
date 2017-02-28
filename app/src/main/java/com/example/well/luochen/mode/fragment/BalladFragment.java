package com.example.well.luochen.mode.fragment;

import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.well.luochen.R;
import com.example.well.luochen.mode.activity.MainActivity;
import com.example.well.luochen.net_okhttp.HttpListener;
import com.example.well.luochen.net_okhttp.data.BsMusicResponse;
import com.example.well.luochen.net_okhttp.info.MusicListInfo;
import com.example.well.luochen.utils.GlideImageUtils;
import com.example.well.luochen.utils.LogUtils;
import com.example.well.luochen.utils.RequestWhat;
import com.example.well.luochen.utils.URLUtils;
import com.example.well.luochen.view.PullZoomListView;
import com.yolanda.nohttp.rest.Response;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Well on 2016/7/26.
 * 民谣
 */
@EFragment(R.layout.fragment_ballad)
public class BalladFragment extends BaseFragment {
    @ViewById
    PullZoomListView id_list_view;
    private String[] data;
    private MainActivity mActivity;


    @AfterViews
    void initAfterViews() {
        mActivity = (MainActivity) getActivity();

        initData();


        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("test " + i);
        }
        data = new String[list.size()];
        list.toArray(data);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(mActivity, android.R.layout.simple_list_item_1, data);
        id_list_view.setAdapter(arrayAdapter);
        id_list_view.getHeaderImageView().setImageResource(R.mipmap.page_cover_default_background);

        id_list_view.setOnRefreshListener(new PullZoomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(mActivity, "正在刷新", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        getBallad();
    }

    public void getBallad() {
        String url = String.format(URLUtils.URL_MUSIC + "?showapi_appid=20775&topid=18&showapi_sign=d159d4a9edd649c9b669386b0170babc");
        LogUtils.logError("音乐"+url);
        mActivity.requestGet(RequestWhat.What_2, url, BsMusicResponse.class, new HttpListener<BsMusicResponse>() {
            @Override
            public void onSucceed(int what, Response<BsMusicResponse> response) {
                LogUtils.logError("歌曲"+response.get().showapi_res_body.pagebean.songlist.toString());
                ArrayList<MusicListInfo> songlist = response.get().showapi_res_body.pagebean.songlist;

                GlideImageUtils.loadImageToImageView(mActivity,songlist.get(0).albumpic_big,id_list_view.getHeaderImageView());

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish() {

            }
        }, true);
    }
}
