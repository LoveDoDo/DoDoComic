package com.dodo.xinyue.core.ui.dialog.anim;

import android.animation.ObjectAnimator;
import android.view.View;

import com.flyco.animation.BaseAnimatorSet;

/**
 * ZoomInAnim
 *
 * @author DoDo
 * @date 2018/1/12
 */
public class ZoomInAnim extends BaseAnimatorSet {

    public ZoomInAnim() {
        duration = 50;
    }

    @Override
    public void setAnimation(View view) {
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1f),
                ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1f),
                ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        );
    }
}
