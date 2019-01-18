package com.dodo.xinyue.conan.module;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.core.delegates.DoDoDelegate;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * BaseModuleDelegate
 * 二级页面的基类
 *
 * @author DoDo
 * @date 2019/1/18
 */
public abstract class BaseModuleDelegate extends DoDoDelegate {

    @BindView(R2.id.tvTitle)
    TextView mTvTitle = null;

    @OnClick(R2.id.tvBack)
    void onTvBackClicked() {
        pop();
    }

    public abstract Object setChildLayout();

    public abstract String setTitle();

    @Override
    public Object setLayout() {
        return R.layout.layout_base_module;
    }

    @Override
    public void onPreBindView(@NonNull LayoutInflater inflater, View rootView) {
        ContentFrameLayout container = rootView.findViewById(R.id.container);
        final View childView;
        if (setChildLayout() instanceof Integer) {
            inflater.inflate((Integer) setChildLayout(), container, true);
        } else if (setChildLayout() instanceof View) {
            childView = (View) setChildLayout();
            container.addView(childView, childView.getLayoutParams());
        } else {
            throw new ClassCastException("type of setChildLayout() must be int or View!");
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mTvTitle.setText(setTitle());
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
