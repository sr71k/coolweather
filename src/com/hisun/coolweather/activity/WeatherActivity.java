package com.hisun.coolweather.activity;

import com.hisun.coolweather.R;
import com.hisun.coolweather.parser.JSONParser;
import com.hisun.coolweather.service.AutoUpdateService;
import com.hisun.coolweather.util.HttpCallBackListenser;
import com.hisun.coolweather.util.HttpUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WeatherActivity  extends Activity implements OnClickListener{
	
	
	
	
	
	private LinearLayout weatherInfoLayout;
	/**
	 * ������ʾ������
	 */
	private TextView cityNameText;
	/**
	 * ������ʾ����ʱ��
	 */
	private TextView publishText;
	/**
	 * ������ʾ����������Ϣ
	 */
	private TextView weatherDespText;
	/**
	 * ������ʾ����1
	 */
	private TextView temp1Text;
	/**
	 * ������ʾ����2
	 */
	private TextView temp2Text;
	/**
	 * ������ʾ��ǰ����
	 */
	private TextView currentDateText;
	
	
	/**
	 * �л����а�ť
	 */
	private Button switchCity;
	/**
	 * ����������ť
	 */
	private Button refreshWeather;
	
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather_layout);
		// ��ʼ�����ؼ�
		weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
		cityNameText = (TextView) findViewById(R.id.city_name);
		publishText = (TextView) findViewById(R.id.publish_text);
		weatherDespText = (TextView) findViewById(R.id.weather_desp);
		temp1Text = (TextView) findViewById(R.id.temp1);
		temp2Text = (TextView) findViewById(R.id.temp2);
		currentDateText = (TextView) findViewById(R.id.current_date);
		
		switchCity=(Button) findViewById(R.id.switch_city);
		refreshWeather=(Button) findViewById(R.id.refresh_weather);
		
		switchCity.setOnClickListener(this);
		refreshWeather.setOnClickListener(this);
		
		
		String countyCode = getIntent().getStringExtra("county_code");
		
		if(!TextUtils.isEmpty(countyCode)){
			
			publishText.setText("ͬ����...");
			
			weatherInfoLayout.setVisibility(View.VISIBLE);
			
			cityNameText.setVisibility(View.VISIBLE);
			
			queryWeatherCode(countyCode);
			
			
		}else{
			
			showWeather();
		}
	}


	
	/**
	 * ��ѯ�ؼ������Ӧ����������
	 * @param countyCode
	 */
	private void queryWeatherCode(String countyCode) {
		
		String adress=""+countyCode+"";
		
		queryFromServer(adress,"countyCode");
		
	}
	
	
	/**
	 * ��ѯ�������Ŷ�Ӧ������
	 * @param weatherCode
	 */
	

	protected void queryWeatherInfo(String weatherCode) {
		// TODO Auto-generated method stub
		
	String adress=""+weatherCode+"";
		
		queryFromServer(adress,"weatherCode");
		
	}

	
	
	


	private void queryFromServer(final String adress, final String type) {
		
		HttpUtils.sendHttpRequest(adress, new HttpCallBackListenser() {
			
			@Override
			public void onFinish(String reaspon) {
				
				if("countyCode".equals(type)){
					
					if(!TextUtils.isEmpty(reaspon)){
						
						String[] array=reaspon.split(",");
						
						if(array!=null&&array.length==2){
							
							String weatherCode=array[0];
							
							queryWeatherInfo(weatherCode);
							
						}
						
						
					}
					
				}else if("weatherCode".equals(type)){
					
					//������������ص�������Ϣ
					
					JSONParser.handleWeatherResponse(WeatherActivity.this, reaspon);
					
					runOnUiThread( new Runnable() {
						public void run() {
							
							showWeather();
							
						}
					});
					
					
					
				}
				
				
				
			}
			
			@Override
			public void onError(Exception e) {
				
				
				runOnUiThread(new  Runnable() {
					public void run() {
						
						publishText.setText("ͬ��ʧ��");
						
					}
				});

			}
		});
		
	}





	private void showWeather() {
		
		SharedPreferences  prefs=PreferenceManager.getDefaultSharedPreferences(this);
		
		cityNameText.setText(prefs.getString("city_name", ""));
		
		temp1Text.setText(prefs.getString("temp1", ""));
		temp2Text.setText(prefs.getString("temp2", ""));
		
		weatherDespText.setText(prefs.getString("weather_desp", ""));
		publishText.setText("����" + prefs.getString("publish_time", "") + "����");
		currentDateText.setText(prefs.getString("current_date", ""));
		weatherInfoLayout.setVisibility(View.VISIBLE);
		cityNameText.setVisibility(View.VISIBLE);
		Intent intent=new Intent(this, AutoUpdateService.class);
		
		startService(intent);
		
	}



	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		
		case R.id.switch_city:
			
			Intent intent= new Intent(this,ChooseActivity.class);
			
			intent.putExtra("from_weather_acticity", true);
			
			startActivity(intent);
			
			finish();
			
			break;
			
		case R.id.refresh_weather:
			
			publishText.setText("ͬ����");
			
			SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
			
			String weatherCode=prefs.getString("weather_code", "");
			
			if(!TextUtils.isEmpty(weatherCode)){
				
				
				queryWeatherInfo(weatherCode);
			}
			
			
			break;

		default:
			break;
		}
		
		
	}

}
