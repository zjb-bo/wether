package com.zjb.weather.gui.mvp.view.weather;

import com.zjb.weather.entry.City;
import com.zjb.weather.entry.County;
import com.zjb.weather.entry.Province;
import com.zjb.weather.entry.WeatherData;

import java.util.List;

/**
 * Created by Administrator on 2016/10/26.
 */

public interface RegionViewDao {
    /**全国所有省 加载**/
    void loadProvinceSuccess(List<Province> province);
    /**当前省下所有市 加载**/
    void loadCitySuccess(List<City> city);
    /**当前省下所有县 加载**/
    void loadCountySuccess(List<County> county);

    /**当前县的天气信息**/
    void loadWeatherDataSuccess(WeatherData weather);

    /**数据加载失败**/
    void loadFailed();

    /**天气代码 加载成功**/
    void loadweatherCodeSuccess(String weatherCode);
}
