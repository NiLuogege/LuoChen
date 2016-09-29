package com.example.well.luochen.mode.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.example.well.luochen.R;
import com.example.well.luochen.adapter.MusicAdaptr;
import com.example.well.luochen.mode.activity.MainActivity;
import com.example.well.luochen.mode.activity.MusicDetailActivity_;
import com.example.well.luochen.net.HttpListener;
import com.example.well.luochen.net.data.BsMusicResponse;
import com.example.well.luochen.net.info.MusicListInfo;
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
import java.util.Random;

/**
 * Created by Well on 2016/7/27.
 */
@EFragment(R.layout.fragment_music)
public class MusicFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    @ViewById
    PullZoomListView pzlv;
    @ViewById
    FrameLayout fl;
    public MainActivity mMainActivity;
    public ArrayList<Integer> imageList = new ArrayList<>();
    public ArrayList<String> textList = new ArrayList<>();
    public ArrayList<String> topIdList = new ArrayList<>();
    public String[] colors;
    private int topId = -1;


    @AfterViews
    void initAfterView() {
        mMainActivity = (MainActivity) getActivity();
        initData();
        initListener();
    }

    private void initListener() {
        pzlv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position != 0)
            MusicDetailActivity_.intent(mMainActivity).position(position).start();
    }

    private void initData() {
        topIdList.add("3");
        topIdList.add("5");
        topIdList.add("6");
        topIdList.add("16");
        topIdList.add("17");
        topIdList.add("18");
        topIdList.add("19");
        topIdList.add("23");
        topIdList.add("26");

        Random random = new Random();
        int i = random.nextInt(30);

        LogUtils.logError("随机数" + i);

        if (!topIdList.contains(i + "")) {
            topId = 19;
        } else {
            topId = i;
        }


        getBallad();

        imageList.clear();
        textList.clear();

        imageList.add(R.drawable.music_minyao);
        imageList.add(R.drawable.music_oumei);
        imageList.add(R.drawable.music_gangtai);
        imageList.add(R.drawable.music_hanguo);
        imageList.add(R.drawable.music_neidi);
        imageList.add(R.drawable.music_rb);
        imageList.add(R.drawable.music_rege);
        imageList.add(R.drawable.music_yaogun);
        imageList.add(R.drawable.music_xiaoliang);

        textList.add("民谣");
        textList.add("欧美");
        textList.add("港台");
        textList.add("韩国");
        textList.add("内地");
        textList.add("日本");
        textList.add("热歌");
        textList.add("摇滚");
        textList.add("销量");

        colors = mMainActivity.getResources().getStringArray(R.array.vertical_ntb);


    }

    public void getBallad() {
        String url = String.format(URLUtils.URL_MUSIC + "?showapi_appid=20775&topid=%s&showapi_sign=d159d4a9edd649c9b669386b0170babc", topId);
        LogUtils.logError("音乐" + url);
        mMainActivity.requestGet(RequestWhat.What_2, url, BsMusicResponse.class, new HttpListener<BsMusicResponse>() {
            @Override
            public void onSucceed(int what, Response<BsMusicResponse> response) {

                if (null != response) {
                    //                LogUtils.logError("歌曲" + response.get().showapi_res_body.pagebean.songlist.toString());

                    pzlv.setAdapter(new MusicAdaptr(MusicFragment.this));

                    ArrayList<MusicListInfo> songlist = response.get().showapi_res_body.pagebean.songlist;

                    if (null != songlist) {
                        GlideImageUtils.loadImageToImageView(mMainActivity, songlist.get(8).albumpic_big, pzlv.getHeaderImageView());
                    }


                }


            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish() {

            }
        }, false);
    }


}
