package com.dodo.xinyue.core.delegates.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dodo.xinyue.core.delegates.web.chromeclient.WebChromeClientImpl;
import com.dodo.xinyue.core.delegates.web.client.WebViewClientImpl;
import com.dodo.xinyue.core.delegates.web.route.RouteKeys;
import com.dodo.xinyue.core.delegates.web.route.Router;

/**
 * WebDelegate 默认实现类
 *
 * @author DoDo
 * @date 2017/10/29
 */
public class WebDelegateImpl extends BaseWebDelegate {

    public static WebDelegateImpl create(String url) {
        final Bundle args = new Bundle();
        args.putString(RouteKeys.URL.name(), url);
        final WebDelegateImpl delegate = new WebDelegateImpl();
        delegate.setArguments(args);
        return delegate;
    }

    public static WebDelegateImpl create(String url, String data) {
        final Bundle args = new Bundle();
        args.putString(RouteKeys.URL.name(), url);
        args.putString(RouteKeys.DATA.name(), data);
        final WebDelegateImpl delegate = new WebDelegateImpl();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public Object setLayout() {
        return getWebView();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);

        //用原生的方式(delegate切换)模拟Web跳转并进行页面加载
        Router.getInstance().loadPage(this, getUrl(), getData());
    }


    @Override
    public IWebViewInitializer setInitializer() {
        return this;
    }

    @Override
    public WebView initWebView(WebView webView) {
        return new WebViewInitializer().initWebView(webView);
    }

    @Override
    public WebViewClient initWebViewClient() {
        final WebViewClientImpl client = new WebViewClientImpl(this);
        client.setPageLoadListener(getPageListener());
        return client;
    }

    @Override
    public WebChromeClient initWebChromeClient() {
        return new WebChromeClientImpl();
    }

    @Override
    public boolean isTrack() {
        return false;
    }
}
