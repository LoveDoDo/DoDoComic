package com.dodo.xinyue.core.delegates.web;

import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.dodo.xinyue.core.delegates.web.event.BaseEvent;
import com.dodo.xinyue.core.delegates.web.event.EventManager;

/**
 * JS和原生交互
 *
 * @author DoDo
 * @date 2017/10/29
 */
public final class WebInterface {

    private final BaseWebDelegate DELEGATE;

    public WebInterface(BaseWebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    /**
     * 简单工厂模式
     *
     * @param delegate
     * @return
     */
    static WebInterface create(BaseWebDelegate delegate) {
        return new WebInterface(delegate);
    }

    /**
     * JS调用的方法
     *
     * android4.4以后需要加上注解，否则会被认为是不安全的
     *
     * @param params
     * @return
     */
    @JavascriptInterface
    public String event(String params) {
        final String action = JSON.parseObject(params).getString("action");
        final BaseEvent event = EventManager.getInstance().getEvent(action);
//        DoDoLogger.d("WEB_EVENT", params);
        if (event != null) {
            event.setAction(action);
            event.setDelegate(DELEGATE);
            event.setContext(DELEGATE.getContext());
            event.setUrl(DELEGATE.getUrl());
            return event.execute(params);
        }
        return null;
    }
}
