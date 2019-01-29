package com.dodo.xinyue.core.ui.popup.base;

import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.annotation.StyleRes;

import com.dodo.xinyue.core.ui.popup.bean.PopupWindowPublicParamsBean;
import com.dodo.xinyue.core.ui.popup.callback.IClose;
import com.dodo.xinyue.core.ui.popup.callback.IOpen;
import com.dodo.xinyue.core.ui.popup.options.PopupWindowOptionFields;
import com.dodo.xinyue.core.ui.popup.options.PopupWindowOptions;

/**
 * BasePopupWindowBuilder
 *
 * @author DoDo
 * @date 2019/1/24
 */
public abstract class BasePopupWindowBuilder <T extends BasePopupWindowBuilder, K extends BasePopupWindow> {

    private final PopupWindowPublicParamsBean mPopupWindowPublicParamsBean = new PopupWindowPublicParamsBean();

    /**
     * 获取公共参数
     */
    public final PopupWindowPublicParamsBean getPopupWindowPublicParamsBean() {
        return mPopupWindowPublicParamsBean;
    }

    public abstract K build();

    public final T options(PopupWindowOptions options) {
        if (options.isValid(PopupWindowOptionFields.CONTEXT)) {
            context(options.getOption(PopupWindowOptionFields.CONTEXT));
        }
        if (options.isValid(PopupWindowOptionFields.ANIM)) {
            anim(options.getOption(PopupWindowOptionFields.ANIM));
        }
        if (options.isValid(PopupWindowOptionFields.GRAVITY)) {
            gravity(options.getOption(PopupWindowOptionFields.GRAVITY));
        }
        if (options.isValid(PopupWindowOptionFields.RADIUS)) {
            radius(options.getOption(PopupWindowOptionFields.RADIUS));
        }
        if (options.isValid(PopupWindowOptionFields.TOP_LEFT_RADIUS)) {
            topLeftRadius(options.getOption(PopupWindowOptionFields.TOP_LEFT_RADIUS));
        }
        if (options.isValid(PopupWindowOptionFields.TOP_RIGHT_RADIUS)) {
            topRightRadius(options.getOption(PopupWindowOptionFields.TOP_RIGHT_RADIUS));
        }
        if (options.isValid(PopupWindowOptionFields.BOTTOM_LEFT_RADIUS)) {
            bottomLeftRadius(options.getOption(PopupWindowOptionFields.BOTTOM_LEFT_RADIUS));
        }
        if (options.isValid(PopupWindowOptionFields.BOTTOM_RIGHT_RADIUS)) {
            bottomRightRadius(options.getOption(PopupWindowOptionFields.BOTTOM_RIGHT_RADIUS));
        }
        if (options.isValid(PopupWindowOptionFields.WIDTH_SCALE)) {
            widthScale(options.getOption(PopupWindowOptionFields.WIDTH_SCALE));
        }
        if (options.isValid(PopupWindowOptionFields.HEIGHT_SCALE)) {
            heightScale(options.getOption(PopupWindowOptionFields.HEIGHT_SCALE));
        }
        if (options.isValid(PopupWindowOptionFields.CANCELED_ON_TOUCH_OUTSIDE)) {
            canceledOnTouchOutside(options.getOption(PopupWindowOptionFields.CANCELED_ON_TOUCH_OUTSIDE));
        }
        if (options.isValid(PopupWindowOptionFields.CANCELABLE)) {
            cancelable(options.getOption(PopupWindowOptionFields.CANCELABLE));
        }
        if (options.isValid(PopupWindowOptionFields.COVER_STATUS_BAR)) {
            coverStatusBar(options.getOption(PopupWindowOptionFields.COVER_STATUS_BAR));
        }
        if (options.isValid(PopupWindowOptionFields.COVER_NAVIGATION_BAR)) {
            coverNavigationBar(options.getOption(PopupWindowOptionFields.COVER_NAVIGATION_BAR));
        }
        if (options.isValid(PopupWindowOptionFields.FULL_SCREEN)) {
            fullScreen(options.getOption(PopupWindowOptionFields.FULL_SCREEN));
        }
        if (options.isValid(PopupWindowOptionFields.BACKGROUND_DIM_ENABLED)) {
            backgroundDimEnabled(options.getOption(PopupWindowOptionFields.BACKGROUND_DIM_ENABLED));
        }
        if (options.isValid(PopupWindowOptionFields.I_OPEN)) {
            onOpen(options.getOption(PopupWindowOptionFields.I_OPEN));
        }
        if (options.isValid(PopupWindowOptionFields.I_CLOSE)) {
            onClose(options.getOption(PopupWindowOptionFields.I_CLOSE));
        }

        return (T) this;
    }

    public final T context(Context context) {
        mPopupWindowPublicParamsBean.setContext(context);
        return (T) this;
    }

    public final T anim(@StyleRes int animStyleId) {
        mPopupWindowPublicParamsBean.setAnim(animStyleId);
        return (T) this;
    }

    public final T gravity(int gravity) {
        mPopupWindowPublicParamsBean.setGravity(gravity);
        return (T) this;
    }

    public final T radius(int radius) {
        mPopupWindowPublicParamsBean.setRadius(new int[]{radius, radius, radius, radius});
        return (T) this;
    }

    public final T topLeftRadius(int topLeftRadius) {
        mPopupWindowPublicParamsBean.setTopLeftRadius(topLeftRadius);
        return (T) this;
    }

    public final T topRightRadius(int topRightRadius) {
        mPopupWindowPublicParamsBean.setTopRightRadius(topRightRadius);
        return (T) this;
    }

    public final T bottomLeftRadius(int bottomLeftRadius) {
        mPopupWindowPublicParamsBean.setBottomLeftRadius(bottomLeftRadius);
        return (T) this;
    }

    public final T bottomRightRadius(int bottomRightRadius) {
        mPopupWindowPublicParamsBean.setBottomRightRadius(bottomRightRadius);
        return (T) this;
    }

    public final T widthScale(@FloatRange(from = 0f, to = 1f) float widthScale) {
        mPopupWindowPublicParamsBean.setWidthScale(widthScale);
        return (T) this;
    }

    public final T heightScale(@FloatRange(from = 0f, to = 1f) float heightScale) {
        mPopupWindowPublicParamsBean.setHeightScale(heightScale);
        return (T) this;
    }

    public final T canceledOnTouchOutside(boolean canceledOnTouchOutside) {
        mPopupWindowPublicParamsBean.setCanceledOnTouchOutside(canceledOnTouchOutside);
        return (T) this;
    }

    public final T cancelable(boolean cancelable) {
        mPopupWindowPublicParamsBean.setCancelable(cancelable);
        return (T) this;
    }

    public final T coverStatusBar(boolean coverStatusBar) {
        mPopupWindowPublicParamsBean.setCoverStatusBar(coverStatusBar);
        return (T) this;
    }

    public final T coverNavigationBar(boolean coverNavigationBar) {
        mPopupWindowPublicParamsBean.setCoverNavigationBar(coverNavigationBar);
        return (T) this;
    }

    public final T fullScreen(boolean fullScreen) {
        mPopupWindowPublicParamsBean.setFullScreen(fullScreen);
        return (T) this;
    }

    public final T backgroundDimEnabled(boolean backgroundDimEnabled) {
        mPopupWindowPublicParamsBean.setBackgroundDimEnabled(backgroundDimEnabled);
        return (T) this;
    }

    public final T dimCount(float dimCount) {
        mPopupWindowPublicParamsBean.setDimCount(dimCount);
        return (T) this;
    }

    public final T dimTime(long dimTime) {
        mPopupWindowPublicParamsBean.setDimTime(dimTime);
        return (T) this;
    }

    public final T onOpen(IOpen iOpen) {
        mPopupWindowPublicParamsBean.setIOpen(iOpen);
        return (T) this;
    }

    public final T onClose(IClose iClose) {
        mPopupWindowPublicParamsBean.setIClose(iClose);
        return (T) this;
    }

}
