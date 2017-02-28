package com.example.well.luochen.net.rxretrofit.service;


import com.example.well.luochen.net.entity.resulte.RetrofitEntity;
import com.example.well.luochen.net.entity.resulte.ServiceInfoResulte;
import com.example.well.luochen.net.entity.resulte.SubjectResulte;
import com.example.well.luochen.net.entity.resulte.Temp;
import com.example.well.luochen.net.rxretrofit.Api.BaseResultEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 测试接口service-post相关
 */

public interface HttpPostService {
    @POST("AppFiftyToneGraph/videoLink")
    Call<RetrofitEntity> getAllVedio(@Body boolean once_no);

    @POST("AppFiftyToneGraph/videoLink")
    Observable<RetrofitEntity> getAllVedioBy(@Body boolean once_no);

    @GET("AppFiftyToneGraph/videoLink/{once_no}")
    Observable<BaseResultEntity<List<SubjectResulte>>> getAllVedioBys(@Query("once_no") boolean once_no);

    @GET("queryServer")
    Observable<BaseResultEntity<ServiceInfoResulte>> getServeiceInfo(@Query("softProduct") String softProduct, @Query("lang") String lang);

    @GET("females/temps")
    Observable<BaseResultEntity<List<Temp>>> getTemp(@Query("cellphone") String cellphone, @Query("beginTime") long beginTime, @Query("endTime") long endTime, @Query("unit") String day);

}
