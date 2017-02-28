package com.example.well.luochen.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.MediaMetadataRetriever;
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
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.example.well.luochen.R;
import com.example.well.luochen.jcvideoplayer.JCVideoPlayer;
import com.example.well.luochen.jcvideoplayer.JCVideoPlayerStandard;
import com.example.well.luochen.mode.fragment.BDJFragment;
import com.example.well.luochen.mode.fragment.BaseFragment;
import com.example.well.luochen.net_okhttp.info.BsbdjListinfo;
import com.example.well.luochen.utils.ActivityCompatUtils;
import com.example.well.luochen.utils.FileHelper;
import com.example.well.luochen.utils.ImageHelper;
import com.example.well.luochen.utils.Kit;
import com.example.well.luochen.utils.LogUtils;
import com.example.well.luochen.utils.MD5;
import com.example.well.luochen.utils.Settings;
import com.example.well.luochen.utils.ToastUtils;
import com.example.well.luochen.utils.download.FileDownloadThread;
import com.example.well.luochen.view.PinchImageView;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import cn.sharesdk.onekeyshare.themeCustom.ShareModel;
import cn.sharesdk.onekeyshare.themeCustom.SharePopupWindow;

/**
 * Created by Well on 2016/7/28.
 */
public class BDJAdapter extends BaseAdapter {
    private BDJFragment mBdjFragment;
    private int num = 0;

    String filePath = "";
    String mFileName = "";

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
            mHolder.mIv_image = (PinchImageView) convertView.findViewById(R.id.item_image);
            mHolder.mFl_type = (FrameLayout) convertView.findViewById(R.id.fl_type);
            mHolder.mHeartButton = (LikeButton) convertView.findViewById(R.id.heart_button);
            mHolder.mJCVP_S = (JCVideoPlayerStandard) convertView.findViewById(R.id.JCVP_S);
            mHolder.mRl_root = (RelativeLayout) convertView.findViewById(R.id.rl_root);
            mHolder.mRl_huge_image = (RelativeLayout) convertView.findViewById(R.id.rl_huge_image);


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

        final PinchImageView iv = mHolder.mIv_image;
        final ViewHolder finalMHolder = mHolder;

        if (wifi && isSaveFlow) {
            ToastUtils.show(mBdjFragment.mActivity, "现在已经是省流量模式啦");
        }
        iv.setImageResource(R.drawable.load_failed);
        if (TextUtils.equals(mBdjFragment.data.get(position).type, "10"))//GIF 图片,和一般图片
        {
            mHolder.mFl_type.setVisibility(View.VISIBLE);
            mHolder.mIv_image.setVisibility(View.VISIBLE);
            mHolder.mJCVP_S.setVisibility(View.INVISIBLE);
            finalMHolder.mRl_huge_image.setVisibility(View.GONE);

            if(listinfo.image0.endsWith(".gif")){//GIF 图片
                displayMoveImage(listinfo, iv, finalMHolder);
            }else{
                displayImage(listinfo, iv, finalMHolder);
                LogUtils.logError("是静态图"+listinfo.image0);
            }



        } else if (TextUtils.equals(listinfo.type, "41"))//视频
        {
            mHolder.mFl_type.setVisibility(View.VISIBLE);
            mHolder.mIv_image.setVisibility(View.INVISIBLE);
            mHolder.mJCVP_S.setVisibility(View.VISIBLE);
            mHolder.mRl_huge_image.setVisibility(View.GONE);

            ViewGroup.LayoutParams layoutParams = mHolder.mFl_type.getLayoutParams();
            layoutParams.height = (int) Kit.dp2px(200f, mBdjFragment.mActivity.getResources());
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            mHolder.mFl_type.setLayoutParams(layoutParams);

            mHolder.mJCVP_S.setUp(listinfo.video_uri, "");
            mHolder.mJCVP_S.setPosition(position);
            mHolder.mJCVP_S.titleTextView.setVisibility(View.GONE);
            mHolder.mJCVP_S.thumbImageView.setImageResource(R.drawable.shape_background_gray);

//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        URL url = new URL(listinfo.video_uri);
//                        URLConnection urlConnection = url.openConnection();
//                        int contentLength = urlConnection.getContentLength();
//                        LogUtils.logError("视频长度="+contentLength);
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();


            setVideoImage(listinfo, finalMHolder);//显示第一帧

        } else if (TextUtils.equals(listinfo.type, "29"))//纯文字
        {
            mHolder.mFl_type.setVisibility(View.GONE);
            mHolder.mIv_image.setVisibility(View.GONE);
            mHolder.mJCVP_S.setVisibility(View.GONE);
            mHolder.mRl_huge_image.setVisibility(View.GONE);

        } else {//其他
            mHolder.mFl_type.setVisibility(View.GONE);
            mHolder.mIv_image.setVisibility(View.GONE);
            mHolder.mJCVP_S.setVisibility(View.GONE);
            mHolder.mRl_huge_image.setVisibility(View.GONE);
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
//                LoadBigImageActivity_.intent(mBdjFragment.mActivity).url(listinfo.image0).start();
                ActivityCompatUtils.start(mBdjFragment.mActivity,listinfo.image0,iv);
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

    private void displayMoveImage(BsbdjListinfo listinfo, PinchImageView iv, ViewHolder finalMHolder) {
        ViewGroup.LayoutParams layoutParams = iv.getLayoutParams();//imageView的
        ViewGroup.LayoutParams lp = finalMHolder.mFl_type.getLayoutParams();//imageView的父控件

        layoutParams.height=(int) Kit.dp2px(200,mBdjFragment.mActivity.getResources());
        layoutParams.width=ViewGroup.LayoutParams.MATCH_PARENT;

        lp.height=(int) Kit.dp2px(200,mBdjFragment.mActivity.getResources());
        lp.width=ViewGroup.LayoutParams.MATCH_PARENT;

        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        iv.setAdjustViewBounds(true);

        Point displayMetrics = Kit.getDisplayScreenMetrics();
        int x = displayMetrics.x;//屏幕宽度
        Glide
                .with(mBdjFragment.mActivity) // could be an issue!
                .load(listinfo.image0)
                .asGif()
                .into(iv);

        LogUtils.logError("是动图"+listinfo.image0);
    }

    private void displayImage(final BsbdjListinfo listinfo, final PinchImageView iv, final ViewHolder finalMHolder) {
        Glide
                .with(mBdjFragment.mActivity) // could be an issue!
                .load(listinfo.image0)
                .asBitmap()
                .error(R.drawable.icon_moren)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        if (null != e && null != e.toString())
                            LogUtils.logError("加载失败" + " e=" + e.toString() + " model=" + model);

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        int imageWidth = resource.getWidth();//图片宽度
                        int imageHeight = resource.getHeight();//图片高度

                        Point displayMetrics = Kit.getDisplayScreenMetrics();
                        final int x = displayMetrics.x;//屏幕宽度
                        final int y = displayMetrics.y;//屏幕盖度

                        ViewGroup.LayoutParams layoutParams = iv.getLayoutParams();//imageView的
                        ViewGroup.LayoutParams lp = finalMHolder.mFl_type.getLayoutParams();//imageView的父控件
                        if (imageWidth <= x && imageHeight <= y) {//一般图片
                            lp.width = x;
                            lp.height = x * imageHeight / imageWidth;
                            finalMHolder.mFl_type.setLayoutParams(lp);

                            iv.setScaleType(ImageView.ScaleType.FIT_XY);
                            layoutParams.width = x;
                            layoutParams.height = x * imageHeight / imageWidth;
                            iv.setLayoutParams(layoutParams);
                            iv.setImageBitmap(resource);
                        } else if (imageHeight > y * 2.0) {//长图
                            finalMHolder.mRl_huge_image.setVisibility(View.VISIBLE);
                            lp.width = x;
//                                lp.height = y / 2;
                            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                            finalMHolder.mFl_type.setLayoutParams(lp);
                            LogUtils.logError(listinfo.text);
                            layoutParams.width = x;
//                                layoutParams.height = y / 2;
                            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                            iv.setScaleType(ImageView.ScaleType.FIT_XY);
                            iv.setAdjustViewBounds(true);  //加上这两个个属性可以是裁剪的图片 占满屏幕宽度显示

                            iv.setLayoutParams(layoutParams);

                            filePath = listinfo.image0;
                            mFileName = MD5.encodeMD5String(filePath);
                            Runnable runnable = new Runnable() {
                                @Override
                                public void run() {

                                    try {
                                        final Bitmap mBitmap = BitmapFactory.decodeStream(ImageHelper.getImageStream(filePath));
                                        ImageHelper.saveFile(mBitmap, mFileName);

                                        mBdjFragment.mActivity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                setPartOfHuge(mBitmap, x, y, iv);
                                            }
                                        });

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            File file = new File(ImageHelper.ALBUM_PATH + mFileName);
                            if (file.exists()) {
                                Bitmap mBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                setPartOfHuge(mBitmap, x, y, iv);
                            } else {
                                new Thread(runnable).start();
                            }


                        } else {
                            lp.width = x;
                            lp.height = x * imageHeight / imageWidth;
                            finalMHolder.mFl_type.setLayoutParams(lp);

                            iv.setScaleType(ImageView.ScaleType.FIT_XY);
                            layoutParams.width = x;
                            layoutParams.height = x * imageHeight / imageWidth;
                            iv.setLayoutParams(layoutParams);
                            iv.setImageBitmap(resource);
                        }
//                            LogUtils.logError("x=" + x + " y=" + y + " width=" + resource.getWidth() + " height=" + resource.getHeight());

                    }
                });
    }

    private void setVideoImage(BsbdjListinfo listinfo, final ViewHolder finalMHolder) {
        File dir = new File(FileHelper.VIDEO_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String md5String = MD5.encodeMD5String(listinfo.video_uri);
        final File file = new File(dir, md5String);

        if (file.exists()) {
            setFirstFrame(file, finalMHolder);
            LogUtils.logError("已经存在");
        } else {
            LogUtils.logError("没有存在");
            try {
                URL url = new URL(listinfo.video_uri);
                FileDownloadThread fileDownloadThread = new FileDownloadThread(url, file, 0, 1024 * 256, 0);
                fileDownloadThread.setOnDownloadCompletedListener(new FileDownloadThread.OnDownloadCompletedListener() {
                    @Override
                    public void onCompleted() {
                        mBdjFragment.mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setFirstFrame(file, finalMHolder);
                            }
                        });

                    }
                });
                fileDownloadThread.start();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    private void setFirstFrame(File file, ViewHolder finalMHolder) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(file.getAbsolutePath());
        Bitmap frameAtTime = mmr.getFrameAtTime(100);
//        Bitmap frameAtTime = mmr.getFrameAtTime();
        finalMHolder.mJCVP_S.thumbImageView.setImageBitmap(frameAtTime);
    }

    private void setPartOfHuge(Bitmap mBitmap, int x, int y, PinchImageView iv) {
        if (null != mBitmap) {
            Bitmap bitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), y / 6);
            iv.setImageBitmap(bitmap);
        }

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
        PinchImageView mIv_image;
        FrameLayout mFl_type;
        LikeButton mHeartButton;
        JCVideoPlayerStandard mJCVP_S;
        RelativeLayout mRl_root;
        RelativeLayout mRl_huge_image;

    }
}
