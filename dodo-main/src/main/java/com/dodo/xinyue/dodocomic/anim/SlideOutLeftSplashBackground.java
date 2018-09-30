package com.dodo.xinyue.dodocomic.anim;

import android.animation.ObjectAnimator;
import android.view.View;

import com.daimajia.androidanimations.library.BaseViewAnimator;

/**
 * SlideOutLeftSplashBackground
 *
 * @author DoDo
 * @date 2018/10/1
 */
public class SlideOutLeftSplashBackground extends BaseViewAnimator {
    @Override
    protected void prepare(View target) {
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "translationX", 0, -target.getRight()*3/4*3)
        );
    }
}
