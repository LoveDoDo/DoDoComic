package com.dodo.xinyue.core.delegates.bottom.bean;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;

/**
 * BottomTabBean基类
 *
 * @author DoDo
 * @date 2017/9/7
 */
public abstract class BaseBottomTabBean implements IBottomTabBean {

    private ColorStateList mTextSelector = null;
    private ColorStateList mIconSelector = null;
    private Drawable mImageSelector = null;
    private Drawable mContainerSelector = null;

    private int mTextSize =0;
    private int mIconSize =0;

    public int getTextSize() {
        return mTextSize;
    }

    public void setTextSize(int textSize) {
        this.mTextSize = textSize;
    }

    public int getIconSize() {
        return mIconSize;
    }

    public void setIconSize(int iconSize) {
        this.mIconSize = iconSize;
    }

    public ColorStateList getTextSelector() {
        return mTextSelector;
    }

    public void setTextSelector(Context context, @ColorRes int textSelectorId) {
        this.mTextSelector = ContextCompat.getColorStateList(context, textSelectorId);
    }

    public ColorStateList getIconSelector() {
        return mIconSelector;
    }

    public void setIconSelector(Context context, @ColorRes int iconSelectorId) {
        this.mIconSelector = ContextCompat.getColorStateList(context, iconSelectorId);
    }

    public Drawable getImageSelector() {
        return mImageSelector;
    }

    public void setImageSelector(Context context, @DrawableRes int imageSelectorId) {
        this.mImageSelector = ContextCompat.getDrawable(context, imageSelectorId);
    }

    public Drawable getContainerSelector() {
        return mContainerSelector;
    }

    public void setContainerSelector(Context context, @DrawableRes int containerSelectorId) {
        this.mContainerSelector = ContextCompat.getDrawable(context, containerSelectorId);
    }

    private int mNormalTextColor = Color.GRAY;
    private int mSelectedTextColor = Color.parseColor("#ffff8800");
    private int mNormalBackgroundColor = Color.BLUE;
    private int mSelectedBackgroundColor = Color.parseColor("#80cccccc");

    public int getNormalTextColor() {
        return mNormalTextColor;
    }

    public void setNormalTextColor(@ColorInt int normalTextColor) {
        this.mNormalTextColor = normalTextColor;
    }

    public int getSelectedTextColor() {
        return mSelectedTextColor;
    }

    public void setSelectedTextColor(@ColorInt int selectedTextColor) {
        this.mSelectedTextColor = selectedTextColor;
    }

    public int getNormalBackgroundColor() {
        return mNormalBackgroundColor;
    }

    public void setNormalBackgroundColor(@ColorInt int normalBackgroundColor) {
        this.mNormalBackgroundColor = normalBackgroundColor;
    }

    public int getSelectedBackgroundColor() {
        return mSelectedBackgroundColor;
    }

    public void setSelectedBackgroundColor(@ColorInt int selectedBackgroundColor) {
        this.mSelectedBackgroundColor = selectedBackgroundColor;
    }
}
