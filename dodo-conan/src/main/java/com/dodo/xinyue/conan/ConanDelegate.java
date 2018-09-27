package com.dodo.xinyue.conan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dodo.xinyue.core.delegates.DoDoDelegate;

/**
 * ConanDelegate
 *
 * @author DoDo
 * @date 2018/9/16
 */
public class ConanDelegate extends DoDoDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_conan;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
//        tvConan.setText("ceshiyixia");
    }

    public static ConanDelegate create() {
        return new ConanDelegate();
    }

//    @BindView(R2.id.tvConan)
//    TextView tvConan = null;
//
//    @OnClick(R2.id.tvConan)
//    void onConanClicked() {
//        pop();
//    }


    @Override
    public boolean onBackPressedSupport() {
        return super.onBackPressedSupport();
    }
}
