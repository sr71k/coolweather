package com.hisun.coolweather.model;

public class County {
	
	public int id;
	public String county_name;
	public String county_code;
	public int cityId;
	
	
	public County() {
		super();
	}


	public County(int id, String county_name, String county_code, int cityId) {
		super();
		this.id = id;
		this.county_name = county_name;
		this.county_code = county_code;
		this.cityId = cityId;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCounty_name() {
		return county_name;
	}


	public void setCounty_name(String county_name) {
		this.county_name = county_name;
	}


	public String getCounty_code() {
		return county_code;
	}


	public void setCounty_code(String county_code) {
		this.county_code = county_code;
	}


	public int getCityId() {
		return cityId;
	}


	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

}
