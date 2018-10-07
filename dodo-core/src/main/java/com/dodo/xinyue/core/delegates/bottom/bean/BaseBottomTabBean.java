package com.dodo.xinyue.core.delegates.bottom.bean;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
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

    private int mTextSize = 0;
    private int mIconSize = 0;

    private int mTabGravity = 0;

    public int getTabGravity() {
        return mTabGravity;
    }

    public void setTabGravity(int gravity) {
        this.mTabGravity = gravity;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseBottomTabBean that = (BaseBottomTabBean) o;
        return mTextSize == that.mTextSize &&
                mIconSize == that.mIconSize &&
                mTabGravity == that.mTabGravity &&
                Objects.equals(mTextSelector, that.mTextSelector) &&
                Objects.equals(mIconSelector, that.mIconSelector) &&
                Objects.equals(mImageSelector, that.mImageSelector) &&
                Objects.equals(mContainerSelector, that.mContainerSelector);
    }

    @Override
    public int hashCode() {

        return Objects.hash(mTextSelector, mIconSelector, mImageSelector, mContainerSelector, mTextSize, mIconSize, mTabGravity);
    }
}
