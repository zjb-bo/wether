package com.zjb.weather.gui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zjb.weather.R;
import com.zjb.weather.entry.City;
import com.zjb.weather.entry.County;
import com.zjb.weather.entry.Province;
import com.zjb.weather.entry.WeatherData;
import com.zjb.weather.gui.adapter.RegionRecyclerAdapter;
import com.zjb.weather.gui.mvp.presenter.weather.RegioPresenterDao;
import com.zjb.weather.gui.mvp.presenter.weather.RegionPresenterImpl;
import com.zjb.weather.gui.mvp.view.weather.RegionViewDao;
import com.zjb.weather.utils.Contanst;
import com.zjb.weather.utils.Dialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/24.
 */

public class WeatherFragment extends BaseFragment {
    @BindView(R.id.weather_title_city)
    TextView weatherTitleCity;
    @BindView(R.id.weather_title_flush)
    Button weatherTitleFlush;
    @BindView(R.id.weather_city_recyclerview)
    RecyclerView weatherCityRecyclerview;
    @BindView(R.id.weather_ptime)
    TextView weatherPtime;
    @BindView(R.id.weather_high_tmp)
    TextView weatherHighTmp;
    @BindView(R.id.weather_detail)
    TextView weatherDetail;
    @BindView(R.id.weather_detail2)
    TextView weatherDetail2;
    @BindView(R.id.weather_relative)
    RelativeLayout weatherRelative;
    private boolean flag;
    private LinearLayout.LayoutParams params;
    private RegionRecyclerAdapter provinceAdapter;
    private RegioPresenterDao presenter;
    private boolean isLoading;
    private String currentWeatherCode;

    @Override
    public int getLayoutId() {
        return R.layout.weatherfragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 7);
        weatherCityRecyclerview.setLayoutManager(manager);

        provinceAdapter = new RegionRecyclerAdapter<Province>(getActivity());
        final RegionRecyclerAdapter cityAdapter = new RegionRecyclerAdapter<City>(getActivity());
        final RegionRecyclerAdapter countyAdapter = new RegionRecyclerAdapter<County>(getActivity());

        weatherCityRecyclerview.setAdapter(provinceAdapter);
        final Dialog dialog = new Dialog(getActivity());

        presenter = new RegionPresenterImpl(new RegionViewDao() {
            @Override
            public void loadProvinceSuccess(List<Province> province) {
                provinceAdapter.addDatas(province, Contanst.PROVINCE_TYPE);

            }

            @Override
            public void loadCitySuccess(List<City> city) {
                cityAdapter.addDatas(city, Contanst.CITY_TYPE);
                weatherCityRecyclerview.setVisibility(View.VISIBLE);

            }

            @Override
            public void loadCountySuccess(List<County> county) {
                countyAdapter.addDatas(county, Contanst.COUNTY_TYPE);
                weatherCityRecyclerview.setVisibility(View.VISIBLE);

            }

            @Override
            public void loadweatherCodeSuccess(String weatherCode) {
                currentWeatherCode = weatherCode;
                dialog.startLoadDailog();
                presenter.loadData("http://www.weather.com.cn/data/cityinfo/" + weatherCode + ".html");

            }

            @Override
            public void loadWeatherDataSuccess(WeatherData weather) {
                setWeatherData(weather);
                dialog.cancleLoadingDailog();
            }

            @Override
            public void loadFailed() {
                dialog.cancleLoadingDailog();
                Toast.makeText(getActivity(), "加载失败~", Toast.LENGTH_SHORT).show();
            }

        });
        /**选择当前省**/
        provinceAdapter.setOnMyItemClickListener(new RegionRecyclerAdapter.OnMyItemClickListener() {
            @Override
            public void onItemClick(int posion, View view, Object obj) {
                Province pro = (Province) obj;
                presenter.loadData("http://www.weather.com.cn/data/list3/city" + pro.getId() + ".xml");
                weatherTitleCity.setText(pro.getName());
                weatherCityRecyclerview.setVisibility(View.INVISIBLE);
                weatherCityRecyclerview.setAdapter(cityAdapter);

            }
        });
        /**选择当前市**/
        cityAdapter.setOnMyItemClickListener(new RegionRecyclerAdapter.OnMyItemClickListener() {
            @Override
            public void onItemClick(int posion, View view, Object obj) {
                City city = (City) obj;
                presenter.loadData("http://www.weather.com.cn/data/list3/city" + city.getId() + ".xml");
                weatherTitleCity.setText(city.getName());
                weatherCityRecyclerview.setVisibility(View.INVISIBLE);
                weatherCityRecyclerview.setAdapter(countyAdapter);
            }
        });

        /**选择当前县**/
        countyAdapter.setOnMyItemClickListener(new RegionRecyclerAdapter.OnMyItemClickListener() {
            @Override
            public void onItemClick(int posion, View view, Object obj) {
                County county = (County) obj;
                String url = "http://www.weather.com.cn/data/list3/city" + county.getId() + ".xml";
                Log.d("msg", "onItemClick: " + url);
                presenter.loadData(url);
                weatherCityRecyclerview.setVisibility(View.INVISIBLE);
                weatherTitleCity.setText(county.getName());
                weatherCityRecyclerview.setVisibility(View.GONE);
            }
        });

        /***
         * 点击刷新按钮
         * 刷新当前城市的天气
         */
        weatherTitleFlush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentWeatherCode == null){
                    return;
                }
                dialog.startLoadDailog();
                Log.d("msg", "onClick: "+currentWeatherCode);
                presenter.loadData("http://www.weather.com.cn/data/cityinfo/" + currentWeatherCode + ".html");

            }
        });

        /***
         * 选择城市显示 天气
         */
        weatherTitleCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = !flag;
                if (flag) {
                    setRegionOn();
                } else {
                    setRegionOff();
                }
            }
        });
    }

    private void setRegionOff() {
        weatherCityRecyclerview.setVisibility(View.GONE);

          /**这里不能写layoutParams.bottomMargin = -40;
           * 只能像下面这样写、、、**/
//        int height = 40;
//        layoutParams.bottomMargin = -height;
    }

    private void setRegionOn() {
        presenter.loadData("http://www.weather.com.cn/data/list3/city.xml");
        weatherCityRecyclerview.setAdapter(provinceAdapter);
        weatherCityRecyclerview.setVisibility(View.VISIBLE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    /**
     * 天气信息显示在UI控件上
     * @param weather
     */
    public void setWeatherData(WeatherData weather){
        WeatherData.WeatherinfoBean weatherinfo = weather.getWeatherinfo();
        weatherPtime.setText("更新时间："+weatherinfo.getPtime());
        weatherHighTmp.setText(weatherinfo.getTemp2());
        weatherDetail.setText(weatherinfo.getTemp1()+" ~ "+weatherinfo.getTemp2());
        weatherDetail2.setText(weatherinfo.getWeather());
    }
}
