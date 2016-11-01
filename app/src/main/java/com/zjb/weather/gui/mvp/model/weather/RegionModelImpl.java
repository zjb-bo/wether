package com.zjb.weather.gui.mvp.model.weather;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zjb.weather.entry.City;
import com.zjb.weather.entry.County;
import com.zjb.weather.entry.Province;
import com.zjb.weather.entry.WeatherData;
import com.zjb.weather.utils.ResponseUtil;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 */

public class RegionModelImpl implements RegionModelDao {
    private OkHttpClient mOkhttpClient;
    private Request mRequest;
    private OnLoadRegionCallback callback;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    callback.loadProvinceSuccess((List<Province>) msg.obj);
                    break;
                case 2:
                    callback.loadCitySuccess((List<City>) msg.obj);
                    break;
                case 3:
                    callback.loadCountySuccess((List<County>) msg.obj);
                    break;
                case 4:
                    callback.loadWeatherDataSuccess((WeatherData) msg.obj);
                    break;
                case 5:
                    callback.loadWeatherCodeSuccess((String) msg.obj);
            }
        }
    };

    @Override
    public void loadRegion(String url, final OnLoadRegionCallback callback) {

        this.callback = callback;
        mOkhttpClient = new OkHttpClient();
        mRequest = new Request.Builder()
                .url(url)
                .build();
        final Call call = mOkhttpClient.newCall(mRequest);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                callback.loadFailed();
            }
            @Override
            public void onResponse(Response response) throws IOException {
                if(response == null || response.equals(""))return;

                String result = response.body().string();
                Log.d("msg", "onResponse: "+result);
                if(ResponseUtil.isWeatherData(result)){
                    Log.d("msg", "result: "+result);
                    WeatherData weather = JSON.parseObject(result,WeatherData.class);
                    handler.sendMessage(handler.obtainMessage(4,weather));

                }
                switch (ResponseUtil.responseIdLength(result)){
                    case ResponseUtil.PROVICE_CODE:
                        List<Province> pro = ResponseUtil.responseToProvice(result);
                        handler.sendMessage(handler.obtainMessage(1,pro));
                        break;
                    case ResponseUtil.CITY_CODE:
                        List<City> city = ResponseUtil.responseToCity(result,"");
                        handler.sendMessage(handler.obtainMessage(2,city));
                        break;
                    case ResponseUtil.COUNTY_CODE:
                        int code = ResponseUtil.responseNameLength(result);
                        if(code == ResponseUtil.WEATHER_CODE){
                            handler.sendMessage(handler.obtainMessage(5,
                                    ResponseUtil.responseNameContent(result)));
                            break;
                        }
                        List<County> count = ResponseUtil.responseToCounty(result,"");
                        handler.sendMessage(handler.obtainMessage(3,count));
                        break;
                }

            }
        });


    }
}
