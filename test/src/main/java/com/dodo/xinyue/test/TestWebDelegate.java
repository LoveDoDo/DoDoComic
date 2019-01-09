package com.dodo.xinyue.test;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dodo.xinyue.core.app.DoDo;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.delegates.web.WebDelegateImpl;

import butterknife.OnClick;

/**
 * TestWebDelegate
 *
 * @author DoDo
 * @date 2018/10/23
 */
public class TestWebDelegate extends DoDoDelegate {

    private WebDelegateImpl mWebDelegate = null;

    @OnClick(R2.id.tvTest)
    void onTvTestClicked() {
//        final WebView webView = mWebDelegate.getWebView();
//        webView.loadUrl("javascript:function setTop(){document.querySelector('.playerhold').style.display=\"none\";}setTop();");

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("youku://play?vid=XMzgwNjkxODQ4NA=="));
        DoDo.getActivity().startActivity(intent);

    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_web;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        mWebDelegate = WebDelegateImpl.create("http://v.youku.com/v_show/id_XMzczNzAwNDE3Ng==.html");
        mWebDelegate.setTopDelegate(this);
        getSupportDelegate().loadRootFragment(R.id.flContainer, mWebDelegate);
    }
}
