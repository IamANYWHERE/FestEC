package com.toplyh.festec.example;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.toplyh.latte.core.app.Latte;
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
                .withApiHost("http://127.0.0.1/")
                .withInterceptor(new DebugInterceptor("index",R.raw.test))
                .withAppId("")
                .withAppSecret("")
                .configure();
        initStetho();
        Logger.addLogAdapter(new AndroidLogAdapter());
        DatabaseManager.getInstance().init(this);
    }

    private void initStetho(){
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }
}
