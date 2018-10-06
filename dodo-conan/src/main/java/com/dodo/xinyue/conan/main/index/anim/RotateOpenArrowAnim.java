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
public class RotateOpenArrowAnim extends BaseViewAnimator {
    @Override
    protected void prepare(View target) {
        int distance = target.getTop() + target.getHeight();
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "alpha", 1f, 0f, 0f, 1f),
                ObjectAnimator.ofFloat(target, "translationY", 0, distance * 0.8f, distance * 0.8f, 0),
                ObjectAnimator.ofFloat(target, "rotation", 0, 0, 180, 180)
//                ObjectAnimator.ofFloat(target, "rotation", 0, 180)
        );
    }
}
