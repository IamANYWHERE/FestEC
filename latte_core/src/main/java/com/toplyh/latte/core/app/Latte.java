package com.toplyh.latte.core.app;

import android.content.Context;

import java.util.HashMap;
import java.util.WeakHashMap;

public final class Latte {

    /**
     * 开始初始化配置
     *
     * @param context
     * @return
     */
    public static Configurator init(Context context) {
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(), context.getApplicationContext());
        return Configurator.getInstance();
    }

    /**
     * 获取配置存储map
     *
     * @return
     */
    public static HashMap<String, Object> getConfigurations() {
        return Configurator.getInstance().getLatteConfigs();
    }


    public static Context getApplicationContext(){
        return (Context) getConfigurations().get(ConfigType.APPLICATION_CONTEXT.name());
    }
}
