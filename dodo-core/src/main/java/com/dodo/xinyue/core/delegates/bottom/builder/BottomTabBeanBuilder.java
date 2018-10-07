package com.dodo.xinyue.core.delegates.bottom.builder;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.view.Gravity;

import com.dodo.xinyue.core.R;
import com.dodo.xinyue.core.app.DoDo;
import com.dodo.xinyue.core.delegates.bottom.bean.BaseBottomTabBean;
import com.dodo.xinyue.core.delegates.bottom.options.BottomTabBeanOptionFields;
import com.dodo.xinyue.core.delegates.bottom.options.BottomTabBeanOptions;
import com.dodo.xinyue.core.util.dimen.DimenUtil;

/**
 * BottomTabBeanBuilder
 *
 * @author DoDo
 * @date 2017/9/15
 */
public final class BottomTabBeanBuilder {

    private BaseBottomTabBean mTabBean = null;

    private BottomTabBeanBuilder() {
    }

    public static BottomTabBeanBuilder builder() {
        return new BottomTabBeanBuilder();
    }

    public final BottomTabBeanBuilder setOptions(BottomTabBeanOptions options) {
        if (options.isValid(BottomTabBeanOptionFields.TEXT_SELECTOR)) {
            setTextSelector(options.getOption(BottomTabBeanOptionFields.TEXT_SELECTOR));
        }
        if (options.isValid(BottomTabBeanOptionFields.ICON_SELECTOR)) {
            setIconSelector(options.getOption(BottomTabBeanOptionFields.ICON_SELECTOR));
        }
        if (options.isValid(BottomTabBeanOptionFields.IMAGE_SELECTOR)) {
            setImageSelector(options.getOption(BottomTabBeanOptionFields.IMAGE_SELECTOR));
        }
        if (options.isValid(BottomTabBeanOptionFields.CONTAINER_SELECTOR)) {
            setContainerSelector(options.getOption(BottomTabBeanOptionFields.CONTAINER_SELECTOR));
        }
        if (options.isValid(BottomTabBeanOptionFields.TEXT_SIZE)) {
            setTextSize(options.getOption(BottomTabBeanOptionFields.TEXT_SIZE));
        }
        if (options.isValid(BottomTabBeanOptionFields.ICON_SIZE)) {
            setIconSize(options.getOption(BottomTabBeanOptionFields.ICON_SIZE));
        }
        if (options.isValid(BottomTabBeanOptionFields.TAB_GRAVITY)) {
            setTabGravity(options.getOption(BottomTabBeanOptionFields.TAB_GRAVITY));
        }

        return this;
    }

    /**
     * 必须先设置TabBean,不然会空指针
     */
    public final BottomTabBeanBuilder setTabBean(BaseBottomTabBean bean) {
        mTabBean = bean;
        return this;
    }

    public final BottomTabBeanBuilder setTextSelector(@ColorRes int textSelectorId) {
        mTabBean.setTextSelector(textSelectorId);
        return this;
    }

    public final BottomTabBeanBuilder setIconSelector(@ColorRes int iconSelectorId) {
        mTabBean.setIconSelector(iconSelectorId);
        return this;
    }

    public final BottomTabBeanBuilder setImageSelector(@DrawableRes int imageSelectorId) {
        mTabBean.setImageSelector(imageSelectorId);
        return this;
    }

    public final BottomTabBeanBuilder setContainerSelector(@DrawableRes int containerSelectorId) {
        mTabBean.setContainerSelector(containerSelectorId);
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

    public final BottomTabBeanBuilder setTabGravity(int gravity) {
        mTabBean.setTabGravity(gravity);
        return this;
    }

    public final BaseBottomTabBean build() {
        if (mTabBean.getContainerSelector() == null) {
            mTabBean.setContainerSelector(R.drawable.selector_bottom_tab_container_background);
        }
        if (mTabBean.getTextSelector() == null) {
            mTabBean.setTextSelector(R.color.selector_bottom_tab_text_color);
        }
        if (mTabBean.getIconSelector() == null) {
            mTabBean.setIconSelector(R.color.selector_bottom_tab_icon_color);
        }
        if (mTabBean.getTextSize() == 0) {
            //.getResources().getDimension() 返回的是px 需要转换为sp
            mTabBean.setTextSize(DimenUtil.px2sp(DoDo.getAppContext().getResources().getDimension(R.dimen.bottom_tab_text_size)));
        }
        if (mTabBean.getIconSize() == 0) {
            mTabBean.setIconSize(DimenUtil.px2sp(DoDo.getAppContext().getResources().getDimension(R.dimen.bottom_tab_icon_size)));
        }
        if (mTabBean.getTabGravity() == 0) {
            mTabBean.setTabGravity(Gravity.CENTER);
        }
        return mTabBean;
    }
}
