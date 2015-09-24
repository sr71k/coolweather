package com.hisun.coolweather.parser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.hisun.coolweather.db.CoolWeatherDB;
import com.hisun.coolweather.model.City;
import com.hisun.coolweather.model.County;
import com.hisun.coolweather.model.Province;

public class JSONParser {


	/***
	 * 解析并处理服务器返回的省份数据
	 * @param coolWeatherDB
	 * @param response
	 * @return
	 */
	public synchronized static boolean handlerProvinceResponse(CoolWeatherDB coolWeatherDB,String response){


		if(!TextUtils.isEmpty(response)){

			String[] allProvince=response.split(",");

			if(allProvince!=null&&allProvince.length>0){

				for(String p:allProvince){

					String[] array=p.split("\\|");

					Province province=new Province();

					province.setProvince_name(array[0]);
					province.setProvince_code(array[1]);

					coolWeatherDB.saveProvince(province);

				}

				return true;

			}


		}


		return false;

	}


	/***
	 * 解析并处理服务器返回的城市数据
	 * @param coolWeatherDB
	 * @param response
	 * @return
	 */

	public static  boolean handlerCityResponse(CoolWeatherDB coolWeatherDB,
			String response,int provinceId){


		if(!TextUtils.isEmpty(response)){

			String[] allCity=response.split(",");

			if(allCity!=null&&allCity.length>0){

				for(String p:allCity){

					String[] array=p.split("\\|");

					City city =new City();

					city.setCity_name(array[0]);
					city.setCity_code(array[1]);
					city.setProvinceId(provinceId);

					coolWeatherDB.saveCity(city);

				}

				return true;

			}


		}


		return false;


	}


	/**
	 * 解析并处理服务器返回的县城数据
	 * @param coolWeatherDB
	 * @param response
	 * @param cityId
	 * @return
	 */

	public static  boolean handlerCountyResponse(CoolWeatherDB coolWeatherDB,
			String response,int cityId){


		if(!TextUtils.isEmpty(response)){

			String[] allCounty=response.split(",");

			if(allCounty!=null&&allCounty.length>0){

				for(String p:allCounty){

					String[] array=p.split("\\|");

					County county =new County(); 


					county.setCounty_name(array[0]);
					county.setCounty_code(array[1]);
					county.setCityId(cityId);

					coolWeatherDB.saveCounty(county);

				}

				return true;

			}


		}


		return false;


	}


	public static void handleWeatherResponse(Context context,String jsonStr){

		if(jsonStr!=null){

			try {
				JSONObject json=new JSONObject(jsonStr);
				JSONObject weatherInfo = json.getJSONObject("weatherinfo");
				String cityName = weatherInfo.getString("city");
				String weatherCode = weatherInfo.getString("cityid");
				String temp1 = weatherInfo.getString("temp1");
				String temp2 = weatherInfo.getString("temp2");
				String weatherDesp = weatherInfo.getString("weather");
				String publishTime = weatherInfo.getString("ptime");


				saveWeatherInfo(context, cityName, weatherCode, temp1, temp2,
						weatherDesp, publishTime);


			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}


	private static void saveWeatherInfo(Context context, String cityName,
			String weatherCode, String temp1, String temp2, String weatherDesp,
			String publishTime) {


		SimpleDateFormat sdf= new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);

		SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(context).edit();

		editor.putBoolean("city_selected", true);
		editor.putString("city_name", cityName);
		editor.putString("weather_code", weatherCode);
		editor.putString("temp1", temp1);
		editor.putString("temp2", temp2);
		editor.putString("weather_desp", weatherDesp);
		editor.putString("publish_time", publishTime);
		editor.putString("current_date", sdf.format(new Date()));
		
		editor.commit();



	}



}
