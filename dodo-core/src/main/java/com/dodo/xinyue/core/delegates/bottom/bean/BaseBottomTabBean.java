package com.dodo.xinyue.core.delegates.bottom.bean;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;

import com.dodo.xinyue.core.app.DoDo;

import java.util.Objects;

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

    public void setTextSelector(@ColorRes int textSelectorId) {
        this.mTextSelector = ContextCompat.getColorStateList(DoDo.getAppContext(), textSelectorId);
    }

    public ColorStateList getIconSelector() {
        return mIconSelector;
    }

    public void setIconSelector(@ColorRes int iconSelectorId) {
        this.mIconSelector = ContextCompat.getColorStateList(DoDo.getAppContext(), iconSelectorId);
    }

    public Drawable getImageSelector() {
        return mImageSelector;
    }

    public void setImageSelector(@DrawableRes int imageSelectorId) {
        this.mImageSelector = ContextCompat.getDrawable(DoDo.getAppContext(), imageSelectorId);
    }

    public Drawable getContainerSelector() {
        return mContainerSelector;
    }

    public void setContainerSelector(@DrawableRes int containerSelectorId) {
        this.mContainerSelector = ContextCompat.getDrawable(DoDo.getAppContext(), containerSelectorId);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseBottomTabBean that = (BaseBottomTabBean) o;
        return mTextSize == that.mTextSize &&
                mIconSize == that.mIconSize &&
                mNormalTextColor == that.mNormalTextColor &&
                mSelectedTextColor == that.mSelectedTextColor &&
                mNormalBackgroundColor == that.mNormalBackgroundColor &&
                mSelectedBackgroundColor == that.mSelectedBackgroundColor &&
                Objects.equals(mTextSelector, that.mTextSelector) &&
                Objects.equals(mIconSelector, that.mIconSelector) &&
                Objects.equals(mImageSelector, that.mImageSelector) &&
                Objects.equals(mContainerSelector, that.mContainerSelector);
    }

    @Override
    public int hashCode() {

        return Objects.hash(mTextSelector, mIconSelector, mImageSelector, mContainerSelector, mTextSize, mIconSize, mNormalTextColor, mSelectedTextColor, mNormalBackgroundColor, mSelectedBackgroundColor);
    }
}
