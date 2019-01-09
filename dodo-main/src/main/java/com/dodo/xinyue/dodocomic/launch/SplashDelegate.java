package com.dodo.xinyue.dodocomic.launch;

import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.dodo.xinyue.conan.main.ConanBottomDelegate;
import com.dodo.xinyue.core.app.DoDo;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.util.timer.BaseTimerTask;
import com.dodo.xinyue.core.util.timer.ITimerListener;
import com.dodo.xinyue.dodocomic.R;
import com.dodo.xinyue.dodocomic.launch.anim.SlideOutLeftSplashBackground;
import com.dodo.xinyue.dodocomic.launch.anim.SlideOutRightSplashBackground;
import com.dodo.xinyue.dodocomic.launch.anim.ZoomInSplashConan;

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
    @BindView(R.id.ivLeft)
    AppCompatImageView mIvLeft = null;
    @BindView(R.id.ivRight)
    AppCompatImageView mIvRight = null;

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
        //裁剪左半边
        ClipDrawable leftClip = (ClipDrawable) mIvLeft.getBackground();
        leftClip.setLevel((int) (10000 * 0.5));//0.5=一半
        //裁剪右半边
        ClipDrawable rightClip = (ClipDrawable) mIvRight.getBackground();
        rightClip.setLevel((int) (10000 * 0.5));//0.5=一半

    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
//        getSupportDelegate().popTo(A, true, () -> {
//            start(C)
//        });
        //设置窗口背景为黑色,为了首页的缩放动画
        getProxyActivity().removeWindowBackground();

        DoDo.getHandler().postDelayed(() ->
                YoYo.with(Techniques.FadeInRight)//渐显+从右至左移入
                        .interpolate(new OvershootInterpolator())//超出边界弹回动画
                        .duration(888)
                        .onEnd(animator -> initTimer())
                        .playOn(mIvConan), 300);//TODO 透明度为0的imageView不能在动画里延时,会闪现

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

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        FragmentAnimator fragmentAnimator = super.onCreateFragmentAnimator();
        fragmentAnimator.setEnter(R.anim.splash_enter);
        fragmentAnimator.setPopExit(R.anim.splash_pop_exit_4);
        return fragmentAnimator;
    }

    /**
     * 进入首页
     */
    private void enterHome() {
        YoYo.with(new ZoomInSplashConan())//放大
                .interpolate(new AnticipateInterpolator())//先回退一小步，然后再加速前进
                .duration(888)
                .onEnd(animator -> {
                    YoYo.with(new SlideOutLeftSplashBackground())//向左滑出
                            .interpolate(new AccelerateInterpolator())//先慢后快
                            .duration(1314)
                            .delay(100)//为了与进栈动画同步 防止卡顿
                            .playOn(mIvLeft);
                    YoYo.with(new SlideOutRightSplashBackground())//向右滑出
                            .interpolate(new AccelerateInterpolator())//先慢后快
                            .duration(1314)
                            .delay(100)//为了与进栈动画同步 防止卡顿
                            .playOn(mIvRight);
                    //打开IndexDelegate
                    getSupportDelegate().startWithPop(new ConanBottomDelegate());
                })
                .playOn(mIvConan);


    }

    /**
     * 取消定时器，结束倒计时
     */
    private void stopTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        mTvTimeSkip.setVisibility(View.GONE);
    }

    /**
     * 屏蔽返回键
     */
    @Override
    public boolean onBackPressedSupport() {
        return true;
    }
}
