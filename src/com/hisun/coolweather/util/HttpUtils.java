package com.hisun.coolweather.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpUtils {
	
	
	public static void sendHttpRequest(final String adress,
			          final HttpCallBackListenser listenser){
		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				HttpURLConnection connection=null;
				
				try {
					
					URL url=new URL(adress);
					
					connection=(HttpURLConnection) url.openConnection();
					
					connection.setRequestMethod("GET");
					
					connection.setConnectTimeout(8000);
					
					connection.setReadTimeout(6000);
					
					InputStream in =connection.getInputStream();
					
					BufferedReader reader=new BufferedReader(new InputStreamReader(in));
					
					StringBuilder respone=new StringBuilder();
					
					String line;
					
					if((line=reader.readLine())!=null){
						
						respone.append(line);	
					}
					
					
					if(listenser!=null){
						
						listenser.onFinish(respone.toString());
							
					}
					
					
				} catch (Exception e) {
					
					if(listenser!=null){
						listenser.onError(e);
					}
				}finally{
					
					if(connection!=null){
						
						connection.disconnect();
					}
				}

			}
		}).start();
		
		
		
	}
	

}
