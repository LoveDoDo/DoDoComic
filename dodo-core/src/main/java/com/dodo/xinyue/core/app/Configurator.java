package com.dodo.xinyue.core.app;

import android.app.Activity;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.Utils;
import com.dodo.xinyue.core.delegates.web.event.BaseEvent;
import com.dodo.xinyue.core.delegates.web.event.EventManager;
import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * 全局配置
 *
 * @author DoDo
 * @date 2017/8/28
 */
public final class Configurator {

    //初始化XX之前，先定义存储它的空间
    //static final 全局初始化时就会初始化好(存疑？？？) 引用（或者叫实例）不可以被修改，但指向的对象里的内容可以修改
    //代码规范：大写 下划线分割

    /**
     * 配置项存储空间
     */
    private static final HashMap<Object, Object> DODO_CONFIGS = new HashMap<>();
    private static final Handler HANDLER = new Handler();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    /**
     * 私有构造，只有在本类中可以实例化
     */
    private Configurator() {
        //配置开始
        DODO_CONFIGS.put(ConfigKeys.CONFIG_READY, false);
        DODO_CONFIGS.put(ConfigKeys.HANDLER, HANDLER);
    }

    /**
     * 线程安全的懒汉模式 1.静态内部类 2.getInstance
     */
    static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 静态内部类 单例模式的初始化
     */
    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    /**
     * 获取配置项集合
     */
    final HashMap<Object, Object> getDodoConfigs() {
        return DODO_CONFIGS;
    }

    /**
     * 完成配置
     * <p>
     * TODO：用【public final】修饰的方法，虚拟机会优化
     */
    public final void configure() {
        initOtherLib();
        //配置完成
        DODO_CONFIGS.put(ConfigKeys.CONFIG_READY, true);
        //初始化Utils 需要放在配置完成后,不然checkConfiguration()会出错
        Utils.init(DoDo.getAppContext());
    }

    /**
     * 初始化其他第三方库
     */
    private final void initOtherLib() {
        //初始化字体图标库
        initIcons();
        //初始化Logger
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    /**
     * 配置原生请求的域名
     */
    public final Configurator withNativeApiHost(String host) {
        DODO_CONFIGS.put(ConfigKeys.NATIVE_API_HOST, host);
        return this;
    }

    /**
     * 配置H5请求的域名
     */
    public final Configurator withWebApiHost(String host) {
        //只留下域名，否则无法同步cookie，不能带http://或末尾的/
        String hostName = host
                .replace("http://", "")
                .replace("https://", "");
        hostName = hostName.substring(0, hostName.lastIndexOf('/'));
        DODO_CONFIGS.put(ConfigKeys.WEB_API_HOST, hostName);
        return this;
    }

    /**
     * 初始化字体图标
     */
    private void initIcons() {
        final int iconSize = ICONS.size();
        if (iconSize <= 0) {
            return;
        }
        final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
        for (int i = 1; i < iconSize; i++) {
            initializer.with(ICONS.get(i));
        }
    }

    /**
     * 配置字体图标
     */
    public final Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    /**
     * 配置拦截器（单个）
     */
    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        DODO_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    /**
     * 配置拦截器（多个）
     */
    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors) {
        //TODO：合并两个集合，key相同则覆盖value
        INTERCEPTORS.addAll(interceptors);
        DODO_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    /**
     * 配置宿主Activity
     */
    public final Configurator withActivity(Activity activity) {
        DODO_CONFIGS.put(ConfigKeys.ACTIVITY, activity);
        return this;
    }

    /**
     * 配置加载延迟
     */
    public final Configurator withLoaderDelayed(long delayed) {
        DODO_CONFIGS.put(ConfigKeys.LOADER_DELAYED, delayed);
        return this;
    }

    /**
     * 配置JS接口(调用名)
     */
    public final Configurator withJavascriptInterface(@NonNull String name) {
        DODO_CONFIGS.put(ConfigKeys.JAVASCRIPT_INTERFACE, name);
        return this;
    }

    /**
     * 配置Web事件(WebView)
     */
    public final Configurator withWebEvent(@NonNull String name, @NonNull BaseEvent event) {
        final EventManager manager = EventManager.getInstance();
        manager.addEvent(name, event);
        return this;
    }

    /**
     * 配置内存泄漏检测
     */
    public final Configurator withRefWatcher(RefWatcher refWatcher) {
        DODO_CONFIGS.put(ConfigKeys.REF_WATCHER, refWatcher);
        return this;
    }

    /**
     * 检查配置是否完成
     */
    private void checkConfiguration() {
        //TODO 不会再修改的类变量和局部变量用final修饰,可以最大程度的避免修改一些本不该修改的变量，虚拟机也会进行优化
        final boolean isReady = (boolean) DODO_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready, please call configure method first");
        }
    }

    /**
     * 获取配置项
     */
    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = DODO_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) value;
    }

}