package com.coolweather.app.db;

import java.security.Principal;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import com.coolweather.app.model.City;
import com.coolweather.app.model.Country;
import com.coolweather.app.model.Province;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CoolWeatherDB {
	/*
	 * ���ݿ���
	 */
	public static final String DB_NAME = "cool_weather";
	
	/*
	 * �汾��
	 */
	public static final int VERSION = 1;
	
	private static CoolWeatherDB coolWeatherDB;
	
	private SQLiteDatabase db;
	
	/*
	 * ���췽��˽�л�
	 */
	private CoolWeatherDB(Context context){
		CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context, 
				DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/*
	 * ͬ��������ȡCoolWeatherDB����
	 */
	public synchronized CoolWeatherDB getInstance(Context context){
		if(coolWeatherDB == null)
			coolWeatherDB = new CoolWeatherDB(context);
		
		return coolWeatherDB;
	}
	
	/*
	 * д��ʡ�ݵ����ݿ�
	 */
	public void saveProvince(Province province){
		db.execSQL("insert into Province (province_name, province_code) values(?,?)",
				new String[]{province.getProvinceName(), province.getProvinceCode()});
	}
	
	/*
	 * ���ݿ��ж�������ʡ��
	 */
	public List<Province> loadProvinces(){
		List<Province> provinces = new ArrayList<>();
		Cursor cursor = db.rawQuery("select * from Province", null);
		
		if(cursor.moveToFirst()){
			do{
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.
						getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor
						.getColumnIndex("province_code")));
				
				provinces.add(province);
			}while(cursor.moveToNext());
		}
		
		if(cursor != null)
			cursor.close();
		return provinces;
	}
	
	/*
	 * ������е����ݿ�
	 */
	public void saveCity(City city){
		db.execSQL("insert into City (city_name, city_code," +
				"province_id) values(?,?,?)",
				new String[]{city.getCityName(), city.getCityCode(), 
				Integer.toString(city.getProvinceId())
				});
	}
	
	/*
	 * ���ݿ��е������г���
	 */
	public List<City> loadCities(){
		List<City> cities = new ArrayList<>();
		Cursor cursor = db.rawQuery("select * from City", null);
		
		if(cursor.moveToFirst()){
			do{
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.
						getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor
						.getColumnIndex("city_code")));
				city.setProvinceId(cursor.getInt(cursor.
						getColumnIndex("province_id")));
				
				cities.add(city);
			}while(cursor.moveToNext());
		}
		
		if(cursor != null)
			cursor.close();
		return cities;
	}
	
	/*
	 * �����سǵ����ݿ�
	 */
	public void saveCountry(Country country){
		db.execSQL("insert into Country (country_name, country_code," +
				"city_id) values(?,?,?)", new String[]{country.getCountryName(),
				country.getCountryCode(), Integer.toString(country.getCityId())});
	}
	
	/*
	 * �����ݿ��ж�ȡ�����س�
	 */
	public List<Country> loadCountry(){
		List<Country> countries = new ArrayList<Country>();
		
		Cursor cursor = db.rawQuery("select * from Country", null);
		
		if(cursor.moveToFirst()){
			do{
				Country country = new Country();
				country.setId(cursor.getInt(cursor.getColumnIndex("id")));
				country.setCountryName(cursor.getString(cursor.
						getColumnIndex("country_name")));
				country.setCountryCode(cursor.getString(cursor.
						getColumnIndex("country_code")));
				country.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
				
				countries.add(country);
			}while(cursor.moveToNext());
		}
		
		if(cursor != null)
			cursor.close();
		
		return countries;
	}
}
