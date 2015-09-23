package com.hisun.coolweather.activity;

import java.util.ArrayList;
import java.util.List;

import com.hisun.coolweather.R;
import com.hisun.coolweather.db.CoolWeatherDB;
import com.hisun.coolweather.model.City;
import com.hisun.coolweather.model.County;
import com.hisun.coolweather.model.Province;
import com.hisun.coolweather.parser.JSONParser;
import com.hisun.coolweather.util.HttpCallBackListenser;
import com.hisun.coolweather.util.HttpUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.DownloadManager.Query;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseActivity extends Activity {


	public static final int LEVEL_PROVINCE=0;
	public static final int LEVEL_CITY=1;
	public static final int LEVEL_COUNTY=2;

	private TextView textView;
	private ProgressDialog dialog;
	private CoolWeatherDB coolWeatherDB;
	private ListView listview;
	private List<String> data=new ArrayList<String>();
	private ArrayAdapter<String> adapter;

	private List<Province> provinceList;

	private List<City> cityList;

	private List<County> countyList;

	private Province selectionProvince;

	private City selectionCity;

	private int currentLevel;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_area);

		setupView();

		adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, data);

		listview.setAdapter(adapter);

		coolWeatherDB=CoolWeatherDB.getInstance(this);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if(currentLevel==LEVEL_PROVINCE){

					selectionProvince=provinceList.get(position);

					queryCites();

				}else if(currentLevel==LEVEL_CITY){

					selectionCity=cityList.get(position);

					queryCounties();

				}	
			}		

		});

		queryProvince();
	}


	private void queryProvince() {

		provinceList=coolWeatherDB.loadProvince();

		if(provinceList.size()>0){

			data.clear();

			for(Province province:provinceList){

				data.add(province.getProvince_name());
			}

			adapter.notifyDataSetChanged();

			listview.setSelection(0);

			textView.setText("china");

			currentLevel=LEVEL_PROVINCE;

		}else{
			

			queryFromServer(null,"province");
		}



	}

	
	protected void queryCites() {
		
		cityList=coolWeatherDB.loadCity(selectionProvince.getId());
		
		if(cityList.size()>0){
			
			data.clear();
			
			for(City city:cityList){
				
				data.add(city.getCity_name());
			}
			
			adapter.notifyDataSetChanged();
			
			listview.setSelection(0);
			
			textView.setText(selectionProvince.getProvince_name());
			
			currentLevel=LEVEL_CITY;
			
			
			
			
		}else{
			
			queryFromServer(selectionProvince.getProvince_code(), "city");
		}

	}


	protected void queryCounties() {
		
		countyList=coolWeatherDB.loadCounty(selectionCity.getId());
		
		if(countyList.size()>0){
			
			for(County county:countyList){
				
				data.add(county.getCounty_name());
			}
			
			adapter.notifyDataSetChanged();
			
			listview.setSelection(0);
			
			textView.setText(selectionCity.getCity_name());
			
			currentLevel=LEVEL_COUNTY;

			
			
			
			
			
		}else{
			
			queryFromServer(selectionCity.getCity_code(), "county");
			
		}
		
		
		

	}

	private void queryFromServer(final String code, final String type) {

		String adress;

		if(!TextUtils.isEmpty(code)){

			adress=""+code+".xml";
		}else{

			adress="";
		}

		showProgerssdiaglog();

		HttpUtils.sendHttpRequest(adress,new HttpCallBackListenser() {

			@Override
			public void onFinish(String response) {

				boolean result=false;

				if("province".equals(type)){

					result=JSONParser.handlerProvinceResponse(coolWeatherDB, response);

				}else if("City".equals(type)){

					result=JSONParser.handlerCityResponse(coolWeatherDB, response, selectionProvince.getId());
				}else if("County".equals(type)){

					result=JSONParser.handlerCountyResponse(coolWeatherDB, response, selectionCity.getId());

				}

				if(result){
					runOnUiThread(new Runnable() {
						public void run() {

							closeProgressDialog();

							if("province".equals(type)){

								queryProvince();
							}else if("city".equals(type)){

								queryCites();
							}else if("county".equals(type)){

								queryCounties();
							}					

						}

					});


				}

			}

			@Override
			public void onError(Exception e) {

				runOnUiThread( new Runnable() {
					public void run() {

						closeProgressDialog();

						Toast.makeText(ChooseActivity.this, "jiazaishib", 0).show();

					}


				});


			}
		});



	}

	private void closeProgressDialog() {
		
		
		if(dialog!=null){
			
			dialog.dismiss();
		}

	}

	private void showProgerssdiaglog() {


		if(dialog==null){
			
			dialog=new ProgressDialog(this);
			
			dialog.setMessage("º”‘ÿ÷–°£°£°£");
			dialog.setCanceledOnTouchOutside(false);
			}
		
		dialog.show();

	}

	private void setupView() {

		textView=(TextView) findViewById(R.id.iv_title);

		listview=(ListView) findViewById(R.id.listview);

	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		if(currentLevel==LEVEL_COUNTY){
			
			queryCites();
		}else if(currentLevel==LEVEL_CITY){
			
			queryProvince();
		}else{
			
			finish();
		}
		
		
	}

}
