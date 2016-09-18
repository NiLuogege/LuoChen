package com.example.well.luochen.mode.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.example.well.luochen.R;
import com.example.well.luochen.downloadMusic.DownloadInfo;
import com.example.well.luochen.downloadMusic.DownloadManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Well on 2016/7/15.
 */
@EFragment(R.layout.fragment_demo)
public class DemoFragment extends BaseFragment {
    @ViewById
    ImageView imageView00;
    private Bitmap bitmap;
    private Bitmap updateBitmap;
    private Canvas canvas;
    private Paint paint;
    private ColorMatrix mCm;
    private Matrix mMatrix;
    float[] fl = new float[16];
    private FragmentActivity mActivity;


    @AfterViews
    void initAfterViews()
    {

        mActivity = getActivity();


    }

    @Click
    void btn()
    {
        Drawable drawable = changeDrawableColor(R.drawable.im_specialist, R.color.hehe2, mActivity);
        imageView00.setImageDrawable(drawable);
    }



    @Click
    void btn_download()
    {
        DownloadManager instance = DownloadManager.getInstance();
        DownloadInfo downloadInfo = new DownloadInfo();
//        downloadInfo.setDownloadURL("http://dl.stream.qqmusic.qq.com/104133518.mp3");
//        downloadInfo.setDownloadURL("http://stream10.qqmusic.qq.com/34833285.mp3");
        downloadInfo.setDownloadURL("http://ws.stream.qqmusic.qq.com/4833285.m4a?fromtag=46");
        downloadInfo= DownloadInfo.downloadPath(downloadInfo);
        instance.download(downloadInfo);
    }


//    public Bitmap changeImageColor(int drawableRes, int colorRes, Context context)
//    {
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.im_specialist);
//        Bitmap updateBitmap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), bitmap.getConfig());
//        Canvas canvas = new Canvas(updateBitmap);
//        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿的画笔
//        ColorMatrix cm = new ColorMatrix();
//        paint.setColorFilter(new ColorMatrixColorFilter(cm));
//        paint.setColor(Color.BLACK);
//        paint.setAntiAlias(true);
//        Matrix matrix = new Matrix();
//        cm.set(new float[] {  255/255f, 0, 0, 0, 0,// 红色值
//                0, 121/255f, 0, 0, 0,// 绿色值
//                0, 0,151/255f, 0, 0,// 蓝色值
//                0, 0, 0, 1, 0 // 透明度
//        });
//        paint.setColorFilter(new ColorMatrixColorFilter(cm));
//        canvas.drawBitmap(bitmap, matrix, paint);
//
//
//        return updateBitmap;
//    }

    public Drawable changeDrawableColor(int drawableRes, int colorRes, Context context)
    {
        int color = context.getResources().getColor(colorRes);
        float r = Color.red(color) / 255f;
        float g = Color.green(color) / 255f;
        float b = Color.blue(color) / 255f;


        ColorMatrix cm = new ColorMatrix();
        cm.set(new float[] {  r, 0, 0, 0, 0,// 红色值
                               0, g, 0, 0, 0,// 绿色值
                               0, 0,b, 0, 0,// 蓝色值
                               0, 0, 0, 1, 0 // 透明度
        });
        Drawable drawable = context.getResources().getDrawable(drawableRes);
        drawable.setColorFilter(new ColorMatrixColorFilter(cm));

        return drawable;
    }

//    public static Drawable changeDrawableColor(int drawableRes, int colorRes, Context context) {
//        //Convert drawable res to bitmap
//        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableRes);
//        final Bitmap resultBitmap = Bitmap.createBitmap(bitmap, 0, 0,
//                bitmap.getWidth() - 1, bitmap.getHeight() - 1);
//        final Paint p = new Paint();
//        final Canvas canvas = new Canvas(resultBitmap);
//        canvas.drawBitmap(resultBitmap, 0, 0, p);
//
//        //Create new drawable based on bitmap
//        final Drawable drawable = new BitmapDrawable(context.getResources(), resultBitmap);
//        drawable.setColorFilter(new
//                PorterDuffColorFilter(context.getResources().getColor(colorRes), PorterDuff.Mode.MULTIPLY));
//        return drawable;
//    }

}
