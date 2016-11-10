package com.zjb.weather.gui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zjb.weather.R;
import com.zjb.weather.entry.NewsData;
import com.zjb.weather.gui.activity.NewsDetailActivity;
import com.zjb.weather.gui.adapter.NewsRecyclerAdapter;
import com.zjb.weather.gui.mvp.presenter.news.NewsPresenterImpl;
import com.zjb.weather.gui.mvp.view.news.NewsViewDao;
import com.zjb.weather.utils.Contanst;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/24.
 */

public class NewsFragment extends BaseFragment implements NewsViewDao, NewsRecyclerAdapter.OnItemListener {
    RecyclerView newsRecyclerview;
    @BindView(R.id.news_all)
    TextView newsAll;
    @BindView(R.id.news_sports)
    TextView newsSports;
    @BindView(R.id.news_education)
    TextView newsEducation;
    @BindView(R.id.news_peplelife)
    TextView newsPeplelife;
    @BindView(R.id.news_entertainment)
    TextView newsEntertainment;
    @BindView(R.id.news_economics)
    TextView newsEconomics;
    @BindView(R.id.news_social)
    TextView newsSocial;
    private NewsRecyclerAdapter mAdapter;
    private boolean isLoading;
    private boolean isNoMoreData;
    private NewsPresenterImpl mNewsPrsenter;
    private LinearLayoutManager manger;
    //代表新闻的数据分类
    private int topClass = Contanst.NEWS_TYPE_ALL;

    @Override
    public int getLayoutId() {
        return R.layout.newsfragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        newsRecyclerview = (RecyclerView) getView().findViewById(R.id.news_recyclerview);

        mNewsPrsenter = new NewsPresenterImpl(this);
        mNewsPrsenter.loadNewsData();
        isLoading = true;

        manger = new LinearLayoutManager(getActivity());
        manger.setOrientation(LinearLayout.VERTICAL);
        newsRecyclerview.setLayoutManager(manger);

        mAdapter = new NewsRecyclerAdapter(getActivity());
        newsRecyclerview.setAdapter(mAdapter);


        newsRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!isLoading && (manger.findLastVisibleItemPosition() == manger.getItemCount() - 1)) {
                    if (!isNoMoreData) {
                        mNewsPrsenter.loadNewsData();
                        isLoading = true;
                    }
                }
            }
        });

        /**设置item监听*/
        mAdapter.setOnItemListener(this);
        /**设置当前选中类的*/
        mAdapter.setOnDataSizeIsZeroListener(new NewsRecyclerAdapter.OnDataSizeIsZero() {
            @Override
            public void currentDataSize() {
                    mNewsPrsenter.loadNewsData();
            }
        });
    }

    @Override
    public void newsDataLoadSuccess(NewsData news) {

        isLoading = false;
        isNoMoreData = false;
        /*列表底部显示正在加载...*/
        Contanst.footerType = 2;
        mAdapter.addMainDatas(news.getTngou(), topClass);
    }

    @Override
    public void newsDataLoadFailed() {
        isLoading = false;
        isNoMoreData = false;


        /*列表底部显示加载失败*/
        Contanst.footerType = 1;
        mAdapter.notifyDataSetChanged();
//        Toast.makeText(getActivity(), "加载失败，请重试~", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void newsNoMoreData() {
        isLoading = false;
        isNoMoreData = true;
        /*列表底部显示没有更多数据*/
        Contanst.footerType = 0;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    /**
     * 选择新闻分类事件
     *
     * @param view
     */
    private View currentView;
    @OnClick({R.id.news_all, R.id.news_sports, R.id.news_education,
            R.id.news_peplelife, R.id.news_entertainment, R.id.news_economics, R.id.news_social})
    public void onClick(View view) {
        if(currentView == view)return;

        if(currentView != null){
            currentView.setBackgroundResource(R.drawable.news_item_description);
        }

        switch (view.getId()){
            case R.id.news_all:
                topClass = Contanst.NEWS_TYPE_ALL;
                break;
            case R.id.news_economics:
                topClass = Contanst.NEWS_TYPE_ECO;
                break;
            case R.id.news_education:
                topClass = Contanst.NEWS_TYPE_EDU;
                break;
            case R.id.news_entertainment:
                topClass = Contanst.NEWS_TYPE_FUN;
                break;
            case R.id.news_sports:
                topClass = Contanst.NEWS_TYPE_SPORTS;
                break;
            case R.id.news_social:
                topClass = Contanst.NEWS_TYPE_SOC;
                break;
            case R.id.news_peplelife:
                topClass = Contanst.NEWS_TYPE_PEOPLE;
                break;

        }
        view.setBackgroundColor(Color.BLUE);
        currentView = view;
        mNewsPrsenter.loadNewsData();
    }


    @Override
    public void itemClick(int position, View view, String fromUrl) {
        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
        intent.putExtra("fromUrl",fromUrl);
        getActivity().startActivity(intent);
    }
}
