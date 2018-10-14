package com.dodo.xinyue.core.delegates.bottom.bean;

import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.ViewGroup;

import com.dodo.xinyue.core.R;

/**
 * BottomTabImageBean
 *
 * @author DoDo
 * @date 2017/9/15
 */
public class BottomTabImageBean extends BaseBottomTabBean {

    private AppCompatImageView mImageView = null;

    @Override
    public Object setTabLayout() {
        return R.layout.bottom_tab_image;
    }

    @Override
    public void initView(View tabView) {
        if (getImageSelector() == null) {
            throw new RuntimeException("BottomTabImageBean must zhiding a ImageSelector");
        }
        mImageView = (AppCompatImageView) ((ViewGroup) tabView).getChildAt(0);

        mImageView.setImageDrawable(getImageSelector());
    }

    @Override
    public void onNormalState(View tabView, boolean onCreate) {

    }

    @Override
    public void onSelectedState(View tabView, boolean onCreate) {

    }

}
