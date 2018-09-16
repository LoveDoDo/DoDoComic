package com.dodo.xinyue.core.delegates.web;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.WebView;

import com.dodo.xinyue.core.app.ConfigKeys;
import com.dodo.xinyue.core.app.DoDo;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.delegates.web.route.RouteKeys;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * WebDelegate 基类
 *
 * @author DoDo
 * @date 2017/10/29
 */
public abstract class BaseWebDelegate extends DoDoDelegate implements IWebViewInitializer {

    private WebView mWebView = null;
    //WebView属于内存敏感的，使用弱引用，避免内存泄漏
    //WebView在xml文件里初始化会造成内存泄漏，最安全的做法就是在代码里new出来
    private final ReferenceQueue<WebView> WEB_VIEW_QUEUE = new ReferenceQueue<>();
    private String mUrl = null;
    //WebView初始化完成标识
    private boolean mIsWebViewAvailable = false;
    private DoDoDelegate mTopDelegate = null;
    private IPageLoadListener mIPageLoadListener = null;

    public BaseWebDelegate() {
    }

    public abstract IWebViewInitializer setInitializer();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        mUrl = args.getString(RouteKeys.URL.name());
        //初始化
        initWebView();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

    }

    /**
     * 初始化WebView
     */
    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
            return;
        }

        final IWebViewInitializer initializer = setInitializer();
        if (initializer == null) {
            throw new NullPointerException("Initializer is null!");
        }

        final WeakReference<WebView> webViewWeakReference = new WeakReference<WebView>(new WebView(getContext()), WEB_VIEW_QUEUE);
        mWebView = webViewWeakReference.get();
        //初始化
        mWebView = initializer.initWebView(mWebView);
        mWebView.setWebViewClient(initializer.initWebViewClient());
        mWebView.setWebChromeClient(initializer.initWebChromeClient());
        //支持JS和原生进行交互
        final String name = DoDo.getConfiguration(ConfigKeys.JAVASCRIPT_INTERFACE);
        mWebView.addJavascriptInterface(WebInterface.create(this), name);
        mIsWebViewAvailable = true;
    }

    public void setTopDelegate(DoDoDelegate delegate) {
        mTopDelegate = delegate;
    }

    public DoDoDelegate getTopDelegate() {
        if (mTopDelegate == null) {
            mTopDelegate = this;
        }
        return mTopDelegate;
    }

    public WebView getWebView() {
        if (mWebView == null) {
            throw new NullPointerException("WebView IS NULL!");
        }
        return mIsWebViewAvailable ? mWebView : null;
    }

    public String getUrl() {
        if (TextUtils.isEmpty(mUrl)) {
            throw new NullPointerException("URL IS NULL!");
        }
        return mUrl;
    }

    public void setPageLoadListener(IPageLoadListener listener) {
        this.mIPageLoadListener = listener;
    }

    public IPageLoadListener getPageListener() {
        return mIPageLoadListener;
    }


    /**
     * 绑定WebView生命周期，下同
     */
    @Override
    public void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.onPause();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsWebViewAvailable = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }
}
