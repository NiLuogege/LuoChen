package com.example.well.luochen.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PackageHelper {

	private static GsonBuilder gb = null;
	private static Gson gson = null;
	
	public static Gson getGson() {
		if (gson == null){
			if (gb == null){
				gb = new GsonBuilder();
			}
			gb.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			gson = gb.create();
		}
		return gson;
	}

	public static <T> String Pack(T object) {
		String result = "";
		result = PackageHelper.getGson().toJson(object);
		return result;
	}
	
	public static <T> T Unpack(T object, String input) throws Exception	{	
		java.lang.reflect.Type genType = object.getClass();
		object = PackageHelper.getGson().fromJson(input, genType);
		return object;
	}

}
