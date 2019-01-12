package com.dodo.xinyue.conan.module.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.core.delegates.DoDoDelegate;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * AboutDelegate
 * 关于
 *
 * @author DoDo
 * @date 2019/1/12
 */
public class AboutDelegate extends DoDoDelegate {

    @BindView(R2.id.tvVersionName)
    TextView mTvVersionName = null;

    @OnClick(R2.id.rlFeedback)
    void onFeedbackClicked() {

    }

    @OnClick(R2.id.rlSaySomething)
    void onSaySomethingClicked() {

    }

    public static AboutDelegate create() {
        return new AboutDelegate();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_about;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mTvVersionName.setText("V" + AppUtils.getAppVersionName());
    }
}
