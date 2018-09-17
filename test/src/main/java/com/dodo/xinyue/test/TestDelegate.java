package com.dodo.xinyue.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dodo.xinyue.core.delegates.DoDoDelegate;

/**
 * TestDelegate
 *
 * @author DoDo
 * @date 2018/9/17
 */
public class TestDelegate extends DoDoDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_test;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    public static TestDelegate create() {
        return new TestDelegate();
    }

}
