package com.dodo.xinyue.conan.module.setting.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.module.BaseModuleDelegate;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * AboutDelegate
 * 关于
 *
 * @author DoDo
 * @date 2019/1/12
 */
public class AboutDelegate extends BaseModuleDelegate {

    @BindView(R2.id.tvVersionName)
    TextView mTvVersionName = null;

    @OnClick(R2.id.rlFeedback)
    void onFeedbackClicked() {
        //意见反馈
        start(FeedbackDelegate.create());
    }

    @OnClick(R2.id.rlSaySomething)
    void onSaySomethingClicked() {
        //作者的话
        start(SaySomethingDelegate.create());
    }

    public static AboutDelegate create() {
        return new AboutDelegate();
    }

    @Override
    public Object setChildLayout() {
        return R.layout.delegate_about;
    }

    @Override
    public String setTitle() {
        return "关于" + AppUtils.getAppName();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        super.onBindView(savedInstanceState, rootView);
        mTvVersionName.setText("V" + AppUtils.getAppVersionName());
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        FragmentAnimator fragmentAnimator = super.onCreateFragmentAnimator();
        fragmentAnimator.setEnter(R.anim.global_enter);
        fragmentAnimator.setExit(R.anim.global_exit);
        fragmentAnimator.setPopEnter(R.anim.global_no_anim_300);
        fragmentAnimator.setPopExit(R.anim.global_no_anim_258);
        return fragmentAnimator;
    }

}
