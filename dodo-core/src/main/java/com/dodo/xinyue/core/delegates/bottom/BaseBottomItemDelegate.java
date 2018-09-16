package com.dodo.xinyue.core.delegates.bottom;

import com.dodo.xinyue.core.delegates.DoDoDelegate;

/**
 * 底部item 基类
 *
 * @author DoDo
 * @date 2017/9/7
 */
public abstract class BaseBottomItemDelegate extends DoDoDelegate {

//    //双击退出间隔时间
//    private static final long WAIT_TIME = 2000L;
//    //记录上次点击的时间
//    private long TOUCH_TIME = 0;
//
//    /**
//     * 双击返回键退出应用
//     *
//     * @return
//     */
//    @Override
//    public boolean onBackPressedSupport() {
//        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
//            getProxyActivity().finish();
//        } else {
//            TOUCH_TIME = System.currentTimeMillis();
//            ToastUtil.showLong("再按一次返回键将关闭程序");
//        }
//        return true;//消费掉该事件，不再向后传递
//    }


//    @Override
//    public FragmentAnimator onCreateFragmentAnimator() {
//        return new DefaultNoAnimator();//设置无动画在出栈的时候会出现空白
//    }
}
