package com.zjb.weather.gui.mvp.presenter.news;

import android.util.Log;

import com.zjb.weather.entry.NewsData;
import com.zjb.weather.gui.mvp.model.news.NewsModelDao;
import com.zjb.weather.gui.mvp.model.news.NewsModelImpl;
import com.zjb.weather.gui.mvp.view.news.NewsViewDao;

/**
 * Created by bobo on 2016/10/30.
 */

public class NewsPresenterImpl implements NewsPresenterDao{
    private NewsViewDao mNewsView;
    private NewsModelDao mNewsModel;
    private int newsPage = 1;
    private String url;

    public void setNewsPage(int newsPage) {
        this.newsPage = newsPage;
    }

    public NewsPresenterImpl(NewsViewDao mNewsView) {
        this.mNewsView = mNewsView;
        this.mNewsModel = new NewsModelImpl();
    }

    @Override
    public void loadNewsData() {
        url = "http://www.tngou.net/api/top/list?page="+newsPage;

        mNewsModel.loadData(url, new NewsModelDao.NewsDataCallback() {
            @Override
            public void newsDataLoadSuccess(NewsData news) {
                mNewsView.newsDataLoadSuccess(news);
                newsPage++;
                Log.d("msg", "newsDataLoadSuccess: "+newsPage);
            }

            @Override
            public void newsDataLoadFailed() {
                mNewsView.newsDataLoadFailed();
            }

            @Override
            public void newsDataNoMoreData() {
                mNewsView.newsNoMoreData();
            }
        });
    }

    @Override
    public void loadNewsDataFlush() {
        url = "http://www.tngou.net/api/top/list?page=1";

        mNewsModel.loadData(url, new NewsModelDao.NewsDataCallback() {
            @Override
            public void newsDataLoadSuccess(NewsData news) {
                mNewsView.newsDataLoadSuccess(news);
            }

            @Override
            public void newsDataLoadFailed() {
                mNewsView.newsDataLoadFailed();
            }

            @Override
            public void newsDataNoMoreData() {
                mNewsView.newsNoMoreData();
            }
        });
    }
}
