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
public class TextScaleFadeInAnim extends BaseViewAnimator {
    @Override
    protected void prepare(View target) {
        int distance = target.getTop() + target.getHeight();
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "alpha", 1, 0, 0),
                ObjectAnimator.ofFloat(target, "scaleX", 1, 0.3f, 0),
                ObjectAnimator.ofFloat(target, "scaleY", 1, 0.3f, 0)
        );
    }
}
