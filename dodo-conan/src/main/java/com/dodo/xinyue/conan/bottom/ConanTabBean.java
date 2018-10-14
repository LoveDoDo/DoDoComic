package com.dodo.xinyue.conan.bottom;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.annotation.RawRes;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.core.delegates.bottom.bean.BaseBottomTabBean;
import com.eftimoff.androipathview.PathView;
import com.joanzapata.iconify.widget.IconTextView;

/**
 * ConanTabBean
 *
 * @author DoDo
 * @date 2018/10/2
 */
public class ConanTabBean extends BaseBottomTabBean {

    private final CharSequence ICON;
    private final CharSequence TITLE;
    private IconTextView mTvIcon = null;
    private AppCompatTextView mTvTitle = null;

    private PathView mPathView = null;
    private final int SVG;

    private AnimatorSet mAnimControl = null;

    public ConanTabBean(CharSequence icon, CharSequence title, @RawRes int svg) {
        this.ICON = icon;
        this.TITLE = title;
        this.SVG = svg;
    }

    @Override
    public Object setTabLayout() {
        return R.layout.item_bottom_tab_conan;
    }

    @Override
    public void initView(View tabView) {
        mTvIcon = tabView.findViewById(R.id.tvIcon);
        mTvTitle = tabView.findViewById(R.id.tvTitle);

        mTvIcon.setText(ICON);
        mTvTitle.setText(TITLE);

        mTvIcon.setTextSize(getIconSize());
        mTvTitle.setTextSize(getTextSize());

        mTvIcon.setTextColor(getIconSelector());
        mTvTitle.setTextColor(getTextSelector());

        mPathView = tabView.findViewById(R.id.pathView);
        mPathView.setFillAfter(false);
        mPathView.setSvgResource(SVG);

        mTvIcon.setVisibility(View.VISIBLE);
        mPathView.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onNormalState(View tabView, boolean onCreate) {
        if (onCreate) {
            return;
        }
        if (mAnimControl != null) {
            mAnimControl.cancel();
            mAnimControl = null;
        }

        mPathView.setVisibility(View.INVISIBLE);
        mTvIcon.setAlpha(1);
        mTvIcon.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSelectedState(View tabView, boolean onCreate) {
        if (onCreate) {
            return;
        }
        if (mAnimControl != null) {
            mAnimControl.cancel();
            mAnimControl = null;
        }

        mTvIcon.setAlpha(0);
        mTvIcon.setVisibility(View.INVISIBLE);
        mPathView.setAlpha(1);
        mPathView.setVisibility(View.VISIBLE);

        mPathView.getPathAnimator()
                .interpolator(new LinearInterpolator())
                .duration(1314)
                .listenerEnd(() -> {
                    mTvIcon.setVisibility(View.VISIBLE);
                    if (!tabView.isSelected()) {
                        return;
                    }
                    ObjectAnimator pathViewAnim = ObjectAnimator.ofFloat(mPathView, "alpha", 1, 0);
                    ObjectAnimator tvIconAnim = ObjectAnimator.ofFloat(mTvIcon, "alpha", 0, 1);
                    mAnimControl = new AnimatorSet();
                    mAnimControl.setDuration(300);
                    mAnimControl.setInterpolator(new LinearInterpolator());
                    mAnimControl.play(pathViewAnim).with(tvIconAnim);
                    mAnimControl.start();
                })
                .start();

    }
}
