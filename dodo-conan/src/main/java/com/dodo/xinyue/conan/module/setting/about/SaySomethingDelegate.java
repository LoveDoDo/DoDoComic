package com.dodo.xinyue.conan.module.setting.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.core.delegates.DoDoDelegate;

import butterknife.OnClick;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * SaySomethingDelegate
 *
 * @author DoDo
 * @date 2019/1/14
 */
public class SaySomethingDelegate extends DoDoDelegate {

    @OnClick(R2.id.tvBack)
    void onTvBackClicked() {
        pop();
    }

    public static SaySomethingDelegate create() {
        return new SaySomethingDelegate();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_say_something;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        FragmentAnimator fragmentAnimator = super.onCreateFragmentAnimator();
        fragmentAnimator.setEnter(R.anim.global_enter);
        fragmentAnimator.setExit(R.anim.global_exit);
        return fragmentAnimator;
    }

}
