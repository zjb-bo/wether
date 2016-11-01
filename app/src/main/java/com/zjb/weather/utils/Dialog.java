package com.zjb.weather.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by bobo on 2016/10/29.
 */

public class Dialog {
    private  ProgressDialog dialog;
    private Context context;

    public Dialog(Context context) {
        this.context = context;
        dialog = new ProgressDialog(context);
    }

    /***
     * 开始显示加载框
     */
    public  void startLoadDailog(){
        if(dialog != null){
            dialog.setMessage("正在加载");
            dialog.show();
        }
    }

    /***
     * 取消加载框
     */
    public  void cancleLoadingDailog(){
        if(dialog != null){
            dialog.cancel();
        }
    }
}
