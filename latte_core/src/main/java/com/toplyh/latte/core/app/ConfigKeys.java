package com.toplyh.latte.core.app;

/**
 * 枚举类
 * 它是唯一的单例，全局只有唯一的一个
 * 只有在使用它的时候才会被初始化
 * 也就是线程安全的懒汉模式
 */
public enum ConfigKeys {
    API_HOST,
    APPLICATION_CONTEXT,
    CONFIG_READY,
    ICON,
    LOADED_DELAYED,
    INTERCEPTOR,
    WE_CHAT_APP_ID,
    WE_CHAT_APP_SECRET,
    ACTIVITY,
    JAVASCRIPT_INTERFACE
}
