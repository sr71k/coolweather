package com.hisun.coolweather.parser;

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





}
