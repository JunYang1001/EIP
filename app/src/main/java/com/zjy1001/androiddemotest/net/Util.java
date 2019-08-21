package com.zjy1001.androiddemotest.net;


import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Util {


	/**
	 * 简单名称值对节点的封装如:name=mxt&upwd=mxt
	 * @param map
	 * @return
	 */
	public static String getPair(Map<String, String> map){
		List<NameValuePair> androidParams=new ArrayList<NameValuePair>();
		for(String key:map.keySet()){
			androidParams.add(new BasicNameValuePair(key,map.get(key).toString()));
		}
		String data=URLEncodedUtils.format(androidParams, "UTF-8");
		Log.d("mxt", data.toString());
		return data;
	}

}
