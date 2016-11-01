package com.zjb.weather.gui.mvp.presenter.weather;

import com.zjb.weather.entry.City;
import com.zjb.weather.entry.County;
import com.zjb.weather.entry.Province;
import com.zjb.weather.entry.WeatherData;
import com.zjb.weather.gui.mvp.model.weather.RegionModelDao;
import com.zjb.weather.gui.mvp.model.weather.RegionModelImpl;
import com.zjb.weather.gui.mvp.view.weather.RegionViewDao;

import java.util.List;

/**
 * Created by Administrator on 2016/10/26.
 */

public class RegionPresenterImpl implements RegioPresenterDao {

    private RegionViewDao viewDao;
    private RegionModelDao modelDao;

    public RegionPresenterImpl(RegionViewDao viewDao) {
        this.viewDao = viewDao;
        modelDao = new RegionModelImpl();
    }

    @Override
    public void loadData(String url) {
        modelDao.loadRegion(url, new RegionModelDao.OnLoadRegionCallback() {
            @Override
            public void loadProvinceSuccess(List<Province> province) {
                viewDao.loadProvinceSuccess(province);
            }

            @Override
            public void loadCitySuccess(List<City> city) {
                viewDao.loadCitySuccess(city);
            }

            @Override
            public void loadCountySuccess(List<County> county) {
                viewDao.loadCountySuccess(county);
            }

            @Override
            public void loadWeatherCodeSuccess(String wetherCode) {
                viewDao.loadweatherCodeSuccess(wetherCode);
            }

            @Override
            public void loadWeatherDataSuccess(WeatherData weather) {
                viewDao.loadWeatherDataSuccess(weather);
            }

            @Override
            public void loadFailed() {
                viewDao.loadFailed();
            }
        });
    }
}
