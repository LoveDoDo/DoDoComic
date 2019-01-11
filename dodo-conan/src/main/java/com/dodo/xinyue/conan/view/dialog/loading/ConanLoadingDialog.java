package com.dodo.xinyue.conan.view.dialog.loading;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.view.dialog.callback.ITimeout;
import com.dodo.xinyue.core.ui.dialog.base.BaseDialog;
import com.dodo.xinyue.core.ui.dialog.bean.DialogPublicParamsBean;
import com.dodo.xinyue.core.util.timer.BaseTimerTask;
import com.dodo.xinyue.core.util.timer.ITimerListener;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.Timer;

import butterknife.BindView;

/**
 * ConanLoadingDialog
 *
 * @author DoDo
 * @date 2018/10/28
 */
public class ConanLoadingDialog extends BaseDialog implements ITimerListener {

    private static final int TIME_OUT = 3;//超时时间

    private ObjectAnimator mRotationAnim = null;
    private final ITimeout mTimeout;

    private Timer mTimer = null;
    private int mCountDownMills = TIME_OUT;//倒计时秒数

    @BindView(R2.id.tvProgress)
    IconTextView mTvProgress = null;

    public ConanLoadingDialog(DialogPublicParamsBean bean,
                              ITimeout timeout) {
        super(bean);
        this.mTimeout = timeout;
    }

    public static ConanLoadingDialogBuilder builder() {
        return new ConanLoadingDialogBuilder();
    }

    @Override
    public Object setLayout() {
        return R.layout.dialog_loading;
    }

    @Override
    public void onBindView(View rootView) {

        mRotationAnim = ObjectAnimator.ofFloat(mTvProgress, "rotation", 0, 360);
        mRotationAnim.setDuration(1314);
        mRotationAnim.setRepeatCount(Animation.INFINITE);//无限循环
        mRotationAnim.setRepeatMode(ValueAnimator.RESTART);//重复动画
        mRotationAnim.setInterpolator(new LinearInterpolator());//覆盖默认的AccelerateDecelerateInterpolator()
        mRotationAnim.start();

        if (mTimeout != null) {
            initTimer();
        }

    }

    /**
     * 初始化定时器
     */
    private void initTimer() {
        mTimer = new Timer();
        final BaseTimerTask timerTask = new BaseTimerTask(this);
        /**
         * 参数二：0立即执行 >0延时xx毫秒后执行
         * 参数三：空=执行一次 非空=时钟周期，延时xx毫秒后再次执行
         */
        mTimer.schedule(timerTask, 0, 1000);
    }

    @Override
    public void onTimer() {
        mCountDownMills--;
        if (mCountDownMills < 0) {
            //取消定时器，结束倒计时
            stopTimer();
            cancel();
            mTimeout.onAutoDismiss();
        }
    }

    /**
     * 取消定时器，结束倒计时
     */
    private void stopTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (mRotationAnim != null) {
            mRotationAnim.cancel();
            mRotationAnim = null;
        }
        stopTimer();
        super.onDismiss(dialog);
    }
}
