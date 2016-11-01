package com.zjb.weather.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/31.
 */

public class MyDateUtil {
    /***
     * 时间戳转化为时间字符串
     * @param ms
     * @return
     */

    public static String timeStampToDateString(long ms){
        String time;
        Date date = new Date(ms);
        SimpleDateFormat format = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss");
        time = format.format(date);
        return time;
    }
}
