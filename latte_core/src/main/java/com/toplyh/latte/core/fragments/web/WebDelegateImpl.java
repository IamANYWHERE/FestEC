package com.toplyh.latte.core.fragments.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.toplyh.latte.core.fragments.IPageLoadListener;
import com.toplyh.latte.core.fragments.web.client.WebViewClientImpl;
import com.toplyh.latte.core.fragments.web.route.RouteKeys;
import com.toplyh.latte.core.fragments.web.route.Router;

public class WebDelegateImpl extends WebDelegate implements IUrHandler,IPageLoadListener{

    public static WebDelegateImpl create(String url){
        final Bundle args= new Bundle();
        args.putString(RouteKeys.URL.name(),url);
        final WebDelegateImpl delegate = new WebDelegateImpl();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public Object setLayout() {
        return getWebView();
    }

    @Override
    public IWebViewInitializer setInitializer() {
        return this;
    }

    @Override
    public IUrHandler setUrlHandler() {
        return this;
    }

    @Override
    public WebView initWebView(WebView webView) {
        return new WebViewInitializer().createWebView(webView);
    }

    @Override
    public WebViewClient initWebViewClient() {
        final WebViewClientImpl client = new WebViewClientImpl(this);
        client.setPageLoadListener(this);
        return client;
    }

    @Override
    public WebChromeClient initWebChromeClient() {
        return new WebChromeClient();
    }

    @Override
    public void handleUrl(WebDelegate delegate) {
        if (getUrl()!=null){
            //用原生的方式模拟web跳转并运行页面加载
            Router.getInstance().loadPage(this,getUrl());
        }
    }

    @Override
    public void onLoadStart() {

    }

    @Override
    public void onLoadEnd() {

    }
}
