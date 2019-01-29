package com.dodo.xinyue.core.ui.popup.options;

import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.annotation.StyleRes;

import com.dodo.xinyue.core.ui.popup.callback.IClose;
import com.dodo.xinyue.core.ui.popup.callback.IOpen;

import java.util.HashMap;

/**
 * PopupWindowOptions
 *
 * @author DoDo
 * @date 2019/1/24
 */
public class PopupWindowOptions {

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

    public final PopupWindowOptions context(Context context) {
        OPTIONS.put(PopupWindowOptionFields.CONTEXT, context);
        return this;
    }

    public final PopupWindowOptions anim(@StyleRes int animStyleId) {
        OPTIONS.put(PopupWindowOptionFields.ANIM, animStyleId);
        return this;
    }

    public final PopupWindowOptions gravity(int gravity) {
        OPTIONS.put(PopupWindowOptionFields.GRAVITY, gravity);
        return this;
    }

    public final PopupWindowOptions radius(int radius) {
        OPTIONS.put(PopupWindowOptionFields.RADIUS, radius);
        return this;
    }

    public final PopupWindowOptions topLeftRadius(int topLeftRadius) {
        OPTIONS.put(PopupWindowOptionFields.TOP_LEFT_RADIUS, topLeftRadius);
        return this;
    }

    public final PopupWindowOptions topRightRadius(int topRightRadius) {
        OPTIONS.put(PopupWindowOptionFields.TOP_RIGHT_RADIUS, topRightRadius);
        return this;
    }

    public final PopupWindowOptions bottomLeftRadius(int bottomLeftRadius) {
        OPTIONS.put(PopupWindowOptionFields.BOTTOM_LEFT_RADIUS, bottomLeftRadius);
        return this;
    }

    public final PopupWindowOptions bottomRightRadius(int bottomRightRadius) {
        OPTIONS.put(PopupWindowOptionFields.BOTTOM_RIGHT_RADIUS, bottomRightRadius);
        return this;
    }

    public final PopupWindowOptions widthScale(@FloatRange(from = 0f, to = 1f) float widthScale) {
        OPTIONS.put(PopupWindowOptionFields.WIDTH_SCALE, widthScale);
        return this;
    }

    public final PopupWindowOptions heightScale(@FloatRange(from = 0f, to = 1f) float heightScale) {
        OPTIONS.put(PopupWindowOptionFields.HEIGHT_SCALE, heightScale);
        return this;
    }

    public final PopupWindowOptions canceledOnTouchOutside(boolean canceledOnTouchOutside) {
        OPTIONS.put(PopupWindowOptionFields.CANCELED_ON_TOUCH_OUTSIDE, canceledOnTouchOutside);
        return this;
    }

    public final PopupWindowOptions cancelable(boolean cancelable) {
        OPTIONS.put(PopupWindowOptionFields.CANCELABLE, cancelable);
        return this;
    }

    public final PopupWindowOptions coverStatusBar(boolean coverStatusBar) {
        OPTIONS.put(PopupWindowOptionFields.COVER_STATUS_BAR, coverStatusBar);
        return this;
    }

    public final PopupWindowOptions coverNavigationBar(boolean coverNavigationBar) {
        OPTIONS.put(PopupWindowOptionFields.COVER_NAVIGATION_BAR, coverNavigationBar);
        return this;
    }

    public final PopupWindowOptions fullScreen(boolean fullScreen) {
        OPTIONS.put(PopupWindowOptionFields.FULL_SCREEN, fullScreen);
        return this;
    }

    public final PopupWindowOptions backgroundDimEnabled(boolean backgroundDimEnabled) {
        OPTIONS.put(PopupWindowOptionFields.BACKGROUND_DIM_ENABLED, backgroundDimEnabled);
        return this;
    }

    public final PopupWindowOptions onOpen(IOpen iOpen) {
        OPTIONS.put(PopupWindowOptionFields.I_OPEN, iOpen);
        return this;
    }

    public final PopupWindowOptions onClose(IClose iClose) {
        OPTIONS.put(PopupWindowOptionFields.I_CLOSE, iClose);
        return this;
    }

}
