package com.example.well.luochen.utils.download;
  
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;
  
/** 
 * 文件下载类 
 *  
 * @author yangxiaolong 
 * @2014-5-6 
 */  
public class FileDownloadThread extends Thread {  
  
    private static final String TAG = FileDownloadThread.class.getSimpleName();

    /**
     * 起始下载位置
     */
    private  int endPos=0;
    /**
     * 结束下载位置
     */
    private  int startPos=0;

    /** 当前下载是否完成 */  
    private boolean isCompleted = false;  
    /** 当前下载文件长度 */  
    private int downloadLength = 0;  
    /** 文件保存路径 */  
    private File file;  
    /** 文件下载路径 */  
    private URL downloadUrl;  
    /** 当前下载线程ID */  
    private int threadId;

    private OnDownloadCompletedListener listener;//下载完成是进行回调

    /**
     * @param downloadUrl:文件下载地址
     * @param file:文件保存路径
     * @param threadId:线程ID 可以不填
     * @param startPos :下载的起始位置
     * @param endPos : 下载的结束位置
     */
    public FileDownloadThread(URL downloadUrl, File file, int startPos,int endPos,int threadId) {
        this.downloadUrl = downloadUrl;  
        this.file = file;  
        this.threadId = threadId;
        this.startPos=startPos;
        this.endPos=endPos;
    }
  
    @Override  
    public void run() {  
  
        BufferedInputStream bis = null;  
        RandomAccessFile raf = null;  
  
        try {  
            URLConnection conn = downloadUrl.openConnection();  
            conn.setAllowUserInteraction(true);  
  
            int startPos = this.startPos;//开始位置
            int endPos = this.endPos;//结束位置
            //设置当前线程下载的起点、终点  
            conn.setRequestProperty("Range", "bytes=" + startPos + "-" + endPos);  
            System.out.println(Thread.currentThread().getName() + "  bytes="  
                    + startPos + "-" + endPos);  
  
            byte[] buffer = new byte[1024];  
            bis = new BufferedInputStream(conn.getInputStream());  
  
            raf = new RandomAccessFile(file, "rwd");  
            raf.seek(startPos);  
            int len;  
            while ((len = bis.read(buffer, 0, 1024)) != -1) {  
                raf.write(buffer, 0, len);  
                downloadLength += len;  
            }  
            isCompleted = true;
            listener.onCompleted();
            Log.d(TAG, "current thread task has finished,all size:"  
                    + downloadLength);  
  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (bis != null) {  
                try {  
                    bis.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
            if (raf != null) {  
                try {  
                    raf.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
  
    /** 
     * 线程文件是否下载完毕 
     */  
    public boolean isCompleted() {  
        return isCompleted;  
    }  
  
    /** 
     * 线程下载文件长度 
     */  
    public int getDownloadLength() {  
        return downloadLength;  
    }

    public interface OnDownloadCompletedListener{
        void onCompleted();
    }

    public void setOnDownloadCompletedListener(OnDownloadCompletedListener listener){
        this.listener=listener;
    }
  
}  