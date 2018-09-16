package com.dodo.xinyue.core.delegates.bottom.bean;

import android.support.v7.widget.AppCompatImageView;
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
    public int getLayoutId() {
        return R.layout.bottom_tab_image;
    }

    @Override
    public void initView(ViewGroup container) {
        if (getImageSelector() == null) {
            throw new RuntimeException("BottomTabImageBean must zhiding a ImageSelector");
        }
        mImageView = (AppCompatImageView) container.getChildAt(0);

        mImageView.setImageDrawable(getImageSelector());
    }

}
