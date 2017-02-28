package com.example.well.luochen.net.entity.api;


import com.example.well.luochen.net.rxretrofit.Api.BaseApi;
import com.example.well.luochen.net.rxretrofit.listener.HttpOnNextListener;
import com.example.well.luochen.net.rxretrofit.service.HttpPostService;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * 测试数据
 * Created by WZG on 2016/7/16.
 */
public class SubjectPostApi extends BaseApi {
//    接口需要传入的参数 可自定义不同类型
    private boolean all;
    /*任何你先要传递的参数*/
//    String xxxxx;
//    String xxxxx;
//    String xxxxx;
//    String xxxxx;


    /**
     * 默认初始化需要给定回调和rx周期类
     * 可以额外设置请求设置加载框显示，回调等（可扩展）
     * @param listener
     * @param rxAppCompatActivity
     */
    public SubjectPostApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        super(listener,rxAppCompatActivity);
        setBaseUrl("http://www.izaodao.com/Api/");
        setShowProgress(true);
        setCancel(true);
        setCache(true);
        setMothed("AppFiftyToneGraph/videoLink");
        setCookieNetWorkTime(60);
        setCookieNoNetWorkTime(24*60*60);
    }



    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpPostService service = retrofit.create(HttpPostService.class);
        return service.getAllVedioBys(isAll());
    }
}
