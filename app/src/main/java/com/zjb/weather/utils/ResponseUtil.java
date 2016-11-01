package com.zjb.weather.utils;

import com.zjb.weather.entry.City;
import com.zjb.weather.entry.County;
import com.zjb.weather.entry.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 */

public class ResponseUtil {
    public static final int PROVICE_CODE = 2;
    public static final int CITY_CODE = 4;
    public static final int COUNTY_CODE = 6;
    public static final int WEATHER_CODE = 9;


    /**
     * 全国的省 集合
     * @param response
     * @return
     */
    public static List<Province> responseToProvice(String response){
        List<Province> list = new ArrayList<>();
        String[] provinces = response.split(",");
        for (String pro:provinces) {
            String[] province = pro.split("\\|");
            Province myPro = new Province(province[0],province[1]);
            list.add(myPro);
        }
        return list;
    }

    /**
     * 当前省下的市 集合
     * @param response
     * @return
     */
    public static List<City> responseToCity(String response,String province_id){
        List<City> list = new ArrayList<>();
        String[] cities = response.split(",");
        for (String cy:cities) {
            String[] city = cy.split("\\|");
            City myCity = new City(city[0],city[1],province_id);
            list.add(myCity);
        }
        return list;
    }


    /**
     * 当前市下的县 集合
     * @param response
     * @return
     */
    public static List<County> responseToCounty(String response, String province_id){
        List<County> list = new ArrayList<>();
        String[] couties = response.split(",");
        for (String cy:couties) {
            String[] county = cy.split("\\|");
            County myCounty = new County(county[0],county[1],province_id);
            list.add(myCounty);
        }
        return list;
    }

    /**
     * 判断返回城市的代码长度，判断为是省  市  县 或者 天气
     * @param response
     * @return
     */
    public static int responseIdLength(String response){
        String[] strs = response.split(",");
            String[] str = strs[0].split("\\|");
            return str[0].length();
    }

    /****
     * 返回天气代码位数
     * @param response
     * @return
     */
    public static int responseNameLength(String response){
        String[] strs = response.split(",");
        String[] str = strs[0].split("\\|");
        return str[1].length();
    }

    /****
     * 返回天气代码内容
     * @param response
     * @return
     */
    public static String responseNameContent(String response){
        String[] strs = response.split(",");
        String[] str = strs[0].split("\\|");
        return str[1];
    }

    /***
     * 判断数据返回是不是天气数据
     * @param reslut
     * @return
     */
    public static boolean isWeatherData(String reslut){
        boolean isWeatherData = false;
        if (reslut.startsWith("{\"weatherinfo\"")){
            isWeatherData = true;
        }else{
            isWeatherData = false;
        }
        return isWeatherData;
    }

}
