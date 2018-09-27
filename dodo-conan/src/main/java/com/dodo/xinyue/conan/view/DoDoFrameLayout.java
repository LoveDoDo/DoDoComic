package com.dodo.xinyue.conan.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * 动画进行过程中,屏蔽当前Fragment的点击事件(未完成)
 * 条件：当前Fragment位于栈顶,且进/出场动画已执行完毕,页面才可点击
 *
 * @author DoDo
 * @date 2018/9/28
 */
public class DoDoFrameLayout extends FrameLayout {

    private boolean mInterceptTouchEvent = true;

    public DoDoFrameLayout(@NonNull Context context) {
        super(context);
    }

    public DoDoFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DoDoFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DoDoFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (super.onInterceptTouchEvent(ev)) {
            return true;
        }
        return mInterceptTouchEvent;
    }

    public boolean isInterceptTouchEvent() {
        return mInterceptTouchEvent;
    }

    public void setInterceptTouchEvent(boolean interceptTouchEvent) {
        this.mInterceptTouchEvent = interceptTouchEvent;
    }
}
