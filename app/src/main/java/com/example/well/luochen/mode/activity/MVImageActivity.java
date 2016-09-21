package com.example.well.luochen.mode.activity;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.well.luochen.R;
import com.example.well.luochen.net.HttpListener;
import com.example.well.luochen.net.data.MVImageResponse;
import com.example.well.luochen.net.info.MVImageBody;
import com.example.well.luochen.net.info.MVImageInfo;
import com.example.well.luochen.utils.GlideUtils;
import com.example.well.luochen.utils.RequestWhat;
import com.example.well.luochen.utils.SpacesItemDecoration;
import com.example.well.luochen.utils.URLUtils;
import com.yolanda.nohttp.rest.Response;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * Created by Well on 2016/9/21.
 */
@EActivity(R.layout.activity_image_mv)
public class MVImageActivity extends BaseActivity {
    @ViewById
    RecyclerView list_rv;

    private int page = 1;
    private ArrayList<MVImageInfo> mNewslist;
    private MVImageAdapter mAdapter;

    @AfterViews
    void initAfterView() {
        list_rv.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        list_rv.addItemDecoration(new SpacesItemDecoration(16));
        initData();
    }

    private void initData() {
        String url = String.format(URLUtils.URL_MV + "?showapi_appid=20775&num=20&page=%s&showapi_sign=d159d4a9edd649c9b669386b0170babc", page);
        requestGet(RequestWhat.What_3, url, MVImageResponse.class, new HttpListener<MVImageResponse>() {
            @Override
            public void onSucceed(int what, Response<MVImageResponse> response) {
                MVImageResponse mvImageResponse = response.get();
                if (null != mvImageResponse) {
                    MVImageBody showapi_res_body = mvImageResponse.showapi_res_body;
                    if (null != showapi_res_body) {
                        int code = showapi_res_body.code;
                        if (code == 200)//访问成功
                        {
                            mNewslist = showapi_res_body.newslist;

                            if (null==mAdapter){
                                mAdapter = new MVImageAdapter();
                                list_rv.setAdapter(mAdapter);
                            }else{
                                mAdapter.notifyDataSetChanged();
                            }

                        }
                    }
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish() {

            }
        }, true);
    }

    public class MVImageAdapter extends RecyclerView.Adapter<MVImageAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(MVImageActivity.this, R.layout.item_activity_image_mv, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            MVImageInfo mvImageInfo = mNewslist.get(position);
            GlideUtils.displayImageView(MVImageActivity.this,mvImageInfo.picUrl,holder.mIv_item,R.drawable.he);
        }

        @Override
        public int getItemCount() {
            return mNewslist.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private ImageView mIv_item;

            public ViewHolder(View itemView) {
                super(itemView);
                 mIv_item = (ImageView) itemView.findViewById(R.id.iv_item);
            }
        }
    }
}
