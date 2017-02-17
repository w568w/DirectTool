package com.baidu.apistore.sdk;

import android.app.Activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class ApiStoreSDK {
	static String APIkey;
	public static void init(String key)
	{
		ApiStoreSDK.APIkey =key;
	}
	public static void execute(final Activity a,final String url,final Parameters p,final ApiCallBack acb)
	{
		new Thread(new Runnable() {
			@Override
			public void run() {
				InputStream is =null;
				String result="";
				Iterator<String> iter=p.keySet().iterator();
				String urlall="";
				while(iter.hasNext())
				{
					String key=iter.next();
					try {
						urlall+= key+"="+ URLEncoder.encode(p.get(key),"UTF-8")+"&";
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
						a.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								acb.onError();
								
							}
						});
						
					}
				}
				urlall=url+"?"+urlall;
				urlall=urlall.substring(0, urlall.length()-1);
				
				
				
				HttpURLConnection httpUrlConnection = null;
				try {
					URL url = new URL(urlall); 
			
					httpUrlConnection = (HttpURLConnection)url.openConnection();
					httpUrlConnection.setDoInput(true); 
					httpUrlConnection.setConnectTimeout(5000);
					httpUrlConnection.setRequestProperty("apikey", ApiStoreSDK.APIkey);
					httpUrlConnection.connect(); 
					is =httpUrlConnection.getInputStream();
				} catch (Exception e) {
					e.printStackTrace();
					a.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							acb.onError();
							
						}
					});
				}
				 try {
					BufferedReader reader =new BufferedReader(new InputStreamReader(is,"utf-8"));
					StringBuilder sb =new StringBuilder();
					String line =null;
					while((line = reader.readLine()) != null) {
			            sb.append(line +"\n");
			        }
					reader.close();
			        is.close();
			        if(httpUrlConnection!=null)
			        	httpUrlConnection.disconnect();
			        result=sb.toString();
			       
				} catch (Exception e) {
					e.printStackTrace();
					a.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							acb.onError();
							
						}
					});
				}
				 final String fuck=result;
				 a.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							acb.onSuccess(fuck);
							
						}
					});
				 
			}
		}).start();
	}	
}
