package com.dodo.xinyue.core.delegates;

import android.os.Bundle;

/**
 * 需要被其他delegate继承
 *
 * @author DoDo
 * @date 2017/8/30
 */
public abstract class DoDoDelegate extends PermissionCheckerDelegate {

    private boolean mAllowBack = false;//防止动画未结束就按返回键(目前因为缺少监听,所以只适用于delegate首次进栈)

    @SuppressWarnings("unchecked")
    public <T extends DoDoDelegate> T getParentDelegate() {
        return (T) getParentFragment();
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        mAllowBack = true;//响应返回键
        super.onEnterAnimationEnd(savedInstanceState);
    }

    @Override
    public boolean onBackPressedSupport() {
        if (!mAllowBack) {
            return true;//屏蔽返回键
        }
        return super.onBackPressedSupport();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //检测内存泄漏
//        final RefWatcher refWatcher = DoDo.getConfiguration(ConfigKeys.REF_WATCHER);
//        refWatcher.watch(this);
    }
}
