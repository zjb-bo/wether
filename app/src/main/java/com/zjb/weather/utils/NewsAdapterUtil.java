package com.zjb.weather.utils;

import com.zjb.weather.entry.NewsData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bobo on 2016/10/31.
 */

public class NewsAdapterUtil {

    /**
     * 将所有的datas根据 topclass 归类
     * @param datas
     * @param topClass
     * @return
     */
    public static List<NewsData.TngouBean> changeType(List<NewsData.TngouBean> datas,int topClass){
        List<NewsData.TngouBean> list = new ArrayList<>();
        for (NewsData.TngouBean data:datas) {
            if (data.getTopclass() == topClass){
                list.add(data);
            }
        }
        return list;
    }

}
