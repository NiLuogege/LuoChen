package com.example.well.luochen.mode.fragment;

import android.widget.ImageView;

import com.example.well.luochen.R;
import com.example.well.luochen.mode.activity.MVImageActivity_;
import com.example.well.luochen.mode.activity.MainActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Well on 2016/9/21.
 */
@EFragment(R.layout.fragment_image)
public class ImageFragment extends BaseFragment {
    @ViewById
    ImageView iv_mv, iv_me, iv_chao, iv_dian;
    private MainActivity mActivity;

    @AfterViews
    void initAfterView() {
        mActivity = (MainActivity) getActivity();
        String url_mv = "http://m.xxxiao.com/wp-content/uploads/sites/3/2015/04/m.xxxiao.com_60062ba95a4dd80a711e82cdf57dc5ab-760x500.jpg";
        String url_me = "http://b100.photo.store.qq.com/psb?/V13kPo3N27Z0ho/k8G6NSE9xBQEXkVXveMkmLF9b7Qfq5SKxMsOYTQ1fAA!/b/dGQAAAAAAAAA&bo=IAMrBLAEQAYFCNU!&rf=viewer_4";
        String url_chao = "http://b100.photo.store.qq.com/psb?/V13kPo3N27Z0ho/k8G6NSE9xBQEXkVXveMkmLF9b7Qfq5SKxMsOYTQ1fAA!/b/dGQAAAAAAAAA&bo=IAMrBLAEQAYFCNU!&rf=viewer_4";
        String url_dian = "http://b100.photo.store.qq.com/psb?/V13kPo3N27Z0ho/k8G6NSE9xBQEXkVXveMkmLF9b7Qfq5SKxMsOYTQ1fAA!/b/dGQAAAAAAAAA&bo=IAMrBLAEQAYFCNU!&rf=viewer_4";
        ImageLoader.getInstance().displayImage(url_mv, iv_mv);
        ImageLoader.getInstance().displayImage(url_me, iv_me);
        ImageLoader.getInstance().displayImage(url_chao, iv_chao);
        ImageLoader.getInstance().displayImage(url_dian, iv_dian);
    }

    @Click
    void iv_mv() {
        MVImageActivity_.intent(mActivity).start();
    }

    @Click
    void iv_me() {

    }

    @Click
    void iv_chao() {

    }

    @Click
    void iv_dian() {

    }
}
