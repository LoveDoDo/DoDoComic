package com.dodo.xinyue.dodocomic.launch.anim;

import android.animation.ObjectAnimator;
import android.view.View;

import com.daimajia.androidanimations.library.BaseViewAnimator;

/**
 * ZoomInSplashConan
 *
 * @author DoDo
 * @date 2018/10/1
 */
public class ZoomInSplashConan extends BaseViewAnimator {
    @Override
    protected void prepare(View target) {
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "alpha", 1, 0),
                ObjectAnimator.ofFloat(target, "scaleX", 1, 2),
                ObjectAnimator.ofFloat(target, "scaleY", 1, 2)
        );
    }
}
