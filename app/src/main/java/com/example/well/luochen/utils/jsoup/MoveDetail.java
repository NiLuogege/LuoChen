package com.example.well.luochen.utils.jsoup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Well on 2016/10/19.
 */

public class MoveDetail {
    public String info = "";//电影详细信息
    public float RatingNum = 0;//电影热度
    public String Intro = "";//电影剧情简介
    public List<String> imageList = new ArrayList<>();//电影剧照

    @Override
    public String toString() {
        return "MoveDetail{" +
                "info='" + info + '\'' +
                ", RatingNum=" + RatingNum +
                ", Intro='" + Intro + '\'' +
                ", imageList=" + imageList +
                '}';
    }
}
