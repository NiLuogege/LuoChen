package com.example.well.luochen.utils.jsoup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Well on 2016/10/19.
 */

public class MoveDetail {
    public String name = "";//电影名字
    public String info = "";//电影详细信息
    public float ratingNum = 0;//电影热度
    public String intro = "";//电影剧情简介
    public List<String> imageList = new ArrayList<>();//电影剧照

    @Override
    public String toString() {
        return "MoveDetail{" +
                "name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", ratingNum=" + ratingNum +
                ", intro='" + intro + '\'' +
                ", imageList=" + imageList +
                '}';
    }
}
