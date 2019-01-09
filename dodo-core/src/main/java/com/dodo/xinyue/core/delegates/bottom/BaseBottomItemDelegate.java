package com.dodo.xinyue.core.delegates.bottom;

import android.os.Bundle;

import com.dodo.xinyue.core.delegates.DoDoDelegate;

import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 底部item 基类
 *
 * @author DoDo
 * @date 2017/9/7
 */
public abstract class BaseBottomItemDelegate extends DoDoDelegate {

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultNoAnimator();//设置无动画在出栈的时候会出现空白
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
    }
}
