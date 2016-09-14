package com.example.well.luochen.net.info;

import java.io.Serializable;

/**
 * Created by Well on 2016/7/26.
 */

public class MusicListInfo implements Serializable {
    public int seconds = 0;
    public String albummid = "";
    public String url = "";

    public String songname = "";
    public int songid = 0;
    public String singername = "";
    public String albumid = "";
    public String albumname = "";
    public String albumpic_big = "";
    public String albumpic_small = "";
    public String downUrl = "";
    public String m4a = "";
    public int singerid = 0;

    @Override
    public String toString() {
        return "MusicListInfo{" +
                "seconds=" + seconds +
                ", albummid='" + albummid + '\'' +
                ", url='" + url + '\'' +
                ", songname='" + songname + '\'' +
                ", songid=" + songid +
                ", singername='" + singername + '\'' +
                ", albumid='" + albumid + '\'' +
                ", albumname='" + albumname + '\'' +
                ", albumpic_big='" + albumpic_big + '\'' +
                ", albumpic_small='" + albumpic_small + '\'' +
                ", downUrl='" + downUrl + '\'' +
                ", m4a='" + m4a + '\'' +
                ", singerid=" + singerid +
                '}';
    }
}
