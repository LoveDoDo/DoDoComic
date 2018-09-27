package com.dodo.xinyue.conan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.util.toast.ToastUtil;

import butterknife.OnClick;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 主页
 *
 * @author DoDo
 * @date 2018/9/26
 */
public class IndexDelegate extends DoDoDelegate {

    @OnClick(R2.id.tvIndex)
    void onTvIndexClicked() {
        start(ConanDelegate.create());
    }

    public static IndexDelegate create() {
        return new IndexDelegate();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        FragmentAnimator fragmentAnimator = super.onCreateFragmentAnimator();
        fragmentAnimator.setEnter(R.anim.conan_index_enter);
        return fragmentAnimator;
    }

    //双击退出间隔时间
    private static final long WAIT_TIME = 2000L;
    //记录上次点击的时间
    private long TOUCH_TIME = 0;

    /**
     * 双击返回键退出应用
     */
    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            getProxyActivity().finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            ToastUtil.showLong("再按一次返回键将关闭程序");
        }
        return true;//消费掉该事件，不再向后传递
    }
}
