package com.dodo.xinyue.core.delegates.bottom.bean;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;

import com.dodo.xinyue.core.R;

/**
 * BottomTabImageTextBean
 *
 * @author DoDo
 * @date 2017/9/15
 */
public class BottomTabImageTextBean extends BaseBottomTabBean {

    private final CharSequence TEXT;
    private AppCompatImageView mImageView = null;
    private AppCompatTextView mText = null;

    public BottomTabImageTextBean(CharSequence text) {
        this.TEXT = text;
    }

    public CharSequence getText() {
        return TEXT;
    }

    @Override
    public Object setTabLayout() {
        return R.layout.bottom_tab_image_text;
    }

    @Override
    public void initView(View tabView) {
        if (getImageSelector() == null) {
            throw new RuntimeException("BottomTabImageTextBean must zhiding a ImageSelector");
        }
        mImageView = (AppCompatImageView) ((ViewGroup) tabView).getChildAt(0);
        mText = (AppCompatTextView) ((ViewGroup) tabView).getChildAt(1);

        mText.setText(getText());
        mText.setTextSize(getTextSize());

        mText.setTextColor(getTextSelector());
        mImageView.setImageDrawable(getImageSelector());
    }

    @Override
    public void onNormalState(View tabView, boolean onCreate) {

    }

    @Override
    public void onSelectedState(View tabView, boolean onCreate) {

    }

}
