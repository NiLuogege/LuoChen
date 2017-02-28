package com.example.well.luochen.net.rxretrofit.service;


import com.example.well.luochen.net.entity.resulte.UploadResulte;
import com.example.well.luochen.net.rxretrofit.Api.BaseResultEntity;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * 测试接口service-上传相关
 */

public interface HttpUploadService {
    /*上传文件*/
    @Multipart
    @POST("AppYuFaKu/uploadHeadImg")
    Observable<BaseResultEntity<UploadResulte>> uploadImage(@Part("uid") RequestBody uid, @Part("auth_key") RequestBody auth_key,
                                                            @Part MultipartBody.Part file);
}
