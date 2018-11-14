package com.toplyh.latte.core.net;

import com.toplyh.latte.core.app.ConfigType;
import com.toplyh.latte.core.app.Latte;

import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestCreator {

    public static RestService getRestService(){
        return RestServiceHolder.REST_SERVICE;
    }

    private static final class ParamsHolder{
        private static final WeakHashMap<String,Object> PARAMS=new WeakHashMap<>();
    }

    public static WeakHashMap<String,Object> getParams(){
        return ParamsHolder.PARAMS;
    }
    //懒惰生成retrofitclient
    private static final class RetrofitHolder{
        private static final String BASE_URL= (String) Latte.getConfigurations()
                .get(ConfigType.API_HOST.name());
        @SuppressWarnings("ConstantConditions")
        private static final Retrofit RETROFIT_CLIENT=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    //懒惰生成okhttpclient
    private static final class OKHttpHolder{
        private static final int TIME_OUT=60;
        private static final OkHttpClient OK_HTTP_CLIENT=new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT,TimeUnit.SECONDS)
                .build();
    }

    //通过RetrofitClient生成RestService实例
    private static final class RestServiceHolder{
        private static final RestService REST_SERVICE=
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }
}
