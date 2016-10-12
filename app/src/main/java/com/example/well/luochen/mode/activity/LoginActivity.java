package com.example.well.luochen.mode.activity;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.well.luochen.R;
import com.example.well.luochen.utils.ToastUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Well on 2016/9/30.
 */
@EActivity(R.layout.activity_loagin)
public class LoginActivity extends BaseActivity {
    @ViewById
    RelativeLayout ll;
    @ViewById
    LinearLayout ll_register, ll_login;
    @ViewById
    TextInputEditText tiet_name_register, tiet_pwd_register, tiet_re_pwd, tiet_name, tiet_pwd;


    @AfterViews
    void initAfterViews() {
//        String name = tiet_name.getText().toString();
//        String pwd = tiet_pwd.getText().toString();

    }

    @Click
    void btn_enter() {
//        MainActivity_.intent(this).start();
        Snackbar.make(ll, "gaa", Snackbar.LENGTH_LONG).setAction("干", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = tiet_name.getText().toString();
                String pwd = tiet_pwd.getText().toString();
                ToastUtils.show(LoginActivity.this, "name=" + name + " pwd=" + pwd);
            }
        }).show();
    }

    @Click
    void btn_snackbar() {

        Snackbar.make(ll, "gaa", Snackbar.LENGTH_LONG).setAction("干", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(LoginActivity.this, "toast");
            }
        }).show();
    }
}
