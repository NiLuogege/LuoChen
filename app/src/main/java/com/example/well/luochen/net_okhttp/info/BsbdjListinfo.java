package com.example.well.luochen.net_okhttp.info;

import java.io.Serializable;

/**
 * Created by Well on 2016/6/21.
 */

public class BsbdjListinfo implements Serializable{
    public String create_time = "";
    public String hate = "";
    public String height = "";
    public String id = "";
    public String love = "";
    public String name = "";
    public String profile_image = "";
    public String text = "";
    public String type = "";
    public String video_uri = "";
    public String videotime = "";
    public String voicelength = "";
    public String voicetime = "";
    public String voiceuri = "";
    public String weixin_url = "";
    public String width = "";
    public String image0 = "";
    public String image1 = "";
    public String image2 = "";
    public String image3 = "";

    @Override
    public String toString() {
        return "BsbdjListinfo{" +
                "create_time='" + create_time + '\'' +
                ", hate='" + hate + '\'' +
                ", height='" + height + '\'' +
                ", id='" + id + '\'' +
                ", love='" + love + '\'' +
                ", name='" + name + '\'' +
                ", profile_image='" + profile_image + '\'' +
                ", text='" + text + '\'' +
                ", type='" + type + '\'' +
                ", video_uri='" + video_uri + '\'' +
                ", videotime='" + videotime + '\'' +
                ", voicelength='" + voicelength + '\'' +
                ", voicetime='" + voicetime + '\'' +
                ", voiceuri='" + voiceuri + '\'' +
                ", weixin_url='" + weixin_url + '\'' +
                ", width='" + width + '\'' +
                ", image0='" + image0 + '\'' +
                ", image1='" + image1 + '\'' +
                ", image2='" + image2 + '\'' +
                ", image3='" + image3 + '\'' +
                '}';
    }
}
