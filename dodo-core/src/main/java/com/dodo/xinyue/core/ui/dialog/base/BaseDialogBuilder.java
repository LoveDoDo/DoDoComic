package com.dodo.xinyue.core.ui.dialog.base;

import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.annotation.StyleRes;

import com.dodo.xinyue.core.ui.dialog.bean.DialogPublicParamsBean;
import com.dodo.xinyue.core.ui.dialog.callback.IClose;
import com.dodo.xinyue.core.ui.dialog.callback.IOpen;
import com.dodo.xinyue.core.ui.dialog.options.DialogOptionFields;
import com.dodo.xinyue.core.ui.dialog.options.DialogOptions;

/**
 * BaseDialogBuilder
 *
 * @author DoDo
 * @date 2018/10/4
 */
@SuppressWarnings("unchecked")
public abstract class BaseDialogBuilder<T extends BaseDialogBuilder, K extends BaseDialog> {

    private final DialogPublicParamsBean mDialogPublicParamsBean = new DialogPublicParamsBean();

    /**
     * 获取公共参数
     */
    public final DialogPublicParamsBean getDialogPublicParamsBean() {
        return mDialogPublicParamsBean;
    }

    public abstract K build();

    public final T options(DialogOptions options) {
        if (options.isValid(DialogOptionFields.CONTEXT)) {
            context(options.getOption(DialogOptionFields.CONTEXT));
        }
        if (options.isValid(DialogOptionFields.THEME)) {
            theme(options.getOption(DialogOptionFields.THEME));
        }
        if (options.isValid(DialogOptionFields.ANIM)) {
            anim(options.getOption(DialogOptionFields.ANIM));
        }
        if (options.isValid(DialogOptionFields.GRAVITY)) {
            gravity(options.getOption(DialogOptionFields.GRAVITY));
        }
        if (options.isValid(DialogOptionFields.RADIUS)) {
            radius(options.getOption(DialogOptionFields.RADIUS));
        }
        if (options.isValid(DialogOptionFields.TOP_LEFT_RADIUS)) {
            topLeftRadius(options.getOption(DialogOptionFields.TOP_LEFT_RADIUS));
        }
        if (options.isValid(DialogOptionFields.TOP_RIGHT_RADIUS)) {
            topRightRadius(options.getOption(DialogOptionFields.TOP_RIGHT_RADIUS));
        }
        if (options.isValid(DialogOptionFields.BOTTOM_LEFT_RADIUS)) {
            bottomLeftRadius(options.getOption(DialogOptionFields.BOTTOM_LEFT_RADIUS));
        }
        if (options.isValid(DialogOptionFields.BOTTOM_RIGHT_RADIUS)) {
            bottomRightRadius(options.getOption(DialogOptionFields.BOTTOM_RIGHT_RADIUS));
        }
        if (options.isValid(DialogOptionFields.WIDTH_SCALE)) {
            widthScale(options.getOption(DialogOptionFields.WIDTH_SCALE));
        }
        if (options.isValid(DialogOptionFields.HEIGHT_SCALE)) {
            heightScale(options.getOption(DialogOptionFields.HEIGHT_SCALE));
        }
        if (options.isValid(DialogOptionFields.CANCELED_ON_TOUCH_OUTSIDE)) {
            canceledOnTouchOutside(options.getOption(DialogOptionFields.CANCELED_ON_TOUCH_OUTSIDE));
        }
        if (options.isValid(DialogOptionFields.CANCELABLE)) {
            cancelable(options.getOption(DialogOptionFields.CANCELABLE));
        }
        if (options.isValid(DialogOptionFields.COVER_STATUS_BAR)) {
            coverStatusBar(options.getOption(DialogOptionFields.COVER_STATUS_BAR));
        }
        if (options.isValid(DialogOptionFields.COVER_NAVIGATION_BAR)) {
            coverNavigationBar(options.getOption(DialogOptionFields.COVER_NAVIGATION_BAR));
        }
        if (options.isValid(DialogOptionFields.FULL_SCREEN)) {
            fullScreen(options.getOption(DialogOptionFields.FULL_SCREEN));
        }
        if (options.isValid(DialogOptionFields.BACKGROUND_DIM_ENABLED)) {
            backgroundDimEnabled(options.getOption(DialogOptionFields.BACKGROUND_DIM_ENABLED));
        }
        if (options.isValid(DialogOptionFields.I_OPEN)) {
            onOpen(options.getOption(DialogOptionFields.I_OPEN));
        }
        if (options.isValid(DialogOptionFields.I_CLOSE)) {
            onClose(options.getOption(DialogOptionFields.I_CLOSE));
        }

        return (T) this;
    }

    public final T context(Context context) {
        mDialogPublicParamsBean.setContext(context);
        return (T) this;
    }

    public final T theme(@StyleRes int themeStyleId) {
        mDialogPublicParamsBean.setTheme(themeStyleId);
        return (T) this;
    }

    public final T anim(@StyleRes int animStyleId) {
        mDialogPublicParamsBean.setAnim(animStyleId);
        return (T) this;
    }

    public final T gravity(int gravity) {
        mDialogPublicParamsBean.setGravity(gravity);
        return (T) this;
    }

    public final T radius(int radius) {
        mDialogPublicParamsBean.setRadius(new int[]{radius, radius, radius, radius});
        return (T) this;
    }

    public final T topLeftRadius(int topLeftRadius) {
        mDialogPublicParamsBean.setTopLeftRadius(topLeftRadius);
        return (T) this;
    }

    public final T topRightRadius(int topRightRadius) {
        mDialogPublicParamsBean.setTopRightRadius(topRightRadius);
        return (T) this;
    }

    public final T bottomLeftRadius(int bottomLeftRadius) {
        mDialogPublicParamsBean.setBottomLeftRadius(bottomLeftRadius);
        return (T) this;
    }

    public final T bottomRightRadius(int bottomRightRadius) {
        mDialogPublicParamsBean.setBottomRightRadius(bottomRightRadius);
        return (T) this;
    }

    public final T widthScale(@FloatRange(from = 0f, to = 1f) float widthScale) {
        mDialogPublicParamsBean.setWidthScale(widthScale);
        return (T) this;
    }

    public final T heightScale(@FloatRange(from = 0f, to = 1f) float heightScale) {
        mDialogPublicParamsBean.setHeightScale(heightScale);
        return (T) this;
    }

    public final T canceledOnTouchOutside(boolean canceledOnTouchOutside) {
        mDialogPublicParamsBean.setCanceledOnTouchOutside(canceledOnTouchOutside);
        return (T) this;
    }

    public final T cancelable(boolean cancelable) {
        mDialogPublicParamsBean.setCancelable(cancelable);
        return (T) this;
    }

    public final T coverStatusBar(boolean coverStatusBar) {
        mDialogPublicParamsBean.setCoverStatusBar(coverStatusBar);
        return (T) this;
    }

    public final T coverNavigationBar(boolean coverNavigationBar) {
        mDialogPublicParamsBean.setCoverNavigationBar(coverNavigationBar);
        return (T) this;
    }

    public final T fullScreen(boolean fullScreen) {
        mDialogPublicParamsBean.setFullScreen(fullScreen);
        return (T) this;
    }

    public final T backgroundDimEnabled(boolean backgroundDimEnabled) {
        mDialogPublicParamsBean.setBackgroundDimEnabled(backgroundDimEnabled);
        return (T) this;
    }

    public final T onOpen(IOpen iOpen) {
        mDialogPublicParamsBean.setIOpen(iOpen);
        return (T) this;
    }

    public final T onClose(IClose iClose) {
        mDialogPublicParamsBean.setIClose(iClose);
        return (T) this;
    }

}
