package com.toplyh.festec.example;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.toplyh.latte.core.app.Latte;
import com.toplyh.festec.example.event.TestEvent;
import com.toplyh.latte.core.net.interceptors.DebugInterceptor;
import com.toplyh.latte.ec.database.DatabaseManager;
import com.toplyh.latte.ec.icon.FontEcModule;

public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withApiHost("http://mock.fulingjie.com/mock/data/")
                .withInterceptor(new DebugInterceptor("index",R.raw.test))
                .withAppId("wx7965b4ad729aadf9")
                .withAppSecret("9f278ff963aa9b5a908cb2dbfb88b884")
                .withJavascriptInterface("latte")
                .withWebEvent("test",new TestEvent())
                .withOSSEndPoint("oss-cn-hongkong.aliyuncs.com")
                .withStsServer("http://47.94.12.38:7080/sts")
                .withBucketName("toplyh")
                .withUploadDir("FestEC")
                .withOSSClient(15*1000,15*1000,5,2)
                .configure();
        //initStetho();
        DatabaseManager.getInstance().init(this);
    }

    private void initStetho(){
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }
}
