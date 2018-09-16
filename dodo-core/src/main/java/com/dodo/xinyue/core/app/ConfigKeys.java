package com.dodo.xinyue.core.app;

/**
 * 全局配置key
 *
 * 枚举类在整个应用程序中是唯一的单例，并且只会初始化一次。
 * 适合在多线程操作中进行安全的惰性单例的初始化（线程安全的懒汉模式）
 *
 * @author DoDo
 * @date 2017/8/28
 */
@SuppressWarnings("unused")
public enum ConfigKeys {
    //原生请求的域名
    NATIVE_API_HOST,
    //H5请求的域名
    WEB_API_HOST,
    //全局的上下文
    APPLICATION_CONTEXT,
    //全局配置完成(标记)
    CONFIG_READY,
    //字体
    ICON,
    //加载框消失时间(延时)
    LOADER_DELAYED,
    //拦截器
    INTERCEPTOR,
    //宿主Activity
    ACTIVITY,
    //全局Handler
    HANDLER,
    //JS接口
    JAVASCRIPT_INTERFACE,
    //RefWatcher 检测fragment的内存泄漏
    REF_WATCHER,
    //微信AppID
    WE_CHAT_APP_ID,
    //微信AppSecret
    WE_CHAT_APP_SECRET
}
