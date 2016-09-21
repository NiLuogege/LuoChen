package com.example.well.luochen.mode.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import com.example.well.luochen.R;
import com.example.well.luochen.jcvideoplayer.JCVideoPlayer;
import com.example.well.luochen.mode.activity.MainActivity;
import com.example.well.luochen.view.CustomViewPager;
import com.gigamole.navigationtabbar.ntb.NavigationTabBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * Created by Well on 2016/7/14.
 */
@EFragment(R.layout.fragment_content)
public class ContentFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    @ViewById
    CustomViewPager vp;
    @ViewById
    NavigationTabBar ntb_vertical;

    private MainActivity mActivity;

    private int DismissNtb = 0;


    @AfterViews
    void initAfterView() {
        initViewPager();
        initTabBar();
        initListener();
    }

    private void initListener() {
        vp.addOnPageChangeListener(this);
    }

    @Click
    void backimg() {
        mActivity.showLeft();
    }

    private void initTabBar() {
        String[] colors = getResources().getStringArray(R.array.vertical_ntb);
        ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(mActivity, R.drawable.shape_translation),
                        Color.parseColor(colors[0]))
//                        .selectedIcon( ContextCompat.getDrawable(mActivity,android.R.color.transparent))
                        .build()
        );

        models.add(
                new NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(mActivity, R.drawable.shape_translation),
                        Color.parseColor(colors[1]))
//                        .selectedIcon( ContextCompat.getDrawable(mActivity,R.drawable.ic_eighth))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(mActivity, R.drawable.shape_translation),
                        Color.parseColor(colors[2]))
//                        .selectedIcon( ContextCompat.getDrawable(mActivity,R.drawable.ic_eighth))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(mActivity, R.drawable.shape_translation),
                        Color.parseColor(colors[3]))
//                        .selectedIcon( ContextCompat.getDrawable(mActivity,R.drawable.ic_eighth))
                        .build()
        );
        ntb_vertical.setBgColor(ContextCompat.getColor(mActivity, android.R.color.transparent));
        ntb_vertical.setModels(models);
        ntb_vertical.setViewPager(vp);

    }

    private void initViewPager() {
        mActivity = (MainActivity) getActivity();
        FragmentManager sfm = mActivity.getSupportFragmentManager();

        ArrayList<Fragment> list = new ArrayList<>();
        com.example.well.luochen.mode.fragment.ImageFragment ImageFragment = ImageFragment_.builder().build();
        MusicFragment_ musicFragment_ = new MusicFragment_();
        BDJFragment_ bdjFragment = new BDJFragment_();
        DemoFragment_ demoFragment1 = new DemoFragment_();
        DemoFragment_ demoFragment2 = new DemoFragment_();
        DemoFragment_ demoFragment3 = new DemoFragment_();

        list.add(ImageFragment);
        list.add(musicFragment_);
        list.add(bdjFragment);
        list.add(demoFragment1);
        list.add(demoFragment2);
        list.add(demoFragment3);

        ContentPagerAdapter contentPagerAdapter = new ContentPagerAdapter(sfm, list);
        vp.setAdapter(contentPagerAdapter);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public class ContentPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> list;

        public ContentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {

            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}
