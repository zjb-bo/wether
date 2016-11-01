package com.zjb.weather.gui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zjb.weather.R;
import com.zjb.weather.entry.NewsData;
import com.zjb.weather.utils.Contanst;
import com.zjb.weather.utils.MyDateUtil;
import com.zjb.weather.utils.NewsAdapterUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zjb.weather.utils.NewsAdapterUtil.changeType;

/**
 * Created by bobo on 2016/10/30.
 */

public class NewsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<NewsData.TngouBean> mMainDatas,mCateMainDatas;
    private LayoutInflater inflater;

    private final int HEADER_ITEM = 0;
    private final int CONTENT_ITEM = 1;
    private final int FOOTER_ITEM = 2;


    public NewsRecyclerAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        mMainDatas = new ArrayList<>();
        mCateMainDatas = new ArrayList<>();
    }

    /**
     * 添加数据
     **/
    public void addMainDatas(List<NewsData.TngouBean> mData,int topClass) {
        mMainDatas.addAll(mData);
        addCateData(mData,topClass);
        notifyDataSetChanged();
    }

    /**
     * 添加分类数据 topclass为 0  代表全部数据
     * @param topClass
     */
    private int currentTopClass = -1;
    public void addCateData(List<NewsData.TngouBean> mData,int topClass){


        if(mMainDatas.size() != 0){
            if(topClass == Contanst.NEWS_TYPE_ALL){
                mCateMainDatas.clear();
                mCateMainDatas.addAll(mMainDatas);
            }else{
                /*如果分类有改变，则清除原来的;
                * 并且从已经加载好的列表中，找出新的分类**/
                if(currentTopClass != topClass){
                    currentTopClass = topClass;
                    mCateMainDatas.clear();
                    mCateMainDatas.addAll(NewsAdapterUtil.changeType(mMainDatas,topClass));
                }
                /**如果分类没有改变，则下一页中找出当前分类 并且添加到数据中**/
                else{
                    mCateMainDatas.addAll(changeType(mData,topClass));
                }
            }
            Log.d("msg", "addCateData: "+mCateMainDatas.size()+"cate");
            Log.d("msg", "addCateData: "+mMainDatas.size()+"all");
        }
    }


    public int getHeadaerItem() {
        return 0;
    }

    public int getFooterItem() {
        return 1;
    }


    @Override
    public int getItemViewType(int position) {
        if (position < getHeadaerItem()) {
            return HEADER_ITEM;
        } else if (position == getItemCount() - getFooterItem()) {
            return FOOTER_ITEM;
        } else {
            return CONTENT_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_ITEM) {
            return null;
        } else if (viewType == CONTENT_ITEM) {
            View view = inflater.inflate(R.layout.news_recyclerview_item, parent, false);
            return new ContentViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.news_recycleview_footer_item, parent, false);
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case HEADER_ITEM:
                break;
            case CONTENT_ITEM:
                    ContentViewHolder mHolder = (ContentViewHolder) holder;
                    NewsData.TngouBean mTngou = mCateMainDatas.get(position);
                    mHolder.newsCount.setText(mTngou.getCount() + "");
                    mHolder.newsDescripsion.setText(mTngou.getDescription());
                    mHolder.newsFcount.setText(mTngou.getFcount() + "");
                    mHolder.newsKeywords.setText("关键字：" + mTngou.getKeywords());
                    mHolder.newsRcount.setText(mTngou.getRcount() + "");
                    mHolder.newsTitle.setText(mTngou.getTitle());
                    /**将时间戳转换为日期**/
                    mHolder.newsTime.setText(MyDateUtil.timeStampToDateString(mTngou.getTime()));
                    mHolder.newsFrom.setText("来源：" + mTngou.getFromname());
                break;
            case FOOTER_ITEM:
                FooterViewHolder mFooterHolder = (FooterViewHolder) holder;
                switch (Contanst.footerType){
                    case 0:
                        mFooterHolder.newsFootProgressBar.setVisibility(View.GONE);
                        mFooterHolder.newsFootTitle.setVisibility(View.VISIBLE);
                        mFooterHolder.newsFootTitle.setText("没有更多数据了哦~~");
                        break;
                    case 1:
                        mFooterHolder.newsFootProgressBar.setVisibility(View.GONE);
                        mFooterHolder.newsFootTitle.setVisibility(View.VISIBLE);
                        mFooterHolder.newsFootTitle.setText("加载失败，请上下滑动重试");

                        break;
                    case 2:
                        mFooterHolder.newsFootProgressBar.setVisibility(View.VISIBLE);
                        mFooterHolder.newsFootTitle.setVisibility(View.GONE);
                        break;
                }
                break;
        }
    }

    @Override
    public int getItemCount() {


        return mCateMainDatas.size();
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_title)
        TextView newsTitle;
        @BindView(R.id.news_descripsion)
        TextView newsDescripsion;
        @BindView(R.id.news_keywords)
        TextView newsKeywords;
        @BindView(R.id.news_fcount)
        TextView newsFcount;
        @BindView(R.id.news_count)
        TextView newsCount;
        @BindView(R.id.news_rcount)
        TextView newsRcount;
        @BindView(R.id.news_time)
        TextView newsTime;
        @BindView(R.id.news_from)
        TextView newsFrom;

        ContentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.news_foot_title)
        TextView newsFootTitle;
        @BindView(R.id.news_foot_progressBar)
        ProgressBar newsFootProgressBar;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
