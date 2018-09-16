package com.dodo.xinyue.core.ui.dialog.anim;

import android.animation.ObjectAnimator;
import android.view.View;

import com.flyco.animation.BaseAnimatorSet;

/**
 * ZoomOutAnim
 *
 * @author DoDo
 * @date 2018/1/12
 */
public class ZoomOutAnim extends BaseAnimatorSet {

    public ZoomOutAnim() {
        duration = 50;
    }

    @Override
    public void setAnimation(View view) {
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.5f),
                ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.5f),
                ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
        );
    }
}
