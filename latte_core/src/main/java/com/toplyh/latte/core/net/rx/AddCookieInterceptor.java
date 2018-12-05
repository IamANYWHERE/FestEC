package com.toplyh.latte.core.net.rx;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.toplyh.latte.core.util.storage.LattePreference;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookieInterceptor implements Interceptor {
    @SuppressLint("CheckResult")
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        Observable.just(LattePreference.getCustomAppProfile("cookie"))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull String cookie) throws Exception {
                        //给原生API请求附带上WebView拦截下来的Cookie
                        builder.addHeader("cookie",cookie);
                    }
                });
        return chain.proceed(builder.build());
    }
}
