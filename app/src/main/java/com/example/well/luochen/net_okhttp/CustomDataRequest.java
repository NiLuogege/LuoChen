package com.example.well.luochen.net_okhttp;


import android.text.TextUtils;

import com.example.well.luochen.utils.LogUtils;
import com.google.gson.Gson;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.RestRequest;
import com.yolanda.nohttp.rest.StringRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class CustomDataRequest<T> extends RestRequest<T> {
	public static final String ACCEPT = "application/json;q=1";
	Class<?> cls;
	public CustomDataRequest(String url,Class<?> cls) {
		super(url);
		this.cls = cls;
	}
	

	public CustomDataRequest(String url, RequestMethod requestMethod,Class<?> cls) {
		super(url, requestMethod);
		this.cls = cls;
		
		Type type = getClass().getGenericSuperclass();
		if(type instanceof ParameterizedType){
			Type[] types = ((ParameterizedType)type).getActualTypeArguments();
			for (int i = 0; i < types.length; i++) {
				LogUtils.logDug(types[i].toString());
				LogUtils.logDug(types[i].getClass().toString());
			}
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public T parseResponse(String url, Headers responseHeaders, byte[] responseBody) {
		
		String jsonStr = StringRequest.parseResponseString(url, responseHeaders, responseBody);
		
		LogUtils.logDug("HTTP Response Result = "+jsonStr);
		
		try {
			if(TextUtils.isEmpty(jsonStr)) return null;
			return  (T) new Gson().fromJson(jsonStr,cls);
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public String getAccept() {
		return ACCEPT;
	}

}
