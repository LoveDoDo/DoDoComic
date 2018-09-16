package com.dodo.xinyue.core.delegates.bottom.builder;

import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.IntRange;

import java.util.WeakHashMap;

/**
 * BottomBarParamsBuilder
 *
 * @author DoDo
 * @date 2017/9/17
 */
public class BottomBarParamsBuilder {

    private final WeakHashMap<BottomBarParamsType, Object> BOTTOM_BAR_PARAMS = new WeakHashMap<>();

    private BottomBarParamsBuilder() {
    }

    public static BottomBarParamsBuilder builder() {
        return new BottomBarParamsBuilder();
    }

    public final BottomBarParamsBuilder setBottomBarHeight(@IntRange(from = 0) int height) {
        BOTTOM_BAR_PARAMS.put(BottomBarParamsType.BOTTOM_BAR_HEIGHT, height);
        return this;
    }

    public final BottomBarParamsBuilder setLineHasVisible(boolean hasVisible) {
        BOTTOM_BAR_PARAMS.put(BottomBarParamsType.LINE_HAS_VISIBLE, hasVisible);
        return this;
    }

    public final BottomBarParamsBuilder setLineHeight(@IntRange(from = 0) int height) {
        BOTTOM_BAR_PARAMS.put(BottomBarParamsType.LINE_HEIGHT, height);
        return this;
    }

    public final BottomBarParamsBuilder setLineBackgroundColor(@ColorInt int color) {
        BOTTOM_BAR_PARAMS.put(BottomBarParamsType.LINE_BACKGROUND_COLOR, color);
        return this;
    }

    public final BottomBarParamsBuilder setLineBackgroundRes(@ColorRes int colorRes) {
        BOTTOM_BAR_PARAMS.put(BottomBarParamsType.LINE_BACKGROUND_RES, colorRes);
        return this;
    }

    public final BottomBarParamsBuilder setBottomBarBackgroundColor(@ColorInt int color) {
        BOTTOM_BAR_PARAMS.put(BottomBarParamsType.BOTTOM_BAR_BACKGROUND_COLOR, color);
        return this;
    }

    public final BottomBarParamsBuilder setBottomBarBackgroundRes(@ColorRes int colorRes) {
        BOTTOM_BAR_PARAMS.put(BottomBarParamsType.BOTTOM_BAR_BACKGROUND_RES, colorRes);
        return this;
    }

    public final BottomBarParamsBuilder setBottomBarPaddingLeftAndRight(int padding) {
        BOTTOM_BAR_PARAMS.put(BottomBarParamsType.BOTTOM_BAR_PADDING_LEFT_AND_RIGHT, padding);
        return this;
    }

    public final WeakHashMap<BottomBarParamsType, Object> build() {
        return BOTTOM_BAR_PARAMS;
    }
}
