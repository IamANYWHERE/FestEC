package com.toplyh.latte.core.fragments.web.client;

import android.graphics.Bitmap;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.toplyh.latte.core.fragments.IPageLoadListener;
import com.toplyh.latte.core.fragments.web.WebDelegate;
import com.toplyh.latte.core.fragments.web.route.Router;

import com.toplyh.latte.core.global.ConfigKeys;
import com.toplyh.latte.core.global.Latte;
import com.toplyh.latte.core.ui.loader.LatteLoader;
import com.toplyh.latte.core.util.log.LatteLogger;
import com.toplyh.latte.core.util.storage.LattePreference;

public class WebViewClientImpl extends WebViewClient {

    private final WebDelegate DELEGATE;
    private IPageLoadListener mIPageLoadListener = null;

    public void setPageLoadListener(IPageLoadListener loadListener) {
        this.mIPageLoadListener = loadListener;

    }

    public WebViewClientImpl(WebDelegate delegate) {
        DELEGATE = delegate;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LatteLogger.d("shouldOverrideUrlLoading", url);
        return Router.getInstance().handleWebUrl(DELEGATE, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadStart();
        }
        LatteLoader.showLoading(view.getContext());
    }

    //获取浏览器中的cookie
    private void syncCookie(){
        final CookieManager manager = CookieManager.getInstance();
        /*
        注意这里的Cookie和API请求的Cookie是不一样的，这个在网页中不可见
         */
        final String webHost = Latte.getConfiguration(ConfigKeys.WEB_API_HOST);
        if (webHost!=null){
            if (manager.hasCookies()){
                final String cookieStr = manager.getCookie(webHost);
                if (cookieStr!=null&&!cookieStr.equals("")){
                    LattePreference.addCustomAppProfile("cookie",cookieStr);
                }
            }
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        syncCookie();
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadEnd();
        }
        LatteLoader.stopLoading();
    }
}
