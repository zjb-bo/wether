package com.zjb.weather.gui.mvp.model.news;

import com.zjb.weather.entry.NewsData;

/**
 * Created by bobo on 2016/10/30.
 */

public interface NewsModelDao {
    void loadData(String url,NewsDataCallback callback);

    public interface NewsDataCallback{
        void newsDataLoadSuccess(NewsData news);
        void newsDataLoadFailed();

        void newsDataNoMoreData();
    }
}
