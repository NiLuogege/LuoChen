package com.example.well.luochen.mode.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.well.luochen.R;
import com.example.well.luochen.net.HttpListener;
import com.example.well.luochen.net.data.BsMusicResponse;
import com.example.well.luochen.net.info.MusicListInfo;
import com.example.well.luochen.utils.GlideImageUtils;
import com.example.well.luochen.utils.LogUtils;
import com.example.well.luochen.utils.Player;
import com.example.well.luochen.utils.RequestWhat;
import com.example.well.luochen.utils.URLUtils;
import com.example.well.luochen.view.CircleImageView;
import com.yolanda.nohttp.rest.Response;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * Created by Well on 2016/7/27.
 */

@EActivity(R.layout.activity_music_detail)
public class MusicDetailActivity extends BaseActivity implements AdapterView.OnItemClickListener, SeekBar.OnSeekBarChangeListener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    @ViewById
    public ListView lv_music_detail;
    @ViewById
    public SeekBar sb;
    @ViewById
    public RelativeLayout ll_music_detail;
    @ViewById
    public CircleImageView iv_riv;
    @ViewById
    public ImageView iv_switch;
    @ViewById
    SwipeRefreshLayout srl;

    @Extra
    public int position;

    private ArrayList<MusicListInfo> mSonglist;
    private Player mPlayer;
    private int progress;
    private boolean isStop = false;
    private ObjectAnimator objAnim = null;
    private float currentValue = 0f;
    private int topId = -1;


    @AfterViews
    public void initAfterView() {
        initView();
        initData();
        initListener();

    }

    private void initView() {
        app.setSystemBar(this, R.color.blue);
        mPlayer = new Player(sb);
        sb.setOnSeekBarChangeListener(this);
    }

    private void initListener() {
        lv_music_detail.setOnItemClickListener(this);
        iv_switch.setOnClickListener(this);
        srl.setOnRefreshListener(this);
        srl.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent));
    }

    private void initData() {
        if (position==0)
            return;
        switch (position - 1) {//这里减一是一位上一个 list添加了一个头所以position 不对
            case 0://民谣
                topId = 18;
                break;
            case 1://欧美
                topId = 3;
                break;
            case 2://港台
                topId = 6;
                break;
            case 3://韩国
                topId = 16;
                break;
            case 4://内地
                topId = 5;
                break;
            case 5://日本
                topId = 17;
                break;
            case 6://热歌
                topId = 26;
                break;
            case 7://摇滚
                topId = 19;
                break;
            case 8://销量
                topId = 23;
                break;
        }

        ArrayList<MusicListInfo> info = (ArrayList<MusicListInfo>) mACache.getAsObject("Music" + (position - 1));
        if (null != info && info.size() > 0) {
            mSonglist = info;
            lv_music_detail.setAdapter(new MusicDetailAdapter());
        } else {
            getMusic();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        ll_music_detail.setVisibility(View.VISIBLE);
        lv_music_detail.setVisibility(View.GONE);

        app.setSystemBar(this, R.color.color_1);

        MusicListInfo info = mSonglist.get(position);
        GlideImageUtils.loadImageToMusic(MusicDetailActivity.this, info.albumpic_big, iv_riv);
        initAnamation();
        sb.setProgress(0);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = mSonglist.get(position).url;
                if(!TextUtils.isEmpty(url))
                {
                    mPlayer.playUrl(url);
                }
            }
        }).start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.logError("onDestroy");
        reSetMediaPlayer();
    }

    private void reSetMediaPlayer() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (ll_music_detail.getVisibility() == View.VISIBLE) {
                ll_music_detail.setVisibility(View.GONE);
                lv_music_detail.setVisibility(View.VISIBLE);
                app.setSystemBar(this, R.color.blue);
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void initAnamation() {
        startAnimation();
    }

    public void getMusic() {
        String url = String.format(URLUtils.URL_MUSIC + "?showapi_appid=20775&topid=%s&showapi_sign=d159d4a9edd649c9b669386b0170babc", topId);
        LogUtils.logError("音乐" + url);
        requestGet(RequestWhat.What_2, url, BsMusicResponse.class, new HttpListener<BsMusicResponse>() {
            @Override
            public void onSucceed(int what, Response<BsMusicResponse> response) {
                if (null!=response)
                {
                    LogUtils.logError("歌曲" + response.get().showapi_res_body.pagebean.songlist.toString());
                    mSonglist = response.get().showapi_res_body.pagebean.songlist;
                    mACache.put("Music" + (position - 1), mSonglist);
                    LogUtils.logError("缓存时候的" + "   Music" + (position - 1));
                    lv_music_detail.setAdapter(new MusicDetailAdapter());
                }

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish() {

            }
        }, true);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        // 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()
        this.progress = progress * mPlayer.mediaPlayer.getDuration() / seekBar.getMax();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
        mPlayer.mediaPlayer.seekTo(progress);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_switch:
                if (isStop) {
                    iv_switch.setImageResource(R.drawable.video_pause_big);
                    isStop = false;
                    mPlayer.play();
                    objAnim.resume();
                } else {
                    iv_switch.setImageResource(R.drawable.video_play_big);
                    isStop = true;
                    mPlayer.pause();
                    objAnim.pause();
                }
                break;
        }
    }

    /**
     * 开始动画
     */
    public void startAnimation() {

        // 设置动画，从上次停止位置开始,这里是顺时针旋转360度
        objAnim = ObjectAnimator.ofFloat(iv_riv, "Rotation", currentValue - 360, currentValue);
        // 设置持续时间
        objAnim.setDuration(9000);
        // 设置循环播放
        objAnim.setRepeatCount(ObjectAnimator.INFINITE);

        LinearInterpolator interpolator = new LinearInterpolator();

        objAnim.setInterpolator(interpolator);

        // 设置动画监听
        objAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 监听动画执行的位置，以便下次开始时，从当前位置开始
                currentValue = (Float) animation.getAnimatedValue();
            }
        });
        objAnim.start();
    }

    @Override
    public void onRefresh() {
//        ToastUtils.show(context, "没有更多数据了");
        srl.setRefreshing(false);
    }


    public class MusicDetailAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mSonglist.size();
        }

        @Override
        public Object getItem(int position) {
            return mSonglist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Viewholder holder = null;
            if (null == convertView) {
                holder = new Viewholder();
                convertView = View.inflate(context, R.layout.item_music_detail, null);
                holder.tv_music = (TextView) convertView.findViewById(R.id.tv_music);
                holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                holder.iv_download = (ImageView) convertView.findViewById(R.id.iv_download);
                holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);

                convertView.setTag(holder);
            } else {
                holder = (Viewholder) convertView.getTag();
            }
            MusicListInfo info = mSonglist.get(position);

            holder.tv_music.setText(TextUtils.isEmpty(info.songname) ? "未知" : info.songname);
            holder.tv_name.setText(TextUtils.isEmpty(info.singername) ? "未知" : info.singername);

            GlideImageUtils.loadImageToImageView(context, info.albumpic_small, holder.iv_image);

            holder.iv_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return convertView;
        }

        class Viewholder {
            TextView tv_music;
            TextView tv_name;
            ImageView iv_download;
            ImageView iv_image;
        }
    }
}
