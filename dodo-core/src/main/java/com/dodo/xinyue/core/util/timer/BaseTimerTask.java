package com.dodo.xinyue.core.util.timer;

import java.util.TimerTask;

/**
 * 定时器任务 基类
 *
 * @author DoDo
 * @date 2017/9/3
 */
public class BaseTimerTask extends TimerTask {

    private ITimerListener mITimerListener = null;

    public BaseTimerTask(ITimerListener timerListener) {
        this.mITimerListener = timerListener;
    }

    @Override
    public void run() {
        if (mITimerListener != null) {
            mITimerListener.onTimer();
        }

    }
}
