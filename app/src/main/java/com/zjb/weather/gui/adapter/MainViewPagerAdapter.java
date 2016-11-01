package com.zjb.weather.gui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zjb.weather.gui.fragment.NewsFragment;
import com.zjb.weather.gui.fragment.PhoneTools;
import com.zjb.weather.gui.fragment.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 */

public class MainViewPagerAdapter extends FragmentPagerAdapter{
    private List<Fragment> datas;

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
        datas = new ArrayList<>();
        datas.add(new WeatherFragment());
        datas.add(new NewsFragment());
        datas.add(new PhoneTools());
    }

    @Override
    public Fragment getItem(int position) {
        return datas.get(position);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "天气预报";
            case 1:
                return "最新新闻";
            case 2:
                return "手机查询";
        }
        return "";
    }
}
