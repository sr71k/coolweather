package com.hisun.coolweather.model;

public class Province {
	
	public int id;
	public String province_name;
	public String province_code;
	
	public Province(int id, String province_name, String province_code) {
		super();
		this.id = id;
		this.province_name = province_name;
		this.province_code = province_code;
	}

	public Province() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProvince_name() {
		return province_name;
	}

	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}

	public String getProvince_code() {
		return province_code;
	}

	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}
	

}
