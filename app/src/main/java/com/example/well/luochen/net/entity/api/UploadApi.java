package com.example.well.luochen.net.entity.api;


import com.example.well.luochen.net.rxretrofit.Api.BaseApi;
import com.example.well.luochen.net.rxretrofit.listener.HttpOnNextListener;
import com.example.well.luochen.net.rxretrofit.service.HttpUploadService;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * 上传请求api
 * Created by WZG on 2016/10/20.
 */

public class UploadApi extends BaseApi {
    /*需要上传的文件*/
    private MultipartBody.Part part;


    public UploadApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        super(listener, rxAppCompatActivity);
        setShowProgress(true);
        setMothed("AppFiftyToneGraph/videoLink");
    }

    public MultipartBody.Part getPart() {
        return part;
    }

    public void setPart(MultipartBody.Part part) {
        this.part = part;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpUploadService service = retrofit.create(HttpUploadService.class);
        RequestBody uid= RequestBody.create(MediaType.parse("text/plain"), "4811420");
        RequestBody key = RequestBody.create(MediaType.parse("text/plain"), "cfed6cc8caad0d79ea56d917376dc4df");
        return service.uploadImage(uid,key,getPart());
    }

}
