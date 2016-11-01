package com.zjb.weather.gui.mvp.model.news;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zjb.weather.entry.NewsData;

import java.io.IOException;

/**
 * Created by bobo on 2016/10/30.
 */

public class NewsModelImpl implements NewsModelDao{
    private OkHttpClient okHttpClient;
    private NewsDataCallback mCallback;

    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    mCallback.newsDataLoadFailed();
                    break;
                case 2:
                    mCallback.newsDataLoadSuccess((NewsData) msg.obj);
                    break;
                case 3:
                    mCallback.newsDataNoMoreData();
                    break;
            }
        }
    };

    @Override
    public void loadData(String url, final NewsDataCallback callback) {
        mCallback = callback;
        okHttpClient = new OkHttpClient();
        Request mRequest = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call mcall = okHttpClient.newCall(mRequest);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mHandler.sendMessage(mHandler.obtainMessage(1));
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(response == null || response.equals(""))return;
                NewsData mNews = JSON.parseObject(response.body().string(),NewsData.class);
                if(mNews != null && mNews.getTngou().get(mNews.getTngou().size()-1).getId()==0){
                    mHandler.sendMessage(mHandler.obtainMessage(3));
                }
                mHandler.sendMessage(mHandler.obtainMessage(2,mNews));
            }
        });
    }
}
