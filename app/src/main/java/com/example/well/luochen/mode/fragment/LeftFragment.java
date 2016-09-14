package com.example.well.luochen.mode.fragment;


import com.example.well.luochen.R;
import com.example.well.luochen.utils.LogUtils;
import com.example.well.luochen.utils.Settings;
import com.rey.material.widget.Switch;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Well on 2016/7/14.
 */

@EFragment(R.layout.fragment_left)
public class LeftFragment extends BaseFragment {
    @ViewById
    Switch saveFlow;


    @AfterViews
    void  initAfterViews()
    {
        boolean isSaveFlow =mACache.getAsBoolean(Settings.IsSaveFlow);
        saveFlow.setChecked(isSaveFlow);
        initListener();

    }

    private void initListener() {
        saveFlow.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch view, boolean checked) {
                LogUtils.logError("checked="+checked);
                mACache.put(Settings.IsSaveFlow,checked);
            }
        });
    }


}
