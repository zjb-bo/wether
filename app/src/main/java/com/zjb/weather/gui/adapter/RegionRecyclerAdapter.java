package com.zjb.weather.gui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjb.weather.R;
import com.zjb.weather.entry.City;
import com.zjb.weather.entry.County;
import com.zjb.weather.entry.Province;
import com.zjb.weather.utils.Contanst;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/26.
 */

public class RegionRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private List<T> data;
    private int type;

    public RegionRecyclerAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        data = new ArrayList<>();
    }

    /***
     * 添加数据
     *
     * @param list
     */
    public void addDatas(List<T> list,int type) {
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
        this.type = type;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.weather_region_recyclerview_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        switch (type){
            case Contanst.CITY_TYPE:
                myViewHolder.itemTextview.setText(((City)data.get(position)).getName());
                break;
            case Contanst.COUNTY_TYPE:
                myViewHolder.itemTextview.setText(((County)data.get(position)).getName());
                break;
            case Contanst.PROVINCE_TYPE:
                myViewHolder.itemTextview.setText(((Province)data.get(position)).getName());
                break;
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public  class MyViewHolder extends  RecyclerView.ViewHolder {
        @BindView(R.id.item_textview)
        TextView itemTextview;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            //设置回调监听
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onItemClick(getAdapterPosition(),v,data.get(getAdapterPosition()));
                    }
                }
            });

        }
    }


    //设置一个回调方法，供weatherFragment调用，什么位置的view 被点击了
    private  OnMyItemClickListener listener;
    public interface OnMyItemClickListener{
        void onItemClick(int posion, View view,Object obj);
    }
    public void setOnMyItemClickListener(OnMyItemClickListener listener){
        this.listener = listener;
    }
}
