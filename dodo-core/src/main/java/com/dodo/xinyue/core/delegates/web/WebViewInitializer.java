package com.dodo.xinyue.core.delegates.web;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * 包装WebView,使其更像原生
 *
 * @author DoDo
 * @date 2017/10/29
 */
public class WebViewInitializer {

    @SuppressLint("SetJavaScriptEnabled")
    public WebView initWebView(WebView webView) {

        //允许调试
        WebView.setWebContentsDebuggingEnabled(true);
        //不能横向滚动
        webView.setHorizontalScrollBarEnabled(false);
        //不能纵向滚动
        webView.setVerticalScrollBarEnabled(false);
        //允许截图
        webView.setDrawingCacheEnabled(true);
        //屏蔽长按事件
        webView.setOnLongClickListener(v -> true);
        //屏蔽过度拖动时的上下阴影
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        //初始化WebSettings
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);//JS互调必须设置的
//        final String ua = settings.getUserAgentString();
//        settings.setUserAgentString(ua + " dodo");
//        settings.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
        //隐藏缩放控件
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        //禁止缩放
        settings.setSupportZoom(false);
        //文件权限
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowContentAccess(true);
        //缓存相关
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
//
////        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//禁止硬件加速，解决闪屏问题
//
//        settings.setUseWideViewPort(true);//设置webview自适应屏幕大小
//
//        settings.setBlockNetworkImage(true);

        return webView;
    }

}
