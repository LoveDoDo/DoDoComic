package com.dodo.xinyue.conan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dodo.xinyue.conan.view.DoDoFrameLayout;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.util.toast.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 主页
 *
 * @author DoDo
 * @date 2018/9/26
 */
public class IndexDelegate extends DoDoDelegate {

    @BindView(R2.id.dodo_container)
    DoDoFrameLayout mDoDoContainer = null;

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
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        mDoDoContainer.setInterceptTouchEvent(false);
//        ToastUtils.showLong("动画结束");

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
    private long mLastTouchTime = 0;

    /**
     * 双击返回键退出应用
     */
    @Override
    public boolean onBackPressedSupport() {
        if (super.onBackPressedSupport()) {
            return true;//防止动画未结束就按返回键
        }
        if (System.currentTimeMillis() - mLastTouchTime < WAIT_TIME) {
            getProxyActivity().finish();
        } else {
            mLastTouchTime = System.currentTimeMillis();
            ToastUtil.showLong("再按一次返回键将关闭程序");
        }
        return true;//消费掉该事件，不再向后传递
    }
}
