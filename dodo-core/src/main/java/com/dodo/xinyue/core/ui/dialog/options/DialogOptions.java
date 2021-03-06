package com.dodo.xinyue.core.ui.dialog.options;

import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.annotation.StyleRes;

import com.dodo.xinyue.core.ui.dialog.callback.IClose;
import com.dodo.xinyue.core.ui.dialog.callback.IOpen;

import java.util.HashMap;

/**
 * DialogOptions
 *
 * @author DoDo
 * @date 2018/10/6
 */
public class DialogOptions {

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

    public final DialogOptions context(Context context) {
        OPTIONS.put(DialogOptionFields.CONTEXT, context);
        return this;
    }

    public final DialogOptions theme(@StyleRes int themeStyleId) {
        OPTIONS.put(DialogOptionFields.THEME, themeStyleId);
        return this;
    }

    public final DialogOptions anim(@StyleRes int animStyleId) {
        OPTIONS.put(DialogOptionFields.ANIM, animStyleId);
        return this;
    }

    public final DialogOptions gravity(int gravity) {
        OPTIONS.put(DialogOptionFields.GRAVITY, gravity);
        return this;
    }

    public final DialogOptions radius(int radius) {
        OPTIONS.put(DialogOptionFields.RADIUS, radius);
        return this;
    }

    public final DialogOptions topLeftRadius(int topLeftRadius) {
        OPTIONS.put(DialogOptionFields.TOP_LEFT_RADIUS, topLeftRadius);
        return this;
    }

    public final DialogOptions topRightRadius(int topRightRadius) {
        OPTIONS.put(DialogOptionFields.TOP_RIGHT_RADIUS, topRightRadius);
        return this;
    }

    public final DialogOptions bottomLeftRadius(int bottomLeftRadius) {
        OPTIONS.put(DialogOptionFields.BOTTOM_LEFT_RADIUS, bottomLeftRadius);
        return this;
    }

    public final DialogOptions bottomRightRadius(int bottomRightRadius) {
        OPTIONS.put(DialogOptionFields.BOTTOM_RIGHT_RADIUS, bottomRightRadius);
        return this;
    }

    public final DialogOptions widthScale(@FloatRange(from = 0f, to = 1f) float widthScale) {
        OPTIONS.put(DialogOptionFields.WIDTH_SCALE, widthScale);
        return this;
    }

    public final DialogOptions heightScale(@FloatRange(from = 0f, to = 1f) float heightScale) {
        OPTIONS.put(DialogOptionFields.HEIGHT_SCALE, heightScale);
        return this;
    }

    public final DialogOptions canceledOnTouchOutside(boolean canceledOnTouchOutside) {
        OPTIONS.put(DialogOptionFields.CANCELED_ON_TOUCH_OUTSIDE, canceledOnTouchOutside);
        return this;
    }

    public final DialogOptions cancelable(boolean cancelable) {
        OPTIONS.put(DialogOptionFields.CANCELABLE, cancelable);
        return this;
    }

    public final DialogOptions coverStatusBar(boolean coverStatusBar) {
        OPTIONS.put(DialogOptionFields.COVER_STATUS_BAR, coverStatusBar);
        return this;
    }

    public final DialogOptions coverNavigationBar(boolean coverNavigationBar) {
        OPTIONS.put(DialogOptionFields.COVER_NAVIGATION_BAR, coverNavigationBar);
        return this;
    }

    public final DialogOptions fullScreen(boolean fullScreen) {
        OPTIONS.put(DialogOptionFields.FULL_SCREEN, fullScreen);
        return this;
    }

    public final DialogOptions backgroundDimEnabled(boolean backgroundDimEnabled) {
        OPTIONS.put(DialogOptionFields.BACKGROUND_DIM_ENABLED, backgroundDimEnabled);
        return this;
    }

    public final DialogOptions onOpen(IOpen iOpen) {
        OPTIONS.put(DialogOptionFields.I_OPEN, iOpen);
        return this;
    }

    public final DialogOptions onClose(IClose iClose) {
        OPTIONS.put(DialogOptionFields.I_CLOSE, iClose);
        return this;
    }


}
