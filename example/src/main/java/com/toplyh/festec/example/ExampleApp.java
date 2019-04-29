package com.toplyh.festec.example;

import android.app.Application;
import android.support.annotation.Nullable;

import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.toplyh.festec.example.event.ShareEvent;
import com.toplyh.latte.core.global.Latte;
import com.toplyh.festec.example.event.TestEvent;
import com.toplyh.latte.core.net.interceptors.DebugInterceptor;
import com.toplyh.latte.core.net.rx.AddCookieInterceptor;
import com.toplyh.latte.core.util.callback.CallbackManager;
import com.toplyh.latte.core.util.callback.CallbackType;
import com.toplyh.latte.core.util.callback.IGlobalCallback;
import com.toplyh.latte.ec.database.DatabaseManager;
import com.toplyh.latte.ec.icon.FontEcModule;

import cn.jpush.android.api.JPushInterface;

public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withNativeApiHost("http://mock.fulingjie.com/mock/")
                .withWebApiHost("https://www.baidu.com/")
                .withInterceptor(new DebugInterceptor("index", R.raw.test))
                //添加Cookie同步拦截器
                .withInterceptor(new AddCookieInterceptor())
                .withAppId("wx7965b4ad729aadf9")
                .withAppSecret("9f278ff963aa9b5a908cb2dbfb88b884")
                .withJavascriptInterface("latte")
                .withWebEvent("test", new TestEvent())
                .withWebEvent("share",new ShareEvent())
                .withOSSEndPoint("oss-cn-hongkong.aliyuncs.com")
                .withStsServer("http://47.94.12.38:7080/sts")
                .withBucketName("toplyh")
                .withUploadDir("FestEC")
                .withOSSClient(15 * 1000, 15 * 1000, 5, 2)
                .configure();
        //initStetho();
        DatabaseManager.getInstance().init(this);
        //开起极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        CallbackManager.getInstance()
                .addCallback(CallbackType.TAG_OPEN_PUSH, new IGlobalCallback() {
                    @Override
                    public void execute(@Nullable Object args) {
                        if (!JPushInterface.isPushStopped(Latte.getApplicationContext()))
                        JPushInterface.stopPush(Latte.getApplicationContext());
                    }
                })
                .addCallback(CallbackType.TAG_STOP_PUSH, new IGlobalCallback() {
                    @Override
                    public void execute(@Nullable Object args) {
                        if (JPushInterface.isPushStopped(Latte.getApplicationContext())) {
                            JPushInterface.resumePush(Latte.getApplicationContext());
                        }
                    }
                });
        //初始化fMobSDK
        //MobSDK.init(this,"290ef906847f8","4ab91220520821129a7b24fc3b7243bb");
    }

    private void initStetho() {
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }
}
