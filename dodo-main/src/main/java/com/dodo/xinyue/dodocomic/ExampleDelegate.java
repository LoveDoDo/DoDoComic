package com.dodo.xinyue.dodocomic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.util.log.DoDoLogger;

import butterknife.OnClick;

/**
 * ExampleDelegate
 *
 * @author DoDo
 * @date 2018/9/16
 */
public class ExampleDelegate extends DoDoDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    static ExampleDelegate create() {
        return new ExampleDelegate();
    }

    @OnClick(R.id.tvExample)
    void onExampleClicked() {
        ToastUtils.showLong("test");
        DoDoLogger.d("haha");
    }
}
