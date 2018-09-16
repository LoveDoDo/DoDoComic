package com.dodo.xinyue.core.delegates.web.client;

import android.graphics.Bitmap;
import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dodo.xinyue.core.app.DoDo;
import com.dodo.xinyue.core.delegates.web.BaseWebDelegate;
import com.dodo.xinyue.core.delegates.web.IPageLoadListener;
import com.dodo.xinyue.core.delegates.web.route.Router;

/**
 * WebViewClient默认实现类
 *
 * @author DoDo
 * @date 2017/10/29
 */
public class WebViewClientImpl extends WebViewClient {

    private final BaseWebDelegate DELEGATE;
    private IPageLoadListener mIPageLoadListener = null;
    private static final Handler HANDLER = DoDo.getHandler();

    public void setPageLoadListener(IPageLoadListener listener) {
        this.mIPageLoadListener = listener;
    }

    public WebViewClientImpl(BaseWebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    /**
     * 拦截浏览器的跳转
     *
     * 为了考虑到兼容性，用了过时的方法
     *
     * @param view
     * @param url
     * @return true=拦截
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return Router.getInstance().handleWebUrl(DELEGATE, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadStart();
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadEnd();
        }
    }
}
