package com.example.well.luochen.downloadMusic;

import com.example.well.luochen.utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DownloadManager {
	//默认状态
	public static final int STATE_NONE=0;
	//等待状态
	public static final int STATE_WAITTING=1;
	//正在下载状态
	public static final int STATE_DOWNLOADING=2;
	//暂停状态
	public static final int STATE_PAUSE=3;
	//下载出错的状态
	public static final int STATE_ERROR=4;
	//下载完成的状态
	public static final int STATE_DOANLOADED=5;
	//存放每一个应用的观察者
	private List<DownloadObserver> observers=new ArrayList<DownloadObserver>();
	//单例模式
	private DownloadManager(){}
	//声明一个对象
	private static DownloadManager downloadManager;
	//存放下载的downloadinfo的对象，持久化到本地
	private Map<String, DownloadInfo> downloadMap=new HashMap<String, DownloadInfo>();
//	ConcurrentHashMap<K, V>线程安全的
	//存放下载任务的集合
	private Map<String, DownloadTask> downloadTaskMap=new HashMap<String, DownloadTask>();
	//提供一个方法获取对象
	public static DownloadManager getInstance(){
		if (downloadManager==null) {
			downloadManager=new DownloadManager();
		}
		return downloadManager;
	}
	/**
	 * 注册观察者的监听
	 */
	public synchronized void registerObserver(DownloadObserver downloadObserver){
		if (downloadObserver!=null&&!observers.contains(downloadObserver)) {
			observers.add(downloadObserver);
		}
	}
	/**
	 * 接触注册的方法
	 */
	public synchronized void unRegisterObserver(DownloadObserver downloadObserver){
		if (downloadObserver!=null&&observers.contains(downloadObserver)) {
			observers.remove(downloadObserver);
		}
	}
	//当状态已发生改变之后，伴随着UI状态的改变
	public synchronized void notifyDownloadStateChange(DownloadInfo downloadInfo){
		if (downloadInfo!=null) {
			//遍历观察者，跟新ui
			for (DownloadObserver observer : observers) {
				observer.notifyStateChange(downloadInfo);
			}
		}
	}
	//当进度已发生改变之后，伴随着UI状态的改变
	public synchronized void notifyDownloadProgressChange(DownloadInfo downloadInfo){
		if (downloadInfo!=null) {
			//遍历观察者，跟新ui
			for (DownloadObserver observer : observers) {
				observer.notifyProgressChange(downloadInfo);
			}
		}
	}
	//使用观察者模式，监听状态和进度的改变，一旦改变之后需要更新UI
	public interface DownloadObserver{
		public abstract void notifyStateChange(DownloadInfo downloadInfo);
		public abstract void notifyProgressChange(DownloadInfo downloadInfo);
	}
//	//安装
//	public void install(AppInfo appInfo){
//		//转换成AppInfo--->downloadInfo,下载apk的存储路径，开启activity做安装操作
//		stopDownload(appInfo);
//		DownloadInfo downloadInfo = downloadMap.get(appInfo.getId());
//		if(downloadInfo!=null){
//			Intent intent = new Intent(Intent.ACTION_VIEW);
//			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			//指定下载路径，安装apk
//			intent.setDataAndType(Uri.parse("file://"+downloadInfo.getPath()),"application/vnd.android.package-archive");
//			UIUtil.getContext().startActivity(intent);
//		}
//	}
	//暂停 等待状态，正在下载状态
	public void pause(DownloadInfo downloadInfo){
		if (downloadInfo!=null) {
//			DownloadInfo downloadInfo = downloadMap.get(appInfo.getId());
			if (downloadInfo!=null) {
				if (downloadInfo.getState()==STATE_WAITTING||downloadInfo.getState()==STATE_DOWNLOADING) {
					//等待状态
					stopDownload(downloadInfo);
					//正在下载状态
					downloadInfo.setState(STATE_PAUSE);
					notifyDownloadStateChange(downloadInfo);
				}
			}
		}
	}
	/**
	 * 停止下载
	 */
	private void stopDownload(DownloadInfo downloadInfo) {
		DownloadTask downloadTask = downloadTaskMap.get(downloadInfo.getDownloadUrl());
		if (downloadTask!=null) {
			ThreadManager.ThreadPoolProxy threadPoolProxy = ThreadManager.getInstance();
			//在线程池中将该任务停止
			threadPoolProxy.cancelDownload(downloadTask);
		}
	}
	//下载,从头开始下载，断点续传下载
	public void download(DownloadInfo downloadInfo){
//		if (appInfo!=null) {
			//之前下载过
//			DownloadInfo downloadInfo = downloadMap.get(appInfo.getId());
//			if (downloadInfo==null) {
//				//从头开始下载
//				downloadInfo = DownloadInfo.appInfo2DownloadInfo(appInfo);
//			}
			downloadMap.put(downloadInfo.getDownloadUrl(), downloadInfo);
			//准备开始下载
			downloadInfo.setState(STATE_WAITTING);
			//修改UI状态
			notifyDownloadStateChange(downloadInfo);
			//下载的任务
			DownloadTask downloadTask = new DownloadTask(downloadInfo);
			ThreadManager.ThreadPoolProxy poolProxy = ThreadManager.getInstance();
			//线程池执行下载任务
			poolProxy.excute(downloadTask);
			//将任务添加到任务集合中
			downloadTaskMap.put(downloadInfo.getDownloadUrl(), downloadTask);
		}
	/**
	 * 下载的任务
	 */
	class DownloadTask implements Runnable{
		private DownloadInfo downloadInfo;
		private HttpHelper.HttpResult httpResult;
		private InputStream is;
		private FileOutputStream fos;
		public DownloadTask(DownloadInfo downloadInfo) {
			this.downloadInfo=downloadInfo;
		}
		@Override
		public void run() {
			//正式开始下载
			downloadInfo.setState(STATE_DOWNLOADING);
			//更新ui状态
			notifyDownloadStateChange(downloadInfo);
			LogUtils.logError("downloadInfo="+downloadInfo.toString()+" Path="+downloadInfo.getPath());
			File file = new File(downloadInfo.getPath());
			if (!file.exists()||downloadInfo.getCurrentPosition()!=file.length()||downloadInfo.getCurrentPosition()==0) {
				//第一次下载
				file.delete();
				//将下载位置置为0
				downloadInfo.setCurrentPosition(0);
//				httpResult = HttpHelper.download(HttpHelper.URL+"download?name="+downloadInfo.getDownloadUrl());
				httpResult = HttpHelper.download(downloadInfo.getDownloadUrl());
			}else{
				//断点续传下载 range
				httpResult=HttpHelper.download(downloadInfo.getDownloadUrl()+"&range="+downloadInfo.getCurrentPosition());
			}
			LogUtils.logError("httpResult="+httpResult);
			LogUtils.logError("is="+httpResult.getInputStream());
			if (httpResult!=null&&(is=httpResult.getInputStream())!=null) {
				//下载成功
				LogUtils.logError("下载成功");
				try {
					fos = new FileOutputStream(file,true);
					byte[] b=new byte[1024];
					int temp=-1;
					//如果当前状态不是正在下载状态的话，不能在读写流，有可能被暂停
					while ((temp=is.read(b))!=-1&&downloadInfo.getState()==STATE_DOWNLOADING) {
						fos.write(b, 0, temp);
						//修改当前的进度
						downloadInfo.setCurrentPosition(downloadInfo.getCurrentPosition()+temp);
						notifyDownloadProgressChange(downloadInfo);
						//将数据刷新到文件中
						fos.flush();
					}
				} catch (Exception e) {
					e.printStackTrace();
					//下载失败
					LogUtils.logError("下载失败");
					downloadInfo.setState(STATE_ERROR);
					notifyDownloadStateChange(downloadInfo);
					file.delete();
					downloadInfo.setCurrentPosition(0);
				}
				if (downloadInfo.getCurrentPosition()==downloadInfo.getSize()) {
					//下载成功
					LogUtils.logError("下载成功");
					downloadInfo.setState(STATE_DOANLOADED);
					notifyDownloadStateChange(downloadInfo);
				}else if(downloadInfo.getState()==STATE_PAUSE){
					//暂停
					LogUtils.logError("下载暂停");
					notifyDownloadStateChange(downloadInfo);
				}
			}else{
				//下载失败
				LogUtils.logError("下载失败");
				downloadInfo.setState(STATE_ERROR);
				notifyDownloadStateChange(downloadInfo);
				file.delete();
				downloadInfo.setCurrentPosition(0);
			}
			//下载完成之后将任务从集合中移除掉
			downloadTaskMap.remove(downloadInfo.getDownloadUrl());
		}
		
	}

	/**
	 * 获取一个下载的DownloadInfo的对象
	 */
	public DownloadInfo getDownloadInfo(String  url) {
		return downloadMap.get(url);
	}
}














