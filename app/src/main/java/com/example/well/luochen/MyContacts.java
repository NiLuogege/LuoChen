package com.example.well.luochen;

/***
 * 常量
 * @author Luo
 * @create date 2016.05.11
 */
public class MyContacts {
	public static boolean showLog = true;//是否显示日志
	public static boolean online = true;
	public static final String TEST_URL = "";
	public static final String ON_LINE_URL = "";

//	public static final String TEST_URL = "?showapi_appid=20775&showapi_sign=d159d4a9edd649c9b669386b0170babc";
//	public static final String ON_LINE_URL = "?showapi_appid=20775&showapi_sign=d159d4a9edd649c9b669386b0170babc";

	public static  String BASE_URL = "";

	static {
		BASE_URL = online ? ON_LINE_URL : TEST_URL;
	}
	
	
}

