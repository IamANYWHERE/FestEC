package com.toplyh.latte.core.global;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.blankj.utilcode.util.Utils;
import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.toplyh.latte.core.fragments.web.event.Event;
import com.toplyh.latte.core.fragments.web.event.EventManager;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * 全局配置类
 * 用于存储和管理所有的配置
 */
public final class Configurator {

    //保存全局配置信息
    private static final HashMap<Object, Object> LATTE_CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private Configurator() {
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY, false);
    }

    @SuppressWarnings("WeakerAccess")
    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    @SuppressWarnings("WeakerAccess")
    public final HashMap<Object, Object> getLatteConfigs() {
        return LATTE_CONFIGS;
    }

    //用静态内部类实现懒汉式单例
    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    /**
     * 确认最终配置完成
     */
    public final void configure() {
        initIcons();
        Logger.addLogAdapter(new AndroidLogAdapter());
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY, true);
        Utils.init(Latte.getApplicationContext());
    }

    /**
     * 配置host地址
     *
     * @param host
     * @return
     */
    public final Configurator withNativeApiHost(String host) {
        LATTE_CONFIGS.put(ConfigKeys.NATIVE_API_HOST, host);
        return this;
    }

    /**
     * 纯原生app没有Web时，可以只要Native HOST
     * @param
     * @return
     */
    public final Configurator withWebApiHost(String host){
        //只留下域名，否则无法同步cookie,不能带http://或末尾的/
        String hostName  = host
                .replace("http://","")
                .replace("https://","");
        hostName = hostName.substring(0,hostName.lastIndexOf('/'));
        LATTE_CONFIGS.put(ConfigKeys.WEB_API_HOST, host);
        return this;
    }

    private void initIcons() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    public final Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }


    public final Configurator withInterceptor(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withAppId(String appId) {
        LATTE_CONFIGS.put(ConfigKeys.WE_CHAT_APP_ID, appId);
        return this;
    }

    public final Configurator withAppSecret(String appSecret) {
        LATTE_CONFIGS.put(ConfigKeys.WE_CHAT_APP_SECRET, appSecret);
        return this;
    }

    public final Configurator withActivity(Activity activity) {
        LATTE_CONFIGS.put(ConfigKeys.ACTIVITY, activity);
        return this;
    }

    public final Configurator withJavascriptInterface(@NonNull String name) {
        LATTE_CONFIGS.put(ConfigKeys.JAVASCRIPT_INTERFACE, name);
        return this;
    }

    public final Configurator withWebEvent(@NonNull String name, @NonNull Event event) {
        final EventManager manager = EventManager.getInstance();
        manager.addEvent(name, event);
        return this;
    }

    public final Configurator withOSSEndPoint(String endPoint){
        LATTE_CONFIGS.put(ConfigKeys.OSS_ENDPOINT,endPoint);
        return this;
    }

    public final Configurator withStsServer(String server){
        LATTE_CONFIGS.put(ConfigKeys.STS_SERVER,server);
        return this;
    }

    public final Configurator withBucketName(String bucketName){
        LATTE_CONFIGS.put(ConfigKeys.BUCKET_NAME,bucketName);
        return this;
    }

    public final Configurator withUploadDir(String dirName){
        LATTE_CONFIGS.put(ConfigKeys.OSS_UPLOAD_DIR,dirName);
        return this;
    }

    public final Configurator withOSSClient(int connectionTimeout,int socketTimeout,int maxConcurrentRequest,int maxErrorRetry){
        final String endPoint = (String) LATTE_CONFIGS.get(ConfigKeys.OSS_ENDPOINT);
        final String stsServer = (String) LATTE_CONFIGS.get(ConfigKeys.STS_SERVER);

        final OSSCredentialProvider credentialProvider = new OSSAuthCredentialsProvider(stsServer);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(connectionTimeout);
        conf.setSocketTimeout(socketTimeout);
        conf.setMaxConcurrentRequest(maxConcurrentRequest);
        conf.setMaxErrorRetry(maxErrorRetry);
        final OSS oss = new OSSClient((Context) LATTE_CONFIGS.get(ConfigKeys.APPLICATION_CONTEXT),endPoint,credentialProvider);
        LATTE_CONFIGS.put(ConfigKeys.OSS_CLIENT,oss);
        return this;
    }

    /**
     * 检测配置是否完成
     */
    private void checkConfiguration() {
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    /**
     * 获取配置信息
     *
     * @param key 配置key
     * @param <T> 配置信息对象的类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public final <T> T getConfiguration(Enum<ConfigKeys> key) {
        checkConfiguration();
        return (T) LATTE_CONFIGS.get(key);
    }
}
