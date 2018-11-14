package com.toplyh.latte.core.net;

import android.content.Context;

import com.toplyh.latte.core.net.callback.IError;
import com.toplyh.latte.core.net.callback.IFailure;
import com.toplyh.latte.core.net.callback.IRequest;
import com.toplyh.latte.core.net.callback.ISuccess;
import com.toplyh.latte.core.ui.LoaderStyle;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RestClientBuilder {

    private String mUrl = null;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private IRequest mIRequest = null;
    private ISuccess mISuccess = null;
    private IFailure mIFailure = null;
    private IError mIError = null;
    private RequestBody mBody = null;
    private File mFile = null;
    private Context mContext = null;
    private LoaderStyle mLoaderStyle = null;

    RestClientBuilder() {

    }

    /**
     * 传入请求url地址
     *
     * @param url
     * @return
     */
    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    /**
     * 传入请求键值对
     *
     * @param params
     * @return
     */
    public final RestClientBuilder params(Map<String, Object> params) {
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
    public final RestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RestClientBuilder file(String filePath) {
        this.mFile = new File(filePath);
        return this;
    }

    /**
     * 传入原始数据到请求体里
     *
     * @param raw
     * @return
     */
    public final RestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess) {
        this.mISuccess = iSuccess;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure) {
        this.mIFailure = iFailure;
        return this;
    }

    public final RestClientBuilder error(IError iError) {
        this.mIError = iError;
        return this;
    }

    public final RestClientBuilder onRequest(IRequest iRequest) {
        this.mIRequest = iRequest;
        return this;
    }

    public final RestClientBuilder loader(Context context, LoaderStyle style) {
        this.mContext = context;
        this.mLoaderStyle = style;
        return this;
    }

    public final RestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RestClient build() {
        return new RestClient(mUrl, PARAMS, mIRequest, mISuccess, mIFailure, mIError, mBody, mFile, mContext, mLoaderStyle);
    }
}