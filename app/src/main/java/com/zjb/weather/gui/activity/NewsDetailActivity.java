package com.zjb.weather.gui.activity;

import android.content.Intent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zjb.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/1.
 */

public class NewsDetailActivity extends ActivityBase {
    @BindView(R.id.news_webview)
    WebView newsWebview;

    @Override
    protected void initDatas() {
        Intent intent = getIntent();
        String fromUrl = intent.getStringExtra("fromUrl");

        newsWebview.loadUrl(fromUrl);

        newsWebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        WebSettings settings = newsWebview.getSettings();
        settings.setJavaScriptEnabled(true);
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.news_detail;
    }


}
