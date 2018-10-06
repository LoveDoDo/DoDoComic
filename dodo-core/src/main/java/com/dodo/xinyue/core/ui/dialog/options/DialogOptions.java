package com.dodo.xinyue.core.ui.dialog.options;

import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.annotation.StyleRes;

import com.dodo.xinyue.core.ui.dialog.callback.ICloseDialog;
import com.dodo.xinyue.core.ui.dialog.callback.IOpenDialog;

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
        OPTIONS.put(OptionFields.CONTEXT, context);
        return  this;
    }

    public final DialogOptions theme(@StyleRes int themeStyleId) {
        OPTIONS.put(OptionFields.DIALOG_THEME, themeStyleId);
        return  this;
    }

    public final DialogOptions anim(@StyleRes int animStyleId) {
        OPTIONS.put(OptionFields.DIALOG_ANIM, animStyleId);
        return  this;
    }

    public final DialogOptions gravity(int gravity) {
        OPTIONS.put(OptionFields.DIALOG_GRAVITY, gravity);
        return  this;
    }

    public final DialogOptions radius(int radius) {
        OPTIONS.put(OptionFields.DIALOG_RADIUS, new int[]{radius, radius, radius, radius});
        return  this;
    }

    public final DialogOptions topLeftRadius(int topLeftRadius) {
        OPTIONS.put(OptionFields.DIALOG_TOP_LEFT_RADIUS, topLeftRadius);
        return this;
    }

    public final DialogOptions topRightRadius(int topRightRadius) {
        OPTIONS.put(OptionFields.DIALOG_TOP_RIGHT_RADIUS, topRightRadius);
        return  this;
    }

    public final DialogOptions bottomLeftRadius(int bottomLeftRadius) {
        OPTIONS.put(OptionFields.DIALOG_BOTTOM_LEFT_RADIUS, bottomLeftRadius);
        return this;
    }

    public final DialogOptions bottomRightRadius(int bottomRightRadius) {
        OPTIONS.put(OptionFields.DIALOG_BOTTOM_RIGHT_RADIUS, bottomRightRadius);
        return this;
    }

    public final DialogOptions widthScale(@FloatRange(from = 0f, to = 1f) float widthScale) {
        OPTIONS.put(OptionFields.WIDTH_SCALE, widthScale);
        return this;
    }

    public final DialogOptions heightScale(@FloatRange(from = 0f, to = 1f) float heightScale) {
        OPTIONS.put(OptionFields.HEIGHT_SCALE, heightScale);
        return this;
    }

    public final DialogOptions canceledOnTouchOutside(boolean canceledOnTouchOutside) {
        OPTIONS.put(OptionFields.CANCELED_ON_TOUCH_OUTSIDE, canceledOnTouchOutside);
        return this;
    }

    public final DialogOptions cancelable(boolean cancelable) {
        OPTIONS.put(OptionFields.CANCELABLE, cancelable);
        return this;
    }

    public final DialogOptions coverStatusBar(boolean coverStatusBar) {
        OPTIONS.put(OptionFields.COVER_STATUSBAR, coverStatusBar);
        return this;
    }

    public final DialogOptions onOpen(IOpenDialog iOpenDialog) {
        OPTIONS.put(OptionFields.IOPEN_DIALOG, iOpenDialog);
        return  this;
    }

    public final DialogOptions onClose(ICloseDialog iCloseDialog) {
        OPTIONS.put(OptionFields.ICLOSE_DIALOG, iCloseDialog);
        return  this;
    }


}
