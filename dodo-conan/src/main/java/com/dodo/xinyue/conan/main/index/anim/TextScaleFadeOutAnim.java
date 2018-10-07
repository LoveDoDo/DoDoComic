package com.dodo.xinyue.conan.main.index.anim;

import android.animation.ObjectAnimator;
import android.view.View;

import com.daimajia.androidanimations.library.BaseViewAnimator;

/**
 * ZoomInSplashConan
 *
 * @author DoDo
 * @date 2018/10/1
 */
public class TextScaleFadeOutAnim extends BaseViewAnimator {
    @Override
    protected void prepare(View target) {
        int distance = target.getTop() + target.getHeight();
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "scaleX", 0.45f, 1),
                ObjectAnimator.ofFloat(target, "scaleY", 0.45f, 1),
                ObjectAnimator.ofFloat(target, "alpha", 0, 1)
        );
    }
}
