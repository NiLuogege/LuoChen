package com.example.well.luochen.utils.jsoup;

import android.app.Activity;
import android.os.Environment;

import com.example.well.luochen.view.AutoSwipeRefreshLayout;

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
     * 获得影评页面的数据
     *
     * @param activity
     * @param i       0表示第一页,10表示第二页,20表示第三页...40表示第五页  共5页
     * @param asrl
     * @return
     */
    public List<Move> getDoubanReview(Activity activity, int i, final AutoSwipeRefreshLayout asrl) {
        String used = "";
        List<Move> data = new ArrayList<>();
        String url = "http://movie.douban.com/review/best/?start=%s";
        try {

//            InputStream open = context.getAssets().open("douban.html");
//            Document document = Jsoup.parse(open, "UTF-8", "https://movie.douban.com");
            String format = String.format(url, i);
            Document document = Jsoup.connect(format).timeout(5000).get();
//            Document document = Jsoup.connect("https://movie.douban.com/nowplaying/shanghai/").timeout(5000).post();
            List<Move> addNameList = getMoveName(document, data);
            List<Move> addImageList = getMoveImage(document, addNameList);
            List<Move> addCommentList = getMoveComment(used, document, addImageList);

            return addCommentList;

        } catch (IOException e) {
            if (null != asrl) {
                boolean refreshing = asrl.isRefreshing();
                if (refreshing) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            asrl.setRefreshing(false);
                        }
                    });
                }
            }
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
//            LogUtils.logError("刚开始的评论= " + text);
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
            } else {
                move.comment = "这盘影评可能有剧透!";//评论
//                LogUtils.logError("comment= " + "这盘影评可能有剧透!");
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

    public MoveDetail getDoubanMoveDetail(String url) throws IOException {
        Document document = Jsoup.connect(url).timeout(5000).get();
        MoveDetail moveDetail = new MoveDetail();
        MoveDetail addInfo = getInfo(document, moveDetail);
        MoveDetail addRatingNum = getRatingNum(document, addInfo);
        MoveDetail addIntro = getIntro(document, addRatingNum);
        MoveDetail addImage = getImage(document, addIntro);
        return addImage;
    }

    /**
     * 获取剧照
     *
     * @param document
     * @param addIntro
     */
    private MoveDetail getImage(Document document, MoveDetail addIntro) {
        List<String> imageList = addIntro.imageList;
        Elements elementsByClass = document.getElementsByClass("related-pic-bd");
        Elements select = elementsByClass.select("img[src]");
        for (int i = 0; i < select.size(); i++) {
            Element element = select.get(i);
            String src = element.attr("abs:src");
            imageList.add(src);
//            LogUtils.logError("attr= " + src);
        }
        return addIntro;
    }

    /**
     * 获得剧情简介
     *
     * @param document
     * @param addRatingNum
     */
    private MoveDetail getIntro(Document document, MoveDetail addRatingNum) {
        Element elementById = document.getElementById("link-report");
        String intro = elementById.text();
        addRatingNum.Intro = intro;
//        LogUtils.logError("text="+intro);
        return addRatingNum;
    }


    /**
     * 获得好评度
     *
     * @param document
     * @param addInfo
     */
    private MoveDetail getRatingNum(Document document, MoveDetail addInfo) {
        Elements select = document.select("strong.ll");
        Element first = select.first();
        String rating = first.text();
        addInfo.RatingNum = Float.parseFloat(rating);
//        LogUtils.logError("大小= "+select.size()+ " 好评度= "+rating);
        return addInfo;
    }

    /**
     * 获得电影文字信息
     *
     * @param document
     * @param moveDetail
     */
    private MoveDetail getInfo(Document document, MoveDetail moveDetail) {
        StringBuffer sb = new StringBuffer();
        Element info = document.getElementById("info");
        Elements children = info.children();
        for (int i = 0; i < children.size(); i++) {
            Element element = children.get(i);
            String text = element.text();
            sb.append(text);
            sb.append(" ");
        }
        String s = sb.toString();
        String[] split = s.split("  ");
//        LogUtils.logError("操作之后-----------------");
        sb = new StringBuffer();
        for (int i = 0; i < split.length; i++) {
            String s1 = split[i];
            sb.append(s1);
            sb.append("\n");
        }
        String s1 = sb.toString();
        moveDetail.info = s1;
//        LogUtils.logError("s1= " + s1);//电影的文字信息
        return moveDetail;
    }

}  