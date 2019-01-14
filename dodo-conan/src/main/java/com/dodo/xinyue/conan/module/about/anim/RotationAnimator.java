package com.dodo.xinyue.conan.module.about.anim;

import android.animation.ObjectAnimator;
import android.view.View;

import com.daimajia.androidanimations.library.BaseViewAnimator;

/**
 * RotationAnimator
 *
 * @author DoDo
 * @date 2019/1/14
 */
public class RotationAnimator extends BaseViewAnimator {

    @Override
    protected void prepare(View target) {
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "rotation", 0, 360)
        );
    }
}
