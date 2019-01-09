package com.dodo.xinyue.dodocomic.launch.anim;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.androidanimations.library.BaseViewAnimator;

/**
 * SlideOutLeftSplashBackground
 *
 * @author DoDo
 * @date 2018/10/1
 */
public class SlideOutRightSplashBackground extends BaseViewAnimator {
    @Override
    public void prepare(View target) {
        ViewGroup parent = (ViewGroup) target.getParent();
        int distance = parent.getWidth() - target.getLeft();
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "translationX", 0, distance*3/4*3)
        );
    }
}
