package com.dodo.xinyue.core.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * 对外的工具类 全部静态方法直接调用
 *
 * @author DoDo
 * @date 2017/8/28
 */
public final class DoDo {

    /**
     * 初始化-start(放在Application的onCreate()方法里)
     */
    public static Configurator init(Context context) {
        Configurator.getInstance()
                .getDodoConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    /**
     * 获取全局配置单例
     */
    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    /**
     * 获取全局配置项
     */
    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    /**
     * 获取全局上下文
     */
    public static Application getAppContext() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }

    /**
     * 获取全局Handler
     */
    public static Handler getHandler() {
        return getConfiguration(ConfigKeys.HANDLER);
    }

    /**
     * 获取全局唯一Activity
     */
    public static Activity getActivity() {
        return getConfiguration(ConfigKeys.ACTIVITY);
    }

}
