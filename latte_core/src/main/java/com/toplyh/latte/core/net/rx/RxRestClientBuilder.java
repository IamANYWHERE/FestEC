package com.toplyh.latte.core.net.rx;

import android.content.Context;

import com.toplyh.latte.core.net.RestClient;
import com.toplyh.latte.core.net.RestCreator;
import com.toplyh.latte.core.net.callback.IError;
import com.toplyh.latte.core.net.callback.IFailure;
import com.toplyh.latte.core.net.callback.IRequest;
import com.toplyh.latte.core.net.callback.ISuccess;
import com.toplyh.latte.core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RxRestClientBuilder {

    private String mUrl = null;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private RequestBody mBody = null;
    private File mFile = null;
    private Context mContext = null;
    private LoaderStyle mLoaderStyle = null;

    RxRestClientBuilder() {

    }

    /**
     * 传入请求url地址
     *
     * @param url
     * @return
     */
    public final RxRestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    /**
     * 传入请求键值对
     *
     * @param params
     * @return
     */
    public final RxRestClientBuilder params(Map<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    /**
     * 传入请求单个参数
     *
     * @param key
     * @param value
     * @return
     */
    public final RxRestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RxRestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RxRestClientBuilder file(String filePath) {
        this.mFile = new File(filePath);
        return this;
    }

    /**
     * 传入原始数据到请求体里
     *
     * @param raw
     * @return
     */
    public final RxRestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RxRestClientBuilder loader(Context context, LoaderStyle style) {
        this.mContext = context;
        this.mLoaderStyle = style;
        return this;
    }

    public final RxRestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RxRestClient build() {
        return new RxRestClient(mUrl, PARAMS, mBody, mFile, mContext, mLoaderStyle);
    }
}
