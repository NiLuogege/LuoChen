package com.example.well.luochen.utils.jsoup;

import android.content.Context;
import android.os.Environment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author InJavaWeTrust
 */
public class JsoupUtil {

    private JsoupUtil() {

    }

    private static final JsoupUtil instance = new JsoupUtil();

    public static JsoupUtil getInstance() {
        return instance;
    }

    /**
     * 将电影名称和评论写入文件
     *
     * @param name 电影名称
     * @param star 评论
     */
    public void writeFile(String name, String star) {
        String URL_move = Environment.getExternalStorageDirectory() + "/luoChen/douban/move";
        File file = new File(URL_move);
        Writer writer = null;
        try {
            writer = new FileWriter(file, true);
            writer.write(star + "   " + name + "\r\n");
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *
     * @param context
     * @param i 0表示第一页,10表示第二页,20表示第三页...40表示第五页  共5页
     * @return
     */
    public List<Move> getDoubanReview(Context context, int i) {
        String used = "";
        List<Move> data = new ArrayList<>();
        String url = "http://movie.douban.com/review/best/?start=%s";
        try {

//            InputStream open = context.getAssets().open("douban.html");
//            Document document = Jsoup.parse(open, "UTF-8", "https://movie.douban.com");
            String format = String.format(url, i);
            Document document = Jsoup.connect(format).get();
//            Document document = Jsoup.connect("https://movie.douban.com/nowplaying/shanghai/").timeout(5000).post();
            List<Move> addNameList = getMoveName(document, data);
            List<Move> addImageList = getMoveImage(document, addNameList);
            List<Move> addCommentList = getMoveComment(used, document, addImageList);

            return addCommentList;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得评论
     *
     * @param used
     * @param document
     * @param hasImageList
     */
    private List<Move> getMoveComment(String used, Document document, List<Move> hasImageList) {
        Elements elementsByClass = document.getElementsByClass("short-content");
        for (int i = 0; i < elementsByClass.size(); i++) {
            Element ebc = elementsByClass.get(i);
            Move move = hasImageList.get(i);
            String text = ebc.text();
            Elements left = ebc.getElementsByClass("left");
            for (Element l : left) {
                used = l.text();
                move.used = used;//多少有用多少没用
//                LogUtils.logError("text1= " + used);
            }
            if (text.contains(used)) {
                String comment = text.replace(used, "");
                move.comment = comment;//评论
//                LogUtils.logError("comment= " + comment);
            }

        }

        return hasImageList;
    }

    /**
     * 获得到了每个电影的名字
     *
     * @param document
     * @param data
     */
    private List<Move> getMoveName(Document document, List<Move> data) {
        Elements elementsByClass = document.getElementsByClass("title-link");
        for (Element ebc : elementsByClass) {
            Move move = new Move();
            String name = ebc.text();
            move.name = name;
            data.add(move);//获得到了每个电影的名字
//            LogUtils.logError("name=" + name);
        }
        return data;
    }

    /**
     * 获得到了每个电影的封面图片
     *
     * @param document
     * @param list
     */
    private List<Move> getMoveImage(Document document, List<Move> list) {
        Elements links = document.getElementsByClass("subject-img");
        for (int i = 0; i < links.size(); i++) {
            Move move = list.get(i);
            Element link = links.get(i);
            Elements media = link.select("[src]");
            for (Element src : media) {
                String cover = src.attr("abs:src");
                move.coverUrl = cover;//获得到了每个电影的封面图片
//                LogUtils.logError(cover);
            }

            Elements select = link.select("[href]");
            for (Element href : select) {
                String l = href.attr("abs:href");
                move.linkUrl = l;//获得到了每个电影详情连接
//                LogUtils.logError(l);
            }
        }

        return list;
    }

}  