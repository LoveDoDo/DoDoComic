package com.dodo.xinyue.core.delegates.bottom.options;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import java.util.HashMap;

/**
 * BottomTabBeanOptions
 *
 * @author DoDo
 * @date 2018/10/7
 */
public class BottomTabBeanOptions {

    private final HashMap<Object, Object> OPTIONS = new HashMap<>();

    public final HashMap<Object, Object> getOptions() {
        return OPTIONS;
    }

    public final boolean isValid(Object key) {
        return OPTIONS.get(key) != null;
    }

    @SuppressWarnings("unchecked")
    public final <T> T getOption(Object key) {
        return (T) OPTIONS.get(key);
    }

    public final BottomTabBeanOptions setTextSelector(@ColorRes int textSelectorId) {
        OPTIONS.put(BottomTabBeanOptionFields.TEXT_SELECTOR, textSelectorId);
        return this;
    }

    public final BottomTabBeanOptions setIconSelector(@ColorRes int iconSelectorId) {
        OPTIONS.put(BottomTabBeanOptionFields.ICON_SELECTOR, iconSelectorId);
        return this;
    }

    public final BottomTabBeanOptions setImageSelector(@DrawableRes int imageSelectorId) {
        OPTIONS.put(BottomTabBeanOptionFields.IMAGE_SELECTOR, imageSelectorId);
        return this;
    }

    public final BottomTabBeanOptions setContainerSelector(@DrawableRes int containerSelectorId) {
        OPTIONS.put(BottomTabBeanOptionFields.CONTAINER_SELECTOR, containerSelectorId);
        return this;
    }

    public final BottomTabBeanOptions setTextSize(int textSize) {
        OPTIONS.put(BottomTabBeanOptionFields.TEXT_SIZE, textSize);
        return this;
    }

    public final BottomTabBeanOptions setIconSize(int iconSize) {
        OPTIONS.put(BottomTabBeanOptionFields.ICON_SIZE, iconSize);
        return this;
    }

    public final BottomTabBeanOptions setTabGravity(int gravity) {
        OPTIONS.put(BottomTabBeanOptionFields.TAB_GRAVITY, gravity);
        return this;
    }

}
