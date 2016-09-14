package com.example.well.luochen.utils;


import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.well.luochen.R;

public class TitleBar {
	private TextView titleTv;
	private View rootView;
	private TitleCallBack titleCallBack;
	
	public void initView(View view,boolean showBack){
		this.rootView = view;
		titleTv = (TextView) rootView.findViewById(R.id.titleTv);
		
		if(titleTv == null) return;
		
		rootView.findViewById(R.id.backimg).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(titleCallBack != null){
					titleCallBack.backClick((ImageView) rootView.findViewById(R.id.backimg));
				}
				
			}
		});
		
		rootView.findViewById(R.id.rightimg).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(titleCallBack != null){
					titleCallBack.rightClick((ImageView) rootView.findViewById(R.id.rightimg));
				}
			}
		});
		
		if(showBack){
			rootView.findViewById(R.id.backimg).setVisibility(View.VISIBLE);
		}
	}
	
	public void setTitle(String title){
		titleTv.setText(title);
	}
	
	public void addTitleCallBack(TitleCallBack callBack){
		this.titleCallBack = callBack;
		if(titleCallBack != null){
			titleCallBack.rightClick((ImageView) rootView.findViewById(R.id.rightimg));
		}
	}
	public interface  TitleCallBack{
		public void backClick(ImageView backImg);
		public  void rightClick(ImageView rightImg);
	}
}
