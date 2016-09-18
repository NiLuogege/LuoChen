//package com.example.well.luochen.mode.fragment;
//
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.load.resource.drawable.GlideDrawable;
//import com.bumptech.glide.request.RequestListener;
//import com.bumptech.glide.request.target.Target;
//import com.example.well.luochen.R;
//import com.example.well.luochen.mode.activity.LoadBigImageActivity_;
//import com.example.well.luochen.mode.activity.MainActivity;
//import com.example.well.luochen.net.HttpListener;
//import com.example.well.luochen.net.data.BsbdjResponse;
//import com.example.well.luochen.net.info.BsbdjListinfo;
//import com.example.well.luochen.utils.LogUtils;
//import com.example.well.luochen.utils.Settings;
//import com.example.well.luochen.utils.ToastUtils;
//import com.example.well.luochen.utils.URLUtils;
//import com.like.LikeButton;
//import com.like.OnLikeListener;
//import com.mob.tools.utils.UIHandler;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
//import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
//import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
//import com.volokh.danylo.video_player_manager.meta.MetaData;
//import com.yolanda.nohttp.rest.Response;
//
//import org.androidannotations.annotations.AfterViews;
//import org.androidannotations.annotations.Click;
//import org.androidannotations.annotations.EFragment;
//import org.androidannotations.annotations.ViewById;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import cn.sharesdk.framework.Platform;
//import cn.sharesdk.framework.PlatformActionListener;
//import cn.sharesdk.framework.ShareSDK;
//import cn.sharesdk.onekeyshare.themeCustom.ShareModel;
//import cn.sharesdk.onekeyshare.themeCustom.SharePopupWindow;
//import com.example.well.luochen.jcvideoplayer.JCVideoPlayer;
//import com.example.well.luochen.jcvideoplayer.JCVideoPlayerStandard;
//
///**
// * Created by Well on 2016/7/14.
// */
//@EFragment(R.layout.fragment_bdj_2)
//public class BDJFragment_2 extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, PlatformActionListener, Handler.Callback {
//    @ViewById
//    RecyclerView rv_main;
//    @ViewById
//    SwipeRefreshLayout srl;
//    @ViewById
//    LinearLayout ll_root;
//
//
//    private final int REFRESH_COMPLETE = 0X110;
//    public ArrayList<BsbdjListinfo> data = new ArrayList<>();
//    private ContentAdapter mAdapter;
//    private int currentPage = 0;//当前页数
//    private boolean isLoadMore = false;
//    private LinearLayoutManager mLinearLayoutManager = null;
//    private MainActivity mActivity;
//    private SharePopupWindow mShare;
//
//    private Handler mHandler = new Handler() {
//        public void handleMessage(android.os.Message msg) {
//            switch (msg.what) {
//                case REFRESH_COMPLETE:
//
//                    isLoadMore = false;//标记为刷新动作
//                    currentPage = 0;
//                    getBsbdqj();
//                    srl.setRefreshing(false);
//                    break;
//
//            }
//        }
//    };
//
//    private VideoPlayerManager<MetaData> mVideoPlayerManager = new SingleVideoPlayerManager(new PlayerItemChangeListener() {
//        @Override
//        public void onPlayerItemChanged(MetaData metaData) {
//            LogUtils.logError("onPlayerItemChanged");
//        }
//    });
//
//
//    @AfterViews
//    void initAfterViews() {
//        mActivity = (MainActivity) getActivity();
//        mLinearLayoutManager = new LinearLayoutManager(mActivity);
//        initData();
//        initListener();
//
//
//    }
//
//    private void initListener() {
//        srl.setOnRefreshListener(this);
//        srl.setColorSchemeColors(ContextCompat.getColor(mActivity, R.color.colorAccent));
//
//    }
//
//    private void initData() {
//        ArrayList<BsbdjListinfo> contentlist = (ArrayList<BsbdjListinfo>) mACache.getAsObject(Settings.Cache_BDJ);
//        if (null != contentlist && contentlist.size() > 0) {
//            LogUtils.logError("contentlist=" + contentlist.toString());
//            isLoadMore = false;
//            data.clear();
//            data.addAll(contentlist);
//            initRecycleView();
//        } else {
//            getBsbdqj();
//        }
//    }
//
//    @Click
//    void rl_title() {
//        mLinearLayoutManager.scrollToPosition(0);
//    }
//
//
////    @Override
////    public void onResume() {
////        super.onResume();
////        LogUtils.logError("onResume"+" mAdapter="+mAdapter+" mLinearLayoutManager"+mLinearLayoutManager);
////        lv_main.setLayoutManager(mLinearLayoutManager);
////        if (mAdapter == null) {
////            mAdapter = new ContentAdapter();
////            lv_main.setAdapter(mAdapter);
////        } else {
////            mAdapter.notifyDataSetChanged();
////        }
////    }
//
//
//    public void getBsbdqj() {
//        String url = String.format(URLUtils.URL_BSBDQJ + "?showapi_appid=20775&page=%s&showapi_sign=d159d4a9edd649c9b669386b0170babc", currentPage + 1);
//        LogUtils.logError("url=" + url);
//        mActivity.requestGet(1, url, BsbdjResponse.class, new HttpListener<BsbdjResponse>() {
//
//            @Override
//            public void onSucceed(int what, Response<BsbdjResponse> response) {
//                if (response.isSucceed()) {
//                    if (isLoadMore)//上拉加载更多
//                    {
//                        data.addAll(response.get().showapi_res_body.pagebean.contentlist);
//
//                    } else//下拉刷新
//                    {
//                        ArrayList<BsbdjListinfo> contentlist = response.get().showapi_res_body.pagebean.contentlist;
//                        data.clear();
//                        data.addAll(contentlist);
//                        mACache.put(Settings.Cache_BDJ, contentlist);
//                    }
//                    currentPage = response.get().showapi_res_body.pagebean.currentPage;
//
//                    LogUtils.logError("isLoadMore=" + isLoadMore + " currentPage=" + currentPage + "" + " data.size=" + data.size());
//
//                    initRecycleView();
//
//                }
//            }
//
//            @Override
//            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
//                Log.e("onFailed11", "onFailed");
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//
//
//        }, true);
//    }
//
//    private void initRecycleView() {
//        rv_main.setLayoutManager(mLinearLayoutManager);
//        LogUtils.logError("mAdapter=" + mAdapter + " isLoadMore=" + isLoadMore);
//        if (mAdapter == null) {
//            mAdapter = new ContentAdapter();
//            rv_main.setAdapter(mAdapter);
//        } else {
//            if (isLoadMore)//上拉加载更多
//            {
//                mAdapter.notifyDataSetChanged();
//            } else {
//                rv_main.setAdapter(mAdapter);
//                mAdapter.notifyDataSetChanged();
//            }
////            scrollToPosition();
//        }
//    }
//
//    private void scrollToPosition() {
//        if (isLoadMore)//上拉加载更多
//        {
//            int position = mLinearLayoutManager.findFirstVisibleItemPosition();
//            View view = mLinearLayoutManager.findViewByPosition(position);
////            View view = lv_main.getChildAt(position);
//            LogUtils.logError("view=" + view);
//            if (null != view) {
//                int top = view.getTop();
//                mLinearLayoutManager.scrollToPositionWithOffset(data.size() - 20, top);
//                LogUtils.logError("top=" + top);
//            }
//        }
//    }
//
//    @Override
//    public void onRefresh() {
//        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (mShare != null) {
//            mShare.dismiss();
//        }
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        JCVideoPlayer.releaseAllVideos();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        LogUtils.logError("onDestroy" + " onDestroy=");
//        ShareSDK.stopSDK(mActivity);
//    }
//
//    @Override
//    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//        Message msg = new Message();
//        msg.arg1 = 1;
//        msg.arg2 = i;
//        msg.obj = platform;
//        UIHandler.sendMessage(msg, BDJFragment_2.this);
//    }
//
//    @Override
//    public void onError(Platform platform, int i, Throwable throwable) {
//        Message msg = new Message();
//        msg.what = 1;
//        UIHandler.sendMessage(msg, this);
//    }
//
//    @Override
//    public void onCancel(Platform platform, int i) {
//        Message msg = new Message();
//        msg.what = 0;
//        UIHandler.sendMessage(msg, this);
//    }
//
//    @Override
//    public boolean handleMessage(Message msg) {
//        int what = msg.what;
//        if (what == 1) {
//            Toast.makeText(mActivity, "分享失败", Toast.LENGTH_SHORT).show();
//
//        }
//        if (mShare != null) {
//            mShare.dismiss();
//        }
//        return false;
//    }
//
//    public class ContentAdapter extends RecyclerView.Adapter {
//
//        private final int CommonView = 0;//一般布局
//        private final int FootView = 1;//脚布局
//
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//            if (viewType == CommonView) {
//                View view = View.inflate(mActivity, R.layout.item_activity_main, null);
//                CommonViewHolder commonViewHolder = new CommonViewHolder(view);
//                return commonViewHolder;
//
//            } else if (viewType == FootView) {
//                View view = View.inflate(mActivity, R.layout.footview, null);
//                FootViewHolder footViewHolder = new FootViewHolder(view);
//                return footViewHolder;
//            }
//
//            return null;
//        }
//
//        @Override
//        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//
//            if (holder instanceof FootViewHolder) {
//                isLoadMore = true;//标记为加载更多动作
//                FootViewHolder footViewHolder = (FootViewHolder) holder;
//                getBsbdqj();
//
//            } else if (holder instanceof CommonViewHolder) {
//
//                final CommonViewHolder vh = (CommonViewHolder) holder;
//                final BsbdjListinfo listinfo = data.get(position);
//
//                vh.mTv_love.setText(data.get(position).love);
//                vh.mTv_name.setText(data.get(position).name);
//                vh.mTv_createTime.setText(data.get(position).create_time);
//                vh.mTv_text.setText(data.get(position).text);
//                vh.mTv_hate.setText(data.get(position).hate);
//                ImageLoader.getInstance().displayImage(data.get(position).profile_image, vh.mIv_profileImage);
//
//                boolean wifi = isWifi();
//                boolean isSaveFlow = BDJFragment_2.this.mACache.getAsBoolean(Settings.IsSaveFlow);
//                LogUtils.logError("wifi" + wifi);
//
//                if (wifi && isSaveFlow) {
//                    ToastUtils.show(mActivity, "现在已经是省流量模式啦");
//                }
//
//                if (TextUtils.equals(data.get(position).type, "10"))//GIF 图片,和一般图片
//                {
//                    vh.mFl_type.setVisibility(View.VISIBLE);
//                    vh.mIv_image.setVisibility(View.VISIBLE);
//                    vh.mJCVP_S.setVisibility(View.GONE);
//
//                    Glide.with(mActivity).load(data.get(position).image0).dontAnimate().error(R.drawable.icon_moren).diskCacheStrategy(DiskCacheStrategy.SOURCE).listener(new RequestListener<String, GlideDrawable>() {
//                        @Override
//                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//
//                            if(null!=e)
//                            LogUtils.logError("加载失败" + " e=" + e.toString() + " model=" + model);
//
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                            return false;
//                        }
//                    }).into(vh.mIv_image);
//
//                } else if (TextUtils.equals(data.get(position).type, "41"))//视频
//                {
//                    vh.mFl_type.setVisibility(View.VISIBLE);
//                    vh.mIv_image.setVisibility(View.GONE);
//                    vh.mJCVP_S.setVisibility(View.VISIBLE);
//
//                    vh.mJCVP_S.setUp(listinfo.video_uri, "");
//                    vh.mJCVP_S.titleTextView.setVisibility(View.GONE);
//                    vh.mJCVP_S.thumbImageView.setImageResource(R.drawable.shape_background_gray);
//
//
//                } else if (TextUtils.equals(data.get(position).type, "29"))//纯文字
//                {
//                    vh.mFl_type.setVisibility(View.GONE);
//                    vh.mIv_image.setVisibility(View.GONE);
//                    vh.mJCVP_S.setVisibility(View.GONE);
//                }
//                /**
//                 * 点赞
//                 */
//
//                vh.mHeartButton.setOnLikeListener(new OnLikeListener() {
//                    @Override
//                    public void liked(LikeButton likeButton) {
//                        ToastUtils.show(mActivity, "你已经点赞了");
//                    }
//
//                    @Override
//                    public void unLiked(LikeButton likeButton) {
//                        ToastUtils.show(mActivity, "你已经取消点赞了");
//                    }
//                });
//                /**
//                 * 看大图
//                 */
//                vh.mIv_image.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        LoadBigImageActivity_.intent(mActivity).url(data.get(position).image0).start();
//                    }
//                });
//
//                /**
//                 * 分享
//                 */
//                vh.mTv_share.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                        showShare();
//                        mShare = new SharePopupWindow(mActivity);
//                        mShare.setPlatformActionListener(BDJFragment_2.this);
//                        ShareModel model = new ShareModel();
//                        model.setImageUrl(listinfo.image0);
//                        model.setText(listinfo.text);
//                        model.setTitle(listinfo.text);
//                        model.setUrl(listinfo.weixin_url);
//                        mShare.initShareParams(model);
//                        mShare.showShareWindow();
//                        // 显示窗口 (设置layout在PopupWindow中显示的位置)
//                        LogUtils.logError("mShare="+mShare+" ll_root="+ll_root);
//
//                        mShare.showAtLocation(ll_root, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//                    }
//                });
//            }
//        }
//
//        private boolean isFootView(int position) {
//            if (position == data.size()) {
//                return true;
//            }
//            return false;
//        }
//
//        @Override
//        public int getItemCount() {
//            return data.size() + 1;
//        }
//
//        @Override
//        public int getItemViewType(int position) {
//            if (isFootView(position)) {
//                return FootView;
//            } else {
//                return CommonView;
//            }
//        }
//
//        class CommonViewHolder extends RecyclerView.ViewHolder {
//            TextView mTv_love;
//            TextView mTv_name;
//            TextView mTv_createTime;
//            TextView mTv_text;
//            TextView mTv_hate;
//            TextView mTv_share;
//            TextView mTv_comment;
//            ImageView mIv_profileImage;
//            ImageView mIv_image;
//            FrameLayout mFl_type;
//            LikeButton mHeartButton;
//            JCVideoPlayerStandard mJCVP_S;
//
//            public CommonViewHolder(View itemView) {
//                super(itemView);
//                mTv_love = (TextView) itemView.findViewById(R.id.tv_love);
//                mTv_name = (TextView) itemView.findViewById(R.id.tv_name);
//                mTv_createTime = (TextView) itemView.findViewById(R.id.tv_createTime);
//                mTv_text = (TextView) itemView.findViewById(R.id.tv_text);
//                mTv_hate = (TextView) itemView.findViewById(R.id.tv_hate);
//                mTv_share = (TextView) itemView.findViewById(R.id.tv_share);
//                mTv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
//                mIv_profileImage = (ImageView) itemView.findViewById(R.id.iv_profileImage);
//                mIv_image = (ImageView) itemView.findViewById(R.id.item_image);
//                mFl_type = (FrameLayout) itemView.findViewById(R.id.fl_type);
//                mHeartButton = (LikeButton) itemView.findViewById(R.id.heart_button);
//                mJCVP_S = (JCVideoPlayerStandard) itemView.findViewById(R.id.JCVP_S);
//
//            }
//        }
//
//        class FootViewHolder extends RecyclerView.ViewHolder {
//
//            private RelativeLayout mLl_root;
//
//            public FootViewHolder(View itemView) {
//                super(itemView);
//                mLl_root = (RelativeLayout) itemView.findViewById(R.id.ll_root);
//            }
//        }
//    }
//}
