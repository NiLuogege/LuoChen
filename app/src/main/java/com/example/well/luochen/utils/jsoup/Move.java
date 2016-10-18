package com.example.well.luochen.utils.jsoup;

/**
 * @author InJavaWeTrust
 */
public class Move {

    public String name = "";//电影名称
    public String coverUrl = "";//电影封面
    public String linkUrl = "";//电影详情网页连接
    public String comment = "";//电影评论
    public String used = "";//评论有多少有用,多少没用

    @Override
    public String toString() {
        return "Move{" +
                "name='" + name + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", comment='" + comment + '\'' +
                ", used='" + used + '\'' +
                '}';
    }
}
  