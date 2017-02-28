package com.example.well.luochen.mode.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.well.luochen.MyApplication;
import com.example.well.luochen.net_okhttp.HttpListener;
import com.example.well.luochen.net_okhttp.NoHttpRequest;
import com.example.well.luochen.utils.ACache;
import com.example.well.luochen.utils.TitleBar;

import java.util.Map;

/***
 * 基类Activity
 * @author Luo
 * @create date 2016.05.11
 */
public class BaseActivity extends FragmentActivity implements TitleBar.TitleCallBack{
	public MyApplication app;
	public TitleBar titleBar = new TitleBar();
	public Context context;
	protected ACache mACache = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (MyApplication) getApplication();
		context = this;
		mACache = ACache.get(this);
		
		registerReceiver(broadcastReceiver, new IntentFilter("close"));
		
	}
	
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals("close")){
				finish();
			}
		}
	};
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if(broadcastReceiver != null){
			unregisterReceiver(broadcastReceiver);
		}
	}


	public void initViews(){
		titleBar.initView(getWindow().getDecorView().getRootView(),true);
		titleBar.addTitleCallBack(this);
	}


	/***
	 * 返回按钮
	 */
	@Override
	public void backClick(ImageView backImg) {
		finish();
	}



	/***
	 * 右边按钮
	 */
	@Override
	public void rightClick(ImageView rightImg) {
	}


	
	public  void setTitleText(String title){
		if(titleBar != null){
			titleBar.setTitle(title);
		}
	}

	public <T> void requestPost(int what, Map<String,String> params, String interfaceUrl, Class<?> cls, HttpListener<T> callback, boolean isShowDialog){
		NoHttpRequest.getInstance().requestPost(this,what, params, interfaceUrl,cls, callback,isShowDialog);
	}
	
	public <T>  void requestGet(int what,String interfaceUrl,Class<?> cls,HttpListener<T> callback,boolean isShowDialog){
		NoHttpRequest.getInstance().requestGet(this, what, interfaceUrl, cls, callback, isShowDialog);
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
		NoHttpRequest.getInstance().upFilePost(context, what, fileAbspath, interfaceUrl, cls, callback, isShowDialog);
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
		NoHttpRequest.getInstance().upFilePost(context, what, fileAbspath, interfaceUrl, cls, callback, isShowDialog);
	}
	
	public void showToast(String str){
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
	
	public void findEditTextEnable(ViewGroup viewGroup){
		for (int i = 0; i < viewGroup.getChildCount(); i++) {
			View view = viewGroup.getChildAt(i);
			if(view instanceof ViewGroup){
				findEditTextEnable((ViewGroup) view);
			}else if(view instanceof EditText){
				((EditText)view).setEnabled(false);
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
