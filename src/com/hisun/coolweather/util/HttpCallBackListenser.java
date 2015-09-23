package com.hisun.coolweather.util;

public interface HttpCallBackListenser {
	
	void onFinish(String reaspon);
	void onError(Exception e);

}
