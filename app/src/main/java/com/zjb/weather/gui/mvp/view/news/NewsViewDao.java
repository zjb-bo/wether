package com.zjb.weather.gui.mvp.view.news;

import com.zjb.weather.entry.NewsData;

/**
 * Created by bobo on 2016/10/30.
 */

public interface NewsViewDao {
    void newsDataLoadSuccess(NewsData news);
    void newsDataLoadFailed();
    void newsNoMoreData();
}
