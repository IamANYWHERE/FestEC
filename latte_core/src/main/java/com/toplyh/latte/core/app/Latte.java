package com.toplyh.latte.core.app;

import android.content.Context;

import java.util.HashMap;

public final class Latte {

    /**
     * 开始初始化配置
     *
     * @param context
     * @return
     */
    public static Configurator init(Context context) {
        getConfigurations().put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    /**
     * 获取配置存储map
     *
     * @return
     */
    public static HashMap<Object, Object> getConfigurations() {
        return Configurator.getInstance().getLatteConfigs();
    }

    public static<T> T getConfiguration(Enum<ConfigKeys> key){
        return (T)Configurator.getInstance().getConfiguration(key);
    }

    public static Configurator getConfigurator(){
        return Configurator.getInstance();
    }


    public static Context getApplicationContext(){
        return (Context) getConfigurations().get(ConfigKeys.APPLICATION_CONTEXT);
    }
}
