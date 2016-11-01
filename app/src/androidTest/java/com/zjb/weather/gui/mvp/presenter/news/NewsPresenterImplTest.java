package com.zjb.weather.gui.mvp.presenter.news;

import android.util.Log;

import com.zjb.weather.entry.NewsData;
import com.zjb.weather.gui.mvp.view.news.NewsViewDao;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Administrator on 2016/11/1.
 */
public class NewsPresenterImplTest implements NewsViewDao{
    @Before
    public void setUp() throws Exception {

    }
    @Test
    public void loadNewsData() throws Exception {
        NewsPresenterImpl presenter = new NewsPresenterImpl(this);
        presenter.loadNewsData();
    }

    @Override
    public void newsDataLoadSuccess(NewsData news) {
        Log.d("NewsPresenterImplTest11", "news.getTngou().size():" + news.getTngou().size());
    }

    @Override
    public void newsDataLoadFailed() {

    }

    @Override
    public void newsNoMoreData() {

    }
}