package com.dodo.xinyue.core.delegates.bottom;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;

/**
 * 支持双击退出应用的delegate基类
 *
 * @author DoDo
 * @date 2018/10/1
 */
public abstract class BaseBottomDelegate extends BaseBottomContainerDelegate {

    //双击退出间隔时间 默认2秒
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
            ToastUtils.showShort("再按一次退出" + AppUtils.getAppName());
        }
        return true;//消费掉该事件，不再向后传递
    }

}
