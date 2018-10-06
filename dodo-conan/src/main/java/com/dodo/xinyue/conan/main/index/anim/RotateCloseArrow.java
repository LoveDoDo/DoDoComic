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
public class RotateCloseArrow extends BaseViewAnimator {
    @Override
    protected void prepare(View target) {
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "rotation", 180, 360)
        );
    }
}
