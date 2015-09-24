package com.hisun.coolweather.service;

import com.hisun.coolweather.parser.JSONParser;
import com.hisun.coolweather.receive.AutoUpdateReceiver;
import com.hisun.coolweather.util.HttpCallBackListenser;
import com.hisun.coolweather.util.HttpUtils;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

public class AutoUpdateService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				updateWeather();
				
			}
		}).start();
		
		
		AlarmManager manager=(AlarmManager) this.getSystemService(ALARM_SERVICE);
		
		int anHour=8*60*60*1000;
		
		long triggerAtTime=SystemClock.elapsedRealtime()+anHour;
		
		Intent i=new Intent(this, AutoUpdateReceiver.class);
		
		PendingIntent pi=PendingIntent.getBroadcast(this, 0, i, 0);
		
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime, pi);
		
		return super.onStartCommand(intent, flags, startId);
		
	
	}

	protected void updateWeather() {
		
		SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
		
		String weatherCode=prefs.getString("weather_code", "");
		
		String address=""+weatherCode;
		
		HttpUtils.sendHttpRequest(address,new HttpCallBackListenser() {
			
			@Override
			public void onFinish(String reaspon) {
				
				JSONParser.handleWeatherResponse(AutoUpdateService.this, reaspon);
				
			}
			
			@Override
			public void onError(Exception e) {
				
				e.printStackTrace();
				
			}
		});
		
	}
	
	
	
	
}
