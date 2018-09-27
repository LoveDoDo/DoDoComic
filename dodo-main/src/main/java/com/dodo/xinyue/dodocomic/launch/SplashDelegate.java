package com.dodo.xinyue.dodocomic.launch;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.dodo.xinyue.conan.IndexDelegate;
import com.dodo.xinyue.core.app.DoDo;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.util.timer.BaseTimerTask;
import com.dodo.xinyue.core.util.timer.ITimerListener;
import com.dodo.xinyue.dodocomic.R;

import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * SplashDelegate
 *
 * @author DoDo
 * @date 2018/9/24
 */
public class SplashDelegate extends DoDoDelegate implements ITimerListener {

    private Timer mTimer = null;
    private int mCountDownMills = 8;//倒计时秒数

    private int mNumColor = Color.parseColor("#ffffa200");

    @BindView(R.id.ivConan)
    AppCompatImageView mIvConan = null;
    @BindView(R.id.tvTimeSkip)
    TextView mTvTimeSkip = null;

    @OnClick(R.id.tvTimeSkip)
    void onTimeSkipClicked() {
        stopTimer();
        enterHome();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_splash;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);

        getSupportDelegate().getActivity().getWindow().setBackgroundDrawableResource(R.color.black);
//        getSupportDelegate().getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);

        YoYo.with(Techniques.FadeInRight)//渐显+从右至左移入
                .interpolate(new OvershootInterpolator())//超出边界弹回动画
                .duration(1000)
                .onEnd(animator -> initTimer())
                .playOn(mIvConan);
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


    /**
     * 倒计时
     */
    @Override
    public void onTimer() {
        DoDo.getHandler().post(() -> {
            //更新倒计时
            if (mTvTimeSkip != null) {
                final String str1 = "跳过( ";
                final String str2 = String.valueOf(mCountDownMills);
                final String str3 = " )";
                final SpannableStringBuilder builder = new SpannableStringBuilder(str1 + str2 + str3);
                builder.setSpan(new ForegroundColorSpan(mNumColor),
                        str1.length(), (str1 + str2).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                mTvTimeSkip.setText(builder);
                if (mTvTimeSkip.getVisibility() != View.VISIBLE) {
                    mTvTimeSkip.setVisibility(View.VISIBLE);
                }
                mCountDownMills--;
                if (mCountDownMills < 0) {
                    //取消定时器，结束倒计时
                    stopTimer();
                    enterHome();
                }
            }
        });
    }

    /**
     * 全部是设置当前Fragment的动画,和之前之后的Fragment无关
     * <p>
     * 参数解释：
     * enter: Fragment的进栈动画
     * exit: Fragment的出栈动画(pop时的)
     * popEnter: 下一个Fragment出栈时，该Fragment从hide状态变为show状态时的动画
     * popExit：下一个Fragment进栈时，该Fragment从show变为hide状态时的动画
     * <p>
     *     A -> B(当前Fragment) -> C
     * enter:    A -> B(进栈) B的进栈动画
     * exit:     A <- B(pop) B的出栈动画
     * popEnter: B <- C(pop) B的伪进栈动画(重回栈顶)
     * popExit： B -> C(进栈) B的伪出栈动画(不再是栈顶)
     */
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        FragmentAnimator fragmentAnimator = super.onCreateFragmentAnimator();
        fragmentAnimator.setEnter(R.anim.splash_enter);
        fragmentAnimator.setPopExit(R.anim.splash_pop_exit_2);
        return fragmentAnimator;
//        return new FragmentAnimator(
//                R.anim.splash_enter,//渐显动画
//                R.anim.splash_pop_exit,//从中间向左边退出效果
//                0,
//                0
//        );
    }

    /**
     * 进入首页
     */
    private void enterHome() {
        getSupportDelegate().startWithPop(IndexDelegate.create());
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

    /**
     * 屏蔽返回键
     */
    @Override
    public boolean onBackPressedSupport() {
        return true;
    }
}
