package com.dodo.xinyue.conan.module.setting.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.module.BaseModuleDelegate;

/**
 * SaySomethingDelegate
 *
 * @author DoDo
 * @date 2019/1/14
 */
public class SaySomethingDelegate extends BaseModuleDelegate {

    public static SaySomethingDelegate create() {
        return new SaySomethingDelegate();
    }

    @Override
    public Object setChildLayout() {
        return R.layout.delegate_say_something;
    }

    @Override
    public String setTitle() {
        return "作者的话";
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        super.onBindView(savedInstanceState, rootView);
    }

}
