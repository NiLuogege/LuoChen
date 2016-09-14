package com.example.well.luochen.downloadMusic;

import android.os.Environment;

import java.io.File;

/**
 * Created by Well on 2016/9/8.
 */

public class DownloadInfo {
    //下载地址
    private String downloadURL;
    //当前状态
    private int state;
    //当前进度
    private float progress;
    //文件放置的位置
    private String path;
    //当前下载位置
    private long currentPosition;
    //应用的大小
    private long size;

    public String getDownloadUrl() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(long currentPosition) {
        this.currentPosition = currentPosition;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    //将appInfo转换成downloadInfo对象
    public static DownloadInfo downloadPath(DownloadInfo downloadInfo){
        if(getFilePath()!=null){
            downloadInfo.setPath(getFilePath()+downloadInfo.getDownloadUrl()+".MP3");
        }
        return downloadInfo;
    }
    /**
     * 获取文件的路劲
     */
    private static String getFilePath() {
        String path= Environment.getExternalStorageDirectory().getAbsolutePath();
        StringBuffer buffer = new StringBuffer();
        buffer.append(path);
        buffer.append(File.separator);
        buffer.append("luoChen");
        buffer.append(File.separator);
        buffer.append("download");
        buffer.append(File.separator);
        if(createFile(buffer.toString())){
            return buffer.toString();
        }
        return null;
    }
    /**
     * 根据文件的路劲去创建文件夹
     * @param string
     */
    private static boolean createFile(String string) {
        File file = new File(string);
        //文件夹不存在或者文件不是文件夹
        if (!file.exists()||!file.isDirectory()) {
            return file.mkdirs();
        }
        return true;
    }

    @Override
    public String toString() {
        return "DownloadInfo{" +
                "downloadURL='" + downloadURL + '\'' +
                ", state=" + state +
                ", progress=" + progress +
                ", path='" + path + '\'' +
                ", currentPosition=" + currentPosition +
                ", size=" + size +
                '}';
    }
}
