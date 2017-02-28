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

public class GetServiceInfoApi extends BaseApi {
    private String projectName;
    private String lang;



    public GetServiceInfoApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        super(listener, rxAppCompatActivity);
        setBaseUrl("http://192.168.121.250:20000/");
        setCache(true);//先设置为false 设置为true的话完成一个请求后 在进行下一个请求是会出错
        setMothed("AppFiftyToneGraph/lal");
    }



    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpPostService httpPostService = retrofit.create(HttpPostService.class);
        return httpPostService.getServeiceInfo(getProjectName(),getLang());
    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
