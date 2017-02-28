package com.example.well.luochen.net.entity.api;


import com.example.well.luochen.net.rxretrofit.Api.BaseApi;
import com.example.well.luochen.net.rxretrofit.listener.HttpOnNextListener;
import com.example.well.luochen.net.rxretrofit.service.HttpPostService;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Well on 2017/2/15.
 */

public class AppUploadApi extends BaseApi {
    private String cellphone;
    private long beginTime;
    private long endTime;
    private String unit;



    public AppUploadApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        super(listener, rxAppCompatActivity);
        setBaseUrl("http://192.168.121.250:8080/");
        setCache(true);//先设置为false 设置为true的话完成一个请求后 在进行下一个请求是会出错
        setMothed("AppFiftyToneGraph/heh");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpPostService httpPostService = retrofit.create(HttpPostService.class);
        return  httpPostService.getTemp(getCellphone(),getBeginTime(),getEndTime(), getUnit());
    }


    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
