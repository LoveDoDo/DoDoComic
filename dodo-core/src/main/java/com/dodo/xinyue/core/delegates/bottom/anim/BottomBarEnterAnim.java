package com.dodo.xinyue.core.delegates.bottom.anim;

import android.animation.ObjectAnimator;
import android.view.View;

import com.daimajia.androidanimations.library.BaseViewAnimator;

/**
 * SlideOutLeftSplashBackground
 *
 * @author DoDo
 * @date 2018/10/1
 */
public class BottomBarEnterAnim extends BaseViewAnimator {
    @Override
    protected void prepare(View target) {
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "translationY", target.getHeight(), 0)
        );
    }
}
