package com.dodo.xinyue.core.delegates.web.chromeclient;

import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * WebChromeClient默认实现类
 *
 * @author DoDo
 * @date 2017/10/29
 */
public class WebChromeClientImpl extends WebChromeClient {

    /**
     * 拦截对话框，可以弹出自己的对话框
     *
     * @param view
     * @param url
     * @param message
     * @param result
     * @return
     */
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }
}
