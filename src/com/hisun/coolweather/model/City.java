package com.hisun.coolweather.model;

public class City {
	
	public int id;
	public String city_name;
	public String city_code;
	public int provinceId;
	
	
	
	public City() {
		super();
	}



	public City(int id, String city_name, String city_code, int provinceId) {
		super();
		this.id = id;
		this.city_name = city_name;
		this.city_code = city_code;
		this.provinceId = provinceId;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getCity_name() {
		return city_name;
	}



	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}



	public String getCity_code() {
		return city_code;
	}



	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}



	public int getProvinceId() {
		return provinceId;
	}



	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
	
	

}
