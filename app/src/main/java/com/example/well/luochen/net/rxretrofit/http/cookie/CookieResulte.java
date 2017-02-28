package com.example.well.luochen.net.rxretrofit.http.cookie;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * post請求緩存数据
 */
@Entity
public class CookieResulte {
    /*url*/
    private String url;
    /*返回结果*/
    private String resulte;
    /*时间*/
    private long time;

    @Generated(hash = 770401651)
    public CookieResulte(String url, String resulte, long time) {
        this.url = url;
        this.resulte = resulte;
        this.time = time;
    }

    @Generated(hash = 2104390000)
    public CookieResulte() {
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResulte() {
        return this.resulte;
    }

    public void setResulte(String resulte) {
        this.resulte = resulte;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
