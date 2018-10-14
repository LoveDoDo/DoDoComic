package com.dodo.xinyue.dodocomic.launch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dodo.xinyue.core.app.DoDo;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.dodocomic.R;

import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * BeforeLaunchDelegate
 *
 * @author DoDo
 * @date 2018/9/24
 */
public class BeforeLaunchDelegate extends DoDoDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_before_launch;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        //延时2秒启动SplashDelegate
        DoDo.getHandler().postDelayed(() -> {
            getSupportDelegate().startWithPop(new SplashDelegate());
        }, 1314);
    }

    /**
     * 屏蔽动画
     */
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        FragmentAnimator fragmentAnimator = super.onCreateFragmentAnimator();
        fragmentAnimator.setEnter(R.anim.before_launch_enter);
        fragmentAnimator.setPopExit(R.anim.before_launch_pop_exit);
        return fragmentAnimator;
    }

    /**
     * 屏蔽返回键
     */
    @Override
    public boolean onBackPressedSupport() {
        return true;
    }

}
