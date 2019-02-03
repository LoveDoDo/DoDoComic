package com.dodo.xinyue.core.delegates.web.client;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
    private boolean isLoadError = false;//是否加载失败(如断网,404等错误)

    public void setPageLoadListener(IPageLoadListener listener) {
        this.mIPageLoadListener = listener;
    }

    public WebViewClientImpl(BaseWebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    /**
     * 拦截浏览器的跳转
     * <p>
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

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
//        if (url.contains("pl-ali.youku.com/playlist")) {
////            Log.e("webhahaha", url);
//        } else {
////            Log.e("webeee", url);
//        }
//
//        if (
//                url.contains("baiduyoukualiyun.com")
//                        || url.contains("atm.youku.com")
//                        || (url.contains("cibntv.net/youku/") && !url.contains("vku.youku.com") && url.contains(".mp4"))
//                        || (url.contains("hudong.alicdn.com/api/data/") && url.contains("player.youku.com"))
//                        || url.contains("hudong.pl.youku.com/interact/player/get/plugins?")
//                        || url.contains("hudong.pl.youku.com/interact/web/get/timeLinePlugin?")
//                        || url.contains("hudong.pl.youku.com/jsapi/interact/playerPlugins.js")
//                        || url.contains("hudong.pl.youku.com/n/interact/player/get/plugins?")
//                        || (url.contains("k.youku.com/player/getFlvPath/sid/") && url.contains("&vl="))
//                        || url.contains("youku.com/cms/tool/pub/get_putdata.")
//                        || url.contains("youku.com/dress/")
//                        || (url.contains("youku.com/QVideo/") && url.contains("ajax/getDefaultItem?"))
//                        || (url.contains("youku.com") && url.contains("lotteryToolbar"))
//                        || (url.contains("youku.com") && url.contains("playlistIkuAD"))
//                        || (url.contains("youku.com") && url.contains("relationvideo_async"))
//                        || (url.contains("youku.com") && url.contains("seebuyGuide"))
//                        || (url.contains("youku.com") && url.contains("seebuyhome"))
//                        || (url.contains("youku.com") && url.contains("u-ads.adap.tv"))
//                        || (url.contains("youku.com") && url.contains("a.ads.cn.miaozhen.com"))
//                        || url.contains("atm.youku.com/cast/getFlvUrl.")
//                        || (url.contains("static.atm.youku.com") && url.contains(".swf"))
//                        || url.contains("valf.atm.youku.com/vf?site=1&rst=mp4&")
//                        || (url.contains("youku.com") && url.contains("adshow"))
//                        || (url.contains("youku.com") && url.contains("showAd"))
//                        || (url.contains("youku.com") && url.contains("mmstat.com/eg.js"))
//                        || url.contains("youku.com/cms/player/userinfo/")
//                        || url.contains("youku.com/cms/playlog/get?")
//                        || (url.contains(".mp4?ccode=") && url.contains("&duration=30&"))
//                        || (url.contains(".mp4?ccode=") && url.contains("&duration=29&"))
//                        || (url.contains("atm.youku.com") && !url.contains("v.youku.com"))
//                        || url.contains("youku.com/cms/player/userinfo/")
//                ) {
//            return new WebResourceResponse(null, null, null);//含有广告资源屏蔽请求
//        }

//        if (
//                (url.contains(".mp4?ccode=") && url.contains("&duration=30&"))
//                        || (url.contains(".mp4?ccode=") && url.contains("&duration=29&"))
//                        || (url.contains("atm.youku.com") && !url.contains("v.youku.com"))
//                        || url.contains("youku.com/cms/player/userinfo/")
//                ) {
//            return new WebResourceResponse(null, null, null);//屏蔽广告
//        }


        return super.shouldInterceptRequest(view, url);//正常加载
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        isLoadError = false;
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadStart();
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadEnd(isLoadError);
        }
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        if (request.isForMainFrame()) {//或者： if(request.getUrl().toString() .equals(getUrl()))
            isLoadError = true;
        }

    }
}
