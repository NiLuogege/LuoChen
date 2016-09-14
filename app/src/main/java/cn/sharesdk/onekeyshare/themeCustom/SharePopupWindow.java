package cn.sharesdk.onekeyshare.themeCustom;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.example.well.luochen.R;
import com.example.well.luochen.utils.LogUtils;
import com.example.well.luochen.utils.ToastUtils;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * TODO<������>
 *
 * @data: 2014-7-21 ����2:45:38
 * @version: V1.0
 */

public class SharePopupWindow extends PopupWindow {

    private Context context;
    private PlatformActionListener platformActionListener;
    private ShareParams shareParams;

    public SharePopupWindow(Context cx) {
        this.context = cx;
    }

    public PlatformActionListener getPlatformActionListener() {
        return platformActionListener;
    }

    public void setPlatformActionListener(
            PlatformActionListener platformActionListener) {
        this.platformActionListener = platformActionListener;
    }

    public void showShareWindow() {
        View view = LayoutInflater.from(context).inflate(R.layout.share_layout, null);
        GridView gridView = (GridView) view.findViewById(R.id.share_gridview);
        ShareAdapter adapter = new ShareAdapter(context);
        gridView.setAdapter(adapter);

        ImageView btn_cancel = (ImageView) view.findViewById(R.id.btn_cancel);
        // ȡ����ť
        btn_cancel.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // ���ٵ�����
                dismiss();
            }
        });

        // ����SelectPicPopupWindow��View
        this.setContentView(view);
        // ����SelectPicPopupWindow��������Ŀ�
        this.setWidth(LayoutParams.MATCH_PARENT);
        // ����SelectPicPopupWindow��������ĸ�
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // ����SelectPicPopupWindow��������ɵ��
        this.setFocusable(true);
        // ����SelectPicPopupWindow�������嶯��Ч��
        this.setAnimationStyle(R.style.AnimBottom);
        // ʵ����һ��ColorDrawable��ɫΪ��͸��
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // ����SelectPicPopupWindow��������ı���
        this.setBackgroundDrawable(dw);

        gridView.setOnItemClickListener(new ShareItemClickListener(this));

    }

    private class ShareItemClickListener implements OnItemClickListener {
        private PopupWindow pop;

        public ShareItemClickListener(PopupWindow pop) {
            this.pop = pop;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            share(position);
            pop.dismiss();

        }
    }

    /**
     * ����
     *
     * @param position
     */
    private void share(int position) {

        if (position == 0) {//微信
            ToastUtils.show(context, "hhehe ");
            weixin();
        } else if (position == 1) {//朋友圈
            share_CircleFriend();
        } else if (position == 2) {

        } else if (position == 3) {
        } else if (position == 4) {
        } else if (position == 5) {
        } else {
            Platform plat = null;
            plat = ShareSDK.getPlatform(context, getPlatform(position));
            if (platformActionListener != null) {
                plat.setPlatformActionListener(platformActionListener);
            }

            plat.share(shareParams);
        }
    }


    /**
     * ��ʼ���������
     *
     * @param shareModel
     */
    public void initShareParams(ShareModel shareModel) {
        if (shareModel != null) {
            ShareParams sp = new ShareParams();
            sp.setShareType(Platform.SHARE_TEXT);
            sp.setShareType(Platform.SHARE_WEBPAGE);

            sp.setTitle(shareModel.getText());
            sp.setText(shareModel.getText());
            sp.setUrl(shareModel.getUrl());
            sp.setImageUrl(shareModel.getImageUrl());
            shareParams = sp;
        }
    }

    /**
     * ��ȡƽ̨
     *
     * @param position
     * @return
     */
    private String getPlatform(int position) {
        String platform = "";
        switch (position) {
            case 0:
                platform = "Wechat";
                break;
            case 1:
                platform = "QQ";
                break;
            case 2:
                platform = "SinaWeibo";
                break;
            case 3:
                platform = "WechatMoments";
                break;
            case 4:
                platform = "QZone";
                break;
            case 5:
                platform = "ShortMessage";
                break;
        }
        return platform;
    }

    /**
     * ����QQ�ռ�
     */
    private void qzone() {
        ShareParams sp = new ShareParams();
        sp.setTitle(shareParams.getTitle());
        sp.setTitleUrl(shareParams.getUrl()); // ����ĳ�����
        sp.setText(shareParams.getText());
        sp.setImageUrl(shareParams.getImageUrl());
        sp.setComment("�ҶԴ˷������ݵ�����");
        sp.setSite(shareParams.getTitle());
        sp.setSiteUrl(shareParams.getUrl());

        Platform qzone = ShareSDK.getPlatform(context, "QZone");

        qzone.setPlatformActionListener(platformActionListener); // ���÷����¼��ص� //
        // ִ��ͼ�ķ���
        qzone.share(sp);
    }

    private void qq() {
        ShareParams sp = new ShareParams();
        sp.setTitle(shareParams.getTitle());
        sp.setTitleUrl(shareParams.getUrl()); // ����ĳ�����
        sp.setText(shareParams.getText());
        sp.setImageUrl(shareParams.getImageUrl());
        sp.setComment("我对此分享内容的评论");
        sp.setSite(shareParams.getTitle());
        sp.setSiteUrl(shareParams.getUrl());
        Platform qq = ShareSDK.getPlatform(context, "QQ");
        qq.setPlatformActionListener(platformActionListener);
        qq.share(sp);
    }


    private void share_CircleFriend() {
//        if (!Utils.isHaveApp("com.tencent.mm", this)) {
//            Utils.showToast(this, "请先安装微信");
//            isClick = false;
//            return;
//        }

        LogUtils.logError(shareParams.toString());

        Platform wechat = ShareSDK.getPlatform(context, WechatMoments.NAME);
        WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);// 一定要设置分享属性
        sp.setTitle("LuoChen的分析");
        sp.setText(shareParams.getText());
        sp.setImageUrl(shareParams.getImageUrl());
        sp.setImagePath(null);
        sp.setUrl(shareParams.getUrl());

        wechat.setPlatformActionListener(platformActionListener); // 设置分享事件回调
        // 执行图文分享
        wechat.share(sp);
    }

    private void weixin() {
//        ShareParams sp = new ShareParams();
//        sp.setTitle(shareParams.getTitle());
//        sp.setTitleUrl(shareParams.getUrl());
//        sp.setText(shareParams.getText());
//        sp.setImageUrl(shareParams.getImageUrl());
//        sp.setComment("我对此分享内容的评论");
//        sp.setSite(shareParams.getTitle());
//        sp.setSiteUrl(shareParams.getUrl());
//        Platform wechat = ShareSDK.getPlatform(context, "Wechat");
//        wechat.setPlatformActionListener(platformActionListener);
//        wechat.share(sp);

        LogUtils.logError(shareParams.toString());

        Platform wechat = ShareSDK.getPlatform(context, Wechat.NAME);
        ShareParams sp = new ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);// 一定要设置分享属性

        sp.setTitle("我是Title");
        sp.setText("我是分享文字");
        sp.setUrl("http://www.budejie.com/detail-19430255.html");
//        sp.setImageData(null);
//        sp.setImageUrl(shareParams.getImageUrl());
//        sp.setImagePath(null);

        wechat.setPlatformActionListener(platformActionListener); // 设置分享事件回调
        // 执行图文分享
        wechat.share(sp);

    }

    /**
     * ��������
     */
    private void shortMessage() {
        ShareParams sp = new ShareParams();
        sp.setAddress("");
        sp.setText(shareParams.getText() + "������ַ��" + shareParams.getUrl() + "���ܸ���Ŷ��");

        Platform circle = ShareSDK.getPlatform(context, "ShortMessage");
        circle.setPlatformActionListener(platformActionListener); // ���÷����¼��ص�
        // ִ��ͼ�ķ���
        circle.share(sp);
    }



}
