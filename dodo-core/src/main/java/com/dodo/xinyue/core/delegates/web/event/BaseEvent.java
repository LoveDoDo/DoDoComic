package com.dodo.xinyue.core.delegates.web.event;

import android.content.Context;
import android.webkit.WebView;

import com.dodo.xinyue.core.delegates.web.BaseWebDelegate;

/**
 * BaseEvent
 *
 * @author DoDo
 * @date 2017/10/30
 */
public abstract class BaseEvent implements IEvent {

    private Context mContext = null;
    private String mAction = null;
    private BaseWebDelegate mDelegate = null;
    private String mUrl = null;
    private WebView mWebView = null;

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public String getAction() {
        return mAction;
    }

    public void setAction(String action) {
        mAction = action;
    }

    public BaseWebDelegate getDelegate() {
        return mDelegate;
    }

    public void setDelegate(BaseWebDelegate delegate) {
        mDelegate = delegate;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public android.webkit.WebView getWebView() {
        return mWebView;
    }

    public void setWebView(android.webkit.WebView webView) {
        mWebView = webView;
    }
}
