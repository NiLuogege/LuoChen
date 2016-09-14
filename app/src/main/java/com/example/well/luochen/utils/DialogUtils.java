package com.example.well.luochen.utils;


import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;

import com.example.well.luochen.MyApplication;
import com.example.well.luochen.R;

/***
 * dialog  工具类。
 * @author Luo
 *
 */
public class DialogUtils {
	private static DialogUtils dialogUtils;
	public static DialogUtils getInstance(){
		return dialogUtils == null ? dialogUtils = new DialogUtils() : dialogUtils;
	}
	
	public Dialog createDialog(Context ctx,int resLayout){
		Dialog dialog = new Dialog(ctx, R.style.custom_dialog);
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.width = MyApplication.getInstance().widthPixels;
		lp.height = MyApplication.getInstance().heightPixels;
		dialog.setContentView(resLayout);
		return dialog;
	}
	
//	public void showSelectPicture(Context ctx,final DialogCallback callback){
//		final Dialog dialog = createDialog(ctx, R.layout.dialog_select_picture_layout);
//		dialog.setCancelable(true);
//		dialog.setCanceledOnTouchOutside(true);
//		dialog.findViewById(R.id.cameraBtn).setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//				if(callback != null){
//					callback.camareClick();
//				}
//			}
//		});
//		
//		dialog.findViewById(R.id.picBtn).setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//				if(callback != null){
//					callback.picClick();
//				}
//			}
//		});
//		
//		dialog.show();
//	}
	
//	public void showSelectList(Context ctx,String[] items,final DialogCallback callback){
//		final Dialog dialog = createDialog(ctx, R.layout.dialog_select_list_layout);
//		final WheelView wheelView = (WheelView) dialog.findViewById(R.id.wheelView);
//		final StringAdapter adapter = new StringAdapter(ctx, items);
//		wheelView.setViewAdapter(adapter);
//		dialog.findViewById(R.id.cancelBtn).setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//			}
//		});
//		
//		dialog.findViewById(R.id.confirmBtn).setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				if(callback!= null){
//					callback.typeStr(adapter.getItemText(wheelView.getCurrentItem()).toString());
//				}
//				dialog.dismiss();
//			}
//		});
//		
//		dialog.show();
//	}
	
//	public void showLoanDialog(Context ctx,final DialogCallback callback){
//		final Dialog dialog = createDialog(ctx, R.layout.dialog_loan_confirm_layout);
//		TextView jkPriceTv = (TextView) dialog.findViewById(R.id.jkPriceTv);
//		TextView costPriceTv =(TextView)  dialog.findViewById(R.id.costPriceTv);
//		TextView rPriceTv = (TextView) dialog.findViewById(R.id.rPriceTv);
//		TextView hPriceTv = (TextView) dialog.findViewById(R.id.hPriceTv);
//		TextView dayTv = (TextView) dialog.findViewById(R.id.dayTv);
//		
//		BorrowMeneyInfo borrowMeneyInfo = callback.getBorrowMeneyInfo();
//		int day = callback.getDay();
//		float costPrice = Float.valueOf(borrowMeneyInfo.ltsummoney) - (Float.valueOf(borrowMeneyInfo.ltmoney) * (float)day * 0.01f);
//		jkPriceTv.setText(borrowMeneyInfo.ltsummoney+"元");
//		costPriceTv.setText(String.format("%.2f", costPrice)+"元");
//		rPriceTv.setText(String.format("%.2f", Float.valueOf(borrowMeneyInfo.ltsummoney)-costPrice)+"元");
//		hPriceTv.setText(borrowMeneyInfo.ltmoney+"元");
//		dayTv.setText(day+"天");
//		
//		dialog.findViewById(R.id.cancelBtn).setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//			}
//		});
//		
//		dialog.findViewById(R.id.confirmBtn).setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				if(callback!= null){
//					callback.confirm();
//				}
//				dialog.dismiss();
//			}
//		});
//		
//		dialog.show();
//	}
	
//	/**
//	 * Adapter for countries
//	 */
//	private class StringAdapter extends AbstractWheelTextAdapter {
//		String[] items;
//		
//		
//		public StringAdapter(Context context, String[] items) {
//			super(context,R.layout.modify_item, NO_RESOURCE);
//			this.items = items;
//			
//			setItemTextResource(R.id.item);
//		}
//
//
//		@Override
//		public View getItem(int index, View cachedView, ViewGroup parent) {
//			View view = super.getItem(index, cachedView, parent);
//			return view;
//		}
//
//		public int getItemsCount() {
//			return items.length;
//		}
//
//		@Override
//		protected CharSequence getItemText(int index) {
//			return items[index];
//		}
//	}
}
