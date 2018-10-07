package com.dodo.xinyue.core.delegates.bottom.bean;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;

import com.dodo.xinyue.core.R;
import com.joanzapata.iconify.widget.IconTextView;

/**
 * BottomTabIconTitleBean
 *
 * @author DoDo
 * @date 2017/9/7
 */
public class BottomTabIconTitleBean extends BaseBottomTabBean {

    /**
     * CharSequence是个接口，String实现了这个接口
     * CharSequence是可读可写序列，String是可读序列
     */

    private final CharSequence ICON;
    private final CharSequence TITLE;
    private IconTextView mIconTv = null;
    private AppCompatTextView mTv = null;

    public BottomTabIconTitleBean(CharSequence icon, CharSequence title) {
        this.ICON = icon;
        this.TITLE = title;
    }

    public CharSequence getIcon() {
        return ICON;
    }

    public CharSequence getTitle() {
        return TITLE;
    }

    @Override
    public Object setTabLayout() {
        return R.layout.bottom_tab_icon_text;
    }

    @Override
    public Object setBigTabLayout() {
        return setTabLayout();
    }

    @Override
    public void initView(View tabView) {
        mIconTv = (IconTextView) ((ViewGroup) tabView).getChildAt(0);
        mTv = (AppCompatTextView) ((ViewGroup) tabView).getChildAt(1);

        mIconTv.setText(getIcon());
        mTv.setText(getTitle());

        mIconTv.setTextSize(getIconSize());
        mTv.setTextSize(getTextSize());

        mIconTv.setTextColor(getIconSelector());
        mTv.setTextColor(getTextSelector());
    }
//
//    @Override
//    public void setNormalState(ViewGroup container) {
//        final IconTextView icon = (IconTextView) container.getChildAt(0);
//        final AppCompatTextView title = (AppCompatTextView) container.getChildAt(1);
//        icon.setText(getIcon());
//        title.setText(getTitle());
//        icon.setTextColor(getNormalTextColor());
//        title.setTextColor(getNormalTextColor());
//        container.setBackgroundColor(getNormalBackgroundColor());
//    }
//
//    @Override
//    public void setSelectedState(ViewGroup container) {
//        final IconTextView icon = (IconTextView) container.getChildAt(0);
//        final AppCompatTextView title = (AppCompatTextView) container.getChildAt(1);
//        icon.setText(getIcon());
//        title.setText(getTitle());
//        icon.setTextColor(getSelectedTextColor());
//        title.setTextColor(getSelectedTextColor());
//        container.setBackgroundColor(getSelectedBackgroundColor());
//    }
}
