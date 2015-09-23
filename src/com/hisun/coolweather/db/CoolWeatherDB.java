package com.hisun.coolweather.db;

import java.util.ArrayList;
import java.util.List;

import com.hisun.coolweather.model.City;
import com.hisun.coolweather.model.County;
import com.hisun.coolweather.model.Province;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CoolWeatherDB {

	/*
	 * 数据库名
	 */

	public static final String DB_NAME="coolweather.db";

	/*
	 * 数据库版本
	 */

	public static final int VERSION=1;

	private static CoolWeatherDB coolWeatherDB;

	private SQLiteDatabase db;



	/*
	 * 构造方法私有化
	 * */

	private CoolWeatherDB(Context context){

		CoolWeatherOpenHelper coolWeatherOpenHelper=new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);

		db=coolWeatherOpenHelper.getWritableDatabase();
	}


	/***
	 * 获取实例
	 * @param context
	 * @return
	 */

	public synchronized static CoolWeatherDB getInstance(Context context){


		if(coolWeatherDB==null){

			coolWeatherDB=new CoolWeatherDB(context);
		}

		return coolWeatherDB;
	}


	/****
	 * 将Province实例储存到数据库
	 * @param province
	 */
	public void saveProvince(Province province){

		if(province!=null){

			ContentValues values= new ContentValues();

			values.put("province_name", province.getProvince_name());
			values.put("province_code", province.getProvince_code());

			db.insert("Province", null, values);

		}

	}

	/****
	 * 从数据库查询province
	 * @return
	 */
	public List<Province> loadProvince(){

		List<Province>  list=new ArrayList<Province>();

		Cursor cursor=db.query("Province",null, null, 
				null, null, 
				null, null);

		if(cursor.moveToFirst()){

			do{
				Province province=new Province();

				province.setId(cursor.getInt(cursor.getColumnIndex("id")));

				province.setProvince_name(cursor.getString(cursor.getColumnIndex("province_name")));

				province.setProvince_code(cursor.getString(cursor.getColumnIndex("province_code")));

				list.add(province);

			}while(cursor.moveToNext());	
		}

		return list;

	}


	public void saveCity(City city ){


		if(city!=null){

			ContentValues values=new ContentValues();

			values.put("id", city.getId());
			values.put("city_name", city.getCity_name());
			values.put("city_code", city.getCity_code());
			values.put("province_id", city.getProvinceId());

			db.insert("City", null, values);
		}

	}



	public List<City> loadCity(int provinceID){
		
		List<City>  list =new ArrayList<City>();


		Cursor cursor=db.query("City", 
				null, "province_id=?",new String[]{String.valueOf(provinceID)}, null, null, null);



		if(cursor.moveToFirst()){

			do{
				City city=new City();

				city.setId(cursor.getInt(cursor.getColumnIndex("id")));

				city.setCity_name(cursor.getString(cursor.getColumnIndex("city_name")));

				city.setCity_code(cursor.getString(cursor.getColumnIndex("city_code")));

				list.add(city);

			}while(cursor.moveToNext());	
		}
		
		return list;

	}

	
	public void saveCounty(County county){
		
		
		if(county!=null){
			
			ContentValues values=new ContentValues();
			
			values.put("id", county.getId());
			values.put("county_name", county.getCounty_name());
			values.put("county_code", county.getCounty_code());
			values.put("city_id", county.getCityId());
			db.insert("County",null, values);
		}
		
		
	}
	
	
	public List<County> loadCounty(int cityID){
		
		List<County> list=new ArrayList<County>();
		
		
		Cursor cursor=db.query("County", null,"city_id=?", new String[]{String.valueOf(cityID)}, null, null, null);
		
		if(cursor.moveToFirst()){
			
			do{
				
				County county=new County();
				
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCounty_name(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCounty_code(cursor.getString(cursor.getColumnIndex("county_code")));
				county.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));				
				list.add(county);
			}while(cursor.moveToNext());

		}
		
		
		return list;
		
	}

}
