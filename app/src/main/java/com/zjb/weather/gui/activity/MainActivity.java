package com.zjb.weather.gui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.zjb.weather.R;
import com.zjb.weather.gui.adapter.MainViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/24.
 */

public class MainActivity extends ActivityBase {
    @BindView(R.id.main_title)
    TextView mainTitle;
    @BindView(R.id.main_tablayout)
    TabLayout mainTablayout;
    @BindView(R.id.main_viewpager)
    ViewPager mainViewpager;



    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewpager.setAdapter(adapter);
        mainTablayout.setupWithViewPager(mainViewpager);
    }

    /**
     * 设置当前显示的view,返回显示layout的Id
     *
     * @return
     */
    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main;
    }

    /**
     * 初始化一些数据
     */
    @Override
    protected void initDatas() {

    }



}
