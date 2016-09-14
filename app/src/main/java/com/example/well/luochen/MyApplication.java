package com.example.well.luochen;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

import com.example.well.luochen.mode.activity.MainActivity;
import com.example.well.luochen.net.NoHttpRequest;
import com.example.well.luochen.utils.SdUtil;
import com.example.well.luochen.utils.SystemOpt;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * 扩展application
 *
 * @author Luo
 */
public class MyApplication extends Application {
    public int heightPixels = 800;
    public int widthPixels = 480;
    public SystemOpt systemOpt = SystemOpt.getInstance();

    public static MainActivity mainActivity;

    private static MyApplication application;

    public static MyApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        initSystem();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void setSystemBar(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(activity, true);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(ContextCompat.getColor(this, color));//通知栏所需颜色
//			tintManager.setStatusBarAlpha(0f);
            tintManager.statusTextColor(activity, true);
        }
    }

    public void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void initSystem() {
        NoHttpRequest.getInstance().init(this);
        systemOpt.init(this);//读取系统信息
        widthPixels = systemOpt.widthPixels;
        heightPixels = systemOpt.heightPixels;

        initImageLoader(this);

    }

    /***
     * 密码规则
     *
     * @param password
     * @return
     */
    public boolean isValidPassword(String password) {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }


    /**
     * 初始化ImageLoader
     */
    @SuppressWarnings("deprecation")
    public static void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, SdUtil.sddisk + SdUtil.IMG_CAHCE);//获取到缓存的目录地址
        //创建配置ImageLoader(所有的选项都是可选的,只使用那些你真的想定制)，这个可以设定在APPLACATION里面，设置为全局的配置参数
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024 / 2);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(context)
                //.memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                //.discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null) // Can slow ImageLoader, use it carefully (Better don't use it)设置缓存的详细信息，最好不要设置这个
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(maxMemory)) // You can pass your own memory cache implementation你可以通过自己的内存缓存实现
                //.memoryCacheSize(2 * 1024 * 1024)
                ///.discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                //.discCacheFileNameGenerator(new HashCodeFileNameGenerator())//将保存的时候的URI名称用HASHCODE加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                //.discCacheFileCount(100) //缓存的File数量
                .discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径
                //.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                //.imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);//全局初始化此配置
    }

}
