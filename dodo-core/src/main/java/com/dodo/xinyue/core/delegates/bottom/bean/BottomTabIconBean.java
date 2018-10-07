package com.dodo.xinyue.core.delegates.bottom.bean;

import android.view.View;
import android.view.ViewGroup;

import com.dodo.xinyue.core.R;
import com.joanzapata.iconify.widget.IconTextView;

/**
 * BottomTabIconBean
 *
 * @author DoDo
 * @date 2017/9/7
 */
public class BottomTabIconBean extends BaseBottomTabBean {

    private final CharSequence ICON;
    private IconTextView mIconTv = null;

    public BottomTabIconBean(CharSequence icon) {
        this.ICON = icon;
    }

    public CharSequence getIcon() {
        return ICON;
    }

    @Override
    public Object setTabLayout() {
        return R.layout.bottom_tab_icon;
    }

    @Override
    public Object setBigTabLayout() {
        return setTabLayout();
    }

    @Override
    public void initView(View tabView) {
        mIconTv = (IconTextView) ((ViewGroup) tabView).getChildAt(0);

        mIconTv.setText(getIcon());
        mIconTv.setTextSize(getIconSize());

        mIconTv.setTextColor(getIconSelector());
    }
//
//    @Override
//    public void setNormalState(ViewGroup container) {
//        final IconTextView icon = (IconTextView) container.getChildAt(0);
//        icon.setText(getIcon());
//        icon.setTextColor(getNormalTextColor());
//        container.setBackgroundColor(getNormalBackgroundColor());
//    }
//
//    @Override
//    public void setSelectedState(ViewGroup container) {
//        final IconTextView icon = (IconTextView) container.getChildAt(0);
//        icon.setText(getIcon());
//        icon.setTextColor(getSelectedTextColor());
//        container.setBackgroundColor(getSelectedBackgroundColor());
//    }

}
