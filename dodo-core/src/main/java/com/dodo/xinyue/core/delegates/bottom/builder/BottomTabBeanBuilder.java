package com.dodo.xinyue.core.delegates.bottom.builder;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import com.dodo.xinyue.core.R;
import com.dodo.xinyue.core.delegates.bottom.bean.BaseBottomTabBean;
import com.dodo.xinyue.core.util.dimen.DimenUtil;

/**
 * BottomTabBeanBuilder
 *
 * @author DoDo
 * @date 2017/9/15
 */
public final class BottomTabBeanBuilder {

    private BaseBottomTabBean mTabBean = null;
    private Context mContext = null;

    private BottomTabBeanBuilder(Context context) {
        this.mContext = context;
    }

    public static BottomTabBeanBuilder builder(Context context) {
        return new BottomTabBeanBuilder(context);
    }

    public final BottomTabBeanBuilder setTabBean(BaseBottomTabBean bean) {
        mTabBean = bean;
        return this;
    }

    public final BottomTabBeanBuilder setTextSelector(@ColorRes int textSelectorId) {
        mTabBean.setTextSelector(mContext, textSelectorId);
        return this;
    }

    public final BottomTabBeanBuilder setIconSelector(@ColorRes int iconSelectorId) {
        mTabBean.setIconSelector(mContext, iconSelectorId);
        return this;
    }

    public final BottomTabBeanBuilder setImageSelector(@DrawableRes int imageSelectorId) {
        mTabBean.setImageSelector(mContext, imageSelectorId);
        return this;
    }

    public final BottomTabBeanBuilder setContainerSelector(@DrawableRes int containerSelectorId) {
        mTabBean.setContainerSelector(mContext, containerSelectorId);
        return this;
    }

    public final BottomTabBeanBuilder setTextSize(int textSize) {
        mTabBean.setTextSize(textSize);
        return this;
    }

    public final BottomTabBeanBuilder setIconSize(int iconSize) {
        mTabBean.setIconSize(iconSize);
        return this;
    }

    public final BaseBottomTabBean build() {
        if (mTabBean.getContainerSelector() == null) {
            mTabBean.setContainerSelector(mContext, R.drawable.selector_bottom_tab_container_background);
        }
        if (mTabBean.getTextSelector() == null) {
            mTabBean.setTextSelector(mContext, R.color.selector_bottom_tab_text_color);
        }
        if (mTabBean.getIconSelector() == null) {
            mTabBean.setIconSelector(mContext, R.color.selector_bottom_tab_icon_color);
        }
        if (mTabBean.getTextSize() == 0) {
            //.getResources().getDimension() 返回的是px 需要转换为sp
            mTabBean.setTextSize(DimenUtil.px2sp(mContext.getResources().getDimension(R.dimen.bottom_tab_text_size)));
        }
        if (mTabBean.getIconSize() == 0) {
            mTabBean.setIconSize(DimenUtil.px2sp(mContext.getResources().getDimension(R.dimen.bottom_tab_icon_size)));
        }
        return mTabBean;
    }
}
