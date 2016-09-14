package com.example.well.luochen.adapter;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.well.luochen.R;
import com.example.well.luochen.jcvideoplayer.JCVideoPlayer;
import com.example.well.luochen.jcvideoplayer.JCVideoPlayerStandard;
import com.example.well.luochen.mode.activity.LoadBigImageActivity_;
import com.example.well.luochen.mode.fragment.BDJFragment;
import com.example.well.luochen.mode.fragment.BaseFragment;
import com.example.well.luochen.net.info.BsbdjListinfo;
import com.example.well.luochen.utils.LogUtils;
import com.example.well.luochen.utils.Settings;
import com.example.well.luochen.utils.ToastUtils;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import cn.sharesdk.onekeyshare.themeCustom.ShareModel;
import cn.sharesdk.onekeyshare.themeCustom.SharePopupWindow;

/**
 * Created by Well on 2016/7/28.
 */
public class BDJAdapter extends BaseAdapter {
    private BDJFragment mBdjFragment;
    private int num = 0;

    public BDJAdapter(BDJFragment bdjFragment) {
        mBdjFragment = bdjFragment;
    }

    @Override
    public int getCount() {
        return mBdjFragment.data.size();
    }

    @Override
    public Object getItem(int position) {
        return mBdjFragment.data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder = null;

        if (convertView == null) {
            mHolder = new ViewHolder();

            convertView = View.inflate(mBdjFragment.mActivity, R.layout.item_activity_main, null);

            mHolder.mTv_love = (TextView) convertView.findViewById(R.id.tv_love);
            mHolder.mTv_name = (TextView) convertView.findViewById(R.id.tv_name);
            mHolder.mTv_createTime = (TextView) convertView.findViewById(R.id.tv_createTime);
            mHolder.mTv_text = (TextView) convertView.findViewById(R.id.tv_text);
            mHolder.mTv_hate = (TextView) convertView.findViewById(R.id.tv_hate);
            mHolder.mTv_share = (TextView) convertView.findViewById(R.id.tv_share);
            mHolder.mTv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
            mHolder.mIv_profileImage = (ImageView) convertView.findViewById(R.id.iv_profileImage);
            mHolder.mIv_image = (ImageView) convertView.findViewById(R.id.item_image);
            mHolder.mFl_type = (FrameLayout) convertView.findViewById(R.id.fl_type);
            mHolder.mHeartButton = (LikeButton) convertView.findViewById(R.id.heart_button);
            mHolder.mJCVP_S = (JCVideoPlayerStandard) convertView.findViewById(R.id.JCVP_S);
            mHolder.mRl_root = (RelativeLayout) convertView.findViewById(R.id.rl_root);


            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }


        num++;
        if (num == 2) {
            JCVideoPlayer.releaseAllVideos();
            num = 0;
        }

        if (position == mBdjFragment.data.size() - 1) {
            mBdjFragment.isLoadMore = true;//标记为加载更多动作
            mBdjFragment.getBsbdqj();
        }


        final BsbdjListinfo listinfo = mBdjFragment.data.get(position);

        mHolder.mTv_love.setText(listinfo.love);
        mHolder.mTv_name.setText(listinfo.name);
        mHolder.mTv_createTime.setText(listinfo.create_time);
        mHolder.mTv_text.setText(listinfo.text);
        mHolder.mTv_hate.setText(listinfo.hate);
        ImageLoader.getInstance().displayImage(listinfo.profile_image, mHolder.mIv_profileImage);

        boolean wifi = BaseFragment.isWifi();
        boolean isSaveFlow = mBdjFragment.mACache.getAsBoolean(Settings.IsSaveFlow);

        if (wifi && isSaveFlow) {
            ToastUtils.show(mBdjFragment.mActivity, "现在已经是省流量模式啦");
        }

        if (TextUtils.equals(mBdjFragment.data.get(position).type, "10"))//GIF 图片,和一般图片
        {
            mHolder.mFl_type.setVisibility(View.VISIBLE);
            mHolder.mIv_image.setVisibility(View.VISIBLE);
            mHolder.mJCVP_S.setVisibility(View.INVISIBLE);

            Glide.with(mBdjFragment.mActivity).load(listinfo.image0).dontAnimate().error(R.drawable.icon_moren).diskCacheStrategy(DiskCacheStrategy.SOURCE).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    LogUtils.logError("加载失败" + " e=" + e.toString() + " model=" + model);
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    return false;
                }
            }).into(mHolder.mIv_image);

        } else if (TextUtils.equals(listinfo.type, "41"))//视频
        {
            mHolder.mFl_type.setVisibility(View.VISIBLE);
            mHolder.mIv_image.setVisibility(View.INVISIBLE);
            mHolder.mJCVP_S.setVisibility(View.VISIBLE);

            mHolder.mJCVP_S.setUp(listinfo.video_uri, "");
            mHolder.mJCVP_S.setPosition(position);
            mHolder.mJCVP_S.titleTextView.setVisibility(View.GONE);
            mHolder.mJCVP_S.thumbImageView.setImageResource(R.drawable.shape_background_gray);

        } else if (TextUtils.equals(listinfo.type, "29"))//纯文字
        {
            mHolder.mFl_type.setVisibility(View.GONE);
            mHolder.mIv_image.setVisibility(View.GONE);
            mHolder.mJCVP_S.setVisibility(View.GONE);
        }
        /**
         * 点赞
         */

        mHolder.mHeartButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                ToastUtils.show(mBdjFragment.mActivity, "你已经点赞了");
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                ToastUtils.show(mBdjFragment.mActivity, "你已经取消点赞了");
            }
        });
        /**
         * 看大图
         */
        mHolder.mIv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadBigImageActivity_.intent(mBdjFragment.mActivity).url(listinfo.image0).start();
            }
        });

        /**
         * 分享
         */
        mHolder.mTv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                        showShare();
                mBdjFragment.mShare = new SharePopupWindow(mBdjFragment.mActivity);
                mBdjFragment.mShare.setPlatformActionListener(mBdjFragment);
                ShareModel model = new ShareModel();
                model.setImageUrl(listinfo.image0);
                model.setText(listinfo.text);
                model.setTitle(listinfo.text);
                model.setUrl(listinfo.weixin_url);
                mBdjFragment.mShare.initShareParams(model);
                mBdjFragment.mShare.showShareWindow();
                // 显示窗口 (设置layout在PopupWindow中显示的位置)
                LogUtils.logError("mShare=" + mBdjFragment.mShare + " ll_root=" + mBdjFragment.ll_root);

                mBdjFragment.mShare.showAtLocation(mBdjFragment.ll_root, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });


        return convertView;
    }


    class ViewHolder {
        TextView mTv_love;
        TextView mTv_name;
        TextView mTv_createTime;
        TextView mTv_text;
        TextView mTv_hate;
        TextView mTv_share;
        TextView mTv_comment;
        ImageView mIv_profileImage;
        ImageView mIv_image;
        FrameLayout mFl_type;
        LikeButton mHeartButton;
        JCVideoPlayerStandard mJCVP_S;
        RelativeLayout mRl_root;

    }
}
