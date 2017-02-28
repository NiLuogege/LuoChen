package com.example.well.luochen.net_okhttp;

import android.content.Context;

import com.example.well.luochen.MyApplication;
import com.example.well.luochen.MyContacts;
import com.example.well.luochen.utils.LogUtils;
import com.yolanda.nohttp.FileBinary;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;

import java.io.File;
import java.util.Map;


/***
 * NoHttp网络框架
 * @author Luo
 *
 */
public class NoHttpRequest {
	private static NoHttpRequest httpRequest;
	public static NoHttpRequest getInstance(){
		return httpRequest == null ? httpRequest = new NoHttpRequest() :httpRequest;
	}
	private  NoHttpRequest(){
		
	}
	
	public void init(Context ctx){
//		NoHttp.init(MyApplication.getInstance());
		NoHttp.initialize(MyApplication.getInstance());
	}
	
	public <T>  void requestPost(Context context ,int what,Map<String,String> params,String interfaceUrl,Class<?> cls,HttpListener<T> callback,boolean isShowDialog){
		//打印访问的一些参数
		LogUtils.logDug("net request start");
		LogUtils.logDug("request what =  " + what);
		LogUtils.logDug("request method = POST" );
		LogUtils.logDug("request url = "+ MyContacts.BASE_URL+ interfaceUrl);
		LogUtils.logDug("request params :");
		
		for (String key : params.keySet()) {
			LogUtils.logDug(key + " :" + params.get(key));
		}
//		 Type type = ((ParameterizedType) callback.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//		 Class<T> cls = null;
//         if (type instanceof Class<?>) {
//        	 cls = ((Class<T>) type);
//         } else if (type instanceof ParameterizedType) {
//        	 cls =((Class<T>) ((ParameterizedType) type).getRawType());
//         }
         
		Request<T> request = new CustomDataRequest<T>(MyContacts.BASE_URL+interfaceUrl,RequestMethod.POST,cls);
		request.add(params);
		CallServer.getRequestInstance().add(context, what, request, callback,MyApplication.mainActivity,isShowDialog);
		
	}
	
	public <T>  void requestGet(Context context ,int what,String interfaceUrl,Class<?> cls,HttpListener<T> callback,boolean isShowDialog){
		LogUtils.logDug("net request start");
		LogUtils.logDug("request what =  " + what);
		LogUtils.logDug("request method = Get" );
		LogUtils.logDug("request url = "+ interfaceUrl+MyContacts.BASE_URL);
		
         
		Request<T> request = new CustomDataRequest<T>(interfaceUrl+MyContacts.BASE_URL,RequestMethod.GET,cls);
		CallServer.getRequestInstance().add(context, what, request, callback,MyApplication.mainActivity,isShowDialog);
		
	}
	
	/****
	 * 上传文件
	 * @param what
	 * @param fileAbspath
	 * @param interfaceUrl
	 * @param callback
	 * @param isShowDialog
	 */
	public <T>void upFilePost(Context context,int what,String fileAbspath,String interfaceUrl,Class<?> cls,HttpListener<T> callback,boolean isShowDialog){
		LogUtils.logDug("net request start");
		LogUtils.logDug("request what =  " + what);
		LogUtils.logDug("request method = POST" );
		LogUtils.logDug("request url = "+MyContacts.BASE_URL );
		LogUtils.logDug("request params :");
		LogUtils.logDug("filePath = " + fileAbspath);
		
		if(!(new File(fileAbspath).exists())){
			LogUtils.logDug("文件不存在");
			return;
		}
		Request<T> request = new CustomDataRequest<T>(MyContacts.BASE_URL+interfaceUrl,RequestMethod.POST,cls);
		request.add("file", new FileBinary(new File(fileAbspath)));
		CallServer.getRequestInstance().add(context, what, request, callback,MyApplication.mainActivity,isShowDialog);
	}
	
	
	/****
	 * 上传文件
	 * @param what
	 * @param fileAbspath
	 * @param interfaceUrl
	 * @param callback
	 * @param isShowDialog
	 */
	public <T>void upFilePost(Context context,int what,String fileAbspath[],String interfaceUrl,Class<?> cls,HttpListener<T> callback,boolean isShowDialog){
		LogUtils.logDug("net request start");
		LogUtils.logDug("request what =  " + what);
		LogUtils.logDug("request method = POST" );
		LogUtils.logDug("request url = "+MyContacts.BASE_URL );
		LogUtils.logDug("request params :");
		LogUtils.logDug("filePath :" );
		for (int i = 0; i < fileAbspath.length; i++) {
			LogUtils.logDug(fileAbspath[i]);
		}
		
		Request<T> request = new CustomDataRequest<T>(MyContacts.BASE_URL+interfaceUrl,RequestMethod.POST,cls);
		
		for (int i = 0; i < fileAbspath.length; i++) {
			request.add("file"+i, new FileBinary(new File(fileAbspath[i])));
		}
		CallServer.getRequestInstance().add(context, what, request, callback,MyApplication.mainActivity,isShowDialog);
	}
}
