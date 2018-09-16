package com.dodo.xinyue.core.delegates.web.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.webkit.URLUtil;
import android.webkit.WebView;

import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.delegates.web.BaseWebDelegate;
import com.dodo.xinyue.core.delegates.web.WebDelegateImpl;

/**
 * Router
 *
 * @author DoDo
 * @date 2017/10/29
 */
public class Router {

    private Router() {
    }

    private static class Holder {
        private static final Router INSTANCE = new Router();
    }

    public static Router getInstance() {
        return Holder.INSTANCE;
    }

    public final boolean handleWebUrl(BaseWebDelegate delegate, String url) {
        if (url.contains("tel:")) {
            callPhone(delegate.getContext(), url);
            return true;
        }

        final DoDoDelegate topDelegate = delegate.getTopDelegate();

        final WebDelegateImpl webDelegate = WebDelegateImpl.create(url);
        webDelegate.setPageLoadListener(delegate.getPageListener());
        topDelegate.start(webDelegate);
//        topDelegate.getFragmentManager().executePendingTransactions();
        return true;//接管WebView的跳转
    }

    /**
     * 加载远程页面
     *
     * @param webView
     * @param url
     */
    private void loadWebPage(WebView webView, String url) {
        if (webView == null) {
            throw new NullPointerException("WebView is null!");
        }
        webView.loadUrl(url);
    }

    /**
     * 加载本地页面
     *
     * @param webView
     * @param url
     */
    private void loadLocalPage(WebView webView, String url) {
        loadWebPage(webView, "file:///android_asset/" + url);
    }

    private void loadPage(WebView webView, String url) {
        if (URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)) {
            loadWebPage(webView, url);
        } else {
            loadLocalPage(webView, url);
        }
    }

    public final void loadPage(BaseWebDelegate delegate, String url) {
        loadPage(delegate.getWebView(), url);
    }

    private void callPhone(Context context, String url) {
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        final Uri data = Uri.parse(url);
        intent.setData(data);
        ContextCompat.startActivity(context, intent, null);
    }

}
