package com.dodo.xinyue.core.ui.dialog.base;

import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.annotation.StyleRes;

import com.dodo.xinyue.core.ui.dialog.bean.DialogPublicParamsBean;
import com.dodo.xinyue.core.ui.dialog.callback.ICloseDialog;
import com.dodo.xinyue.core.ui.dialog.callback.IOpenDialog;
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
        if (options.isValid(DialogOptionFields.DIALOG_THEME)) {
            theme(options.getOption(DialogOptionFields.DIALOG_THEME));
        }
        if (options.isValid(DialogOptionFields.DIALOG_ANIM)) {
            anim(options.getOption(DialogOptionFields.DIALOG_ANIM));
        }
        if (options.isValid(DialogOptionFields.DIALOG_GRAVITY)) {
            gravity(options.getOption(DialogOptionFields.DIALOG_GRAVITY));
        }
        if (options.isValid(DialogOptionFields.DIALOG_RADIUS)) {
            radius(options.getOption(DialogOptionFields.DIALOG_RADIUS));
        }
        if (options.isValid(DialogOptionFields.DIALOG_TOP_LEFT_RADIUS)) {
            topLeftRadius(options.getOption(DialogOptionFields.DIALOG_TOP_LEFT_RADIUS));
        }
        if (options.isValid(DialogOptionFields.DIALOG_TOP_RIGHT_RADIUS)) {
            topRightRadius(options.getOption(DialogOptionFields.DIALOG_TOP_RIGHT_RADIUS));
        }
        if (options.isValid(DialogOptionFields.DIALOG_BOTTOM_LEFT_RADIUS)) {
            bottomLeftRadius(options.getOption(DialogOptionFields.DIALOG_BOTTOM_LEFT_RADIUS));
        }
        if (options.isValid(DialogOptionFields.DIALOG_BOTTOM_RIGHT_RADIUS)) {
            bottomRightRadius(options.getOption(DialogOptionFields.DIALOG_BOTTOM_RIGHT_RADIUS));
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
        if (options.isValid(DialogOptionFields.COVER_STATUSBAR)) {
            coverStatusBar(options.getOption(DialogOptionFields.COVER_STATUSBAR));
        }
        if (options.isValid(DialogOptionFields.BACKGROUND_DIM_ENABLED)) {
            backgroundDimEnabled(options.getOption(DialogOptionFields.BACKGROUND_DIM_ENABLED));
        }
        if (options.isValid(DialogOptionFields.IOPEN_DIALOG)) {
            onOpen(options.getOption(DialogOptionFields.IOPEN_DIALOG));
        }
        if (options.isValid(DialogOptionFields.ICLOSE_DIALOG)) {
            onClose(options.getOption(DialogOptionFields.ICLOSE_DIALOG));
        }

        return (T) this;
    }

    public final T context(Context context) {
        mDialogPublicParamsBean.setContext(context);
        return (T) this;
    }

    public final T theme(@StyleRes int themeStyleId) {
        mDialogPublicParamsBean.setDialogTheme(themeStyleId);
        return (T) this;
    }

    public final T anim(@StyleRes int animStyleId) {
        mDialogPublicParamsBean.setDialogAnim(animStyleId);
        return (T) this;
    }

    public final T gravity(int gravity) {
        mDialogPublicParamsBean.setDialogGravity(gravity);
        return (T) this;
    }

    public final T radius(int radius) {
        mDialogPublicParamsBean.setDialogRadius(new int[]{radius, radius, radius, radius});
        return (T) this;
    }

    public final T topLeftRadius(int topLeftRadius) {
        mDialogPublicParamsBean.setDialogTopLeftRadius(topLeftRadius);
        return (T) this;
    }

    public final T topRightRadius(int topRightRadius) {
        mDialogPublicParamsBean.setDialogTopRightRadius(topRightRadius);
        return (T) this;
    }

    public final T bottomLeftRadius(int bottomLeftRadius) {
        mDialogPublicParamsBean.setDialogBottomLeftRadius(bottomLeftRadius);
        return (T) this;
    }

    public final T bottomRightRadius(int bottomRightRadius) {
        mDialogPublicParamsBean.setDialogBottomRightRadius(bottomRightRadius);
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

    public final T backgroundDimEnabled(boolean backgroundDimEnabled) {
        mDialogPublicParamsBean.setBackgroundDimEnabled(backgroundDimEnabled);
        return (T) this;
    }

    public final T onOpen(IOpenDialog iOpenDialog) {
        mDialogPublicParamsBean.setIOpenDialog(iOpenDialog);
        return (T) this;
    }

    public final T onClose(ICloseDialog iCloseDialog) {
        mDialogPublicParamsBean.setICloseDialog(iCloseDialog);
        return (T) this;
    }

}
