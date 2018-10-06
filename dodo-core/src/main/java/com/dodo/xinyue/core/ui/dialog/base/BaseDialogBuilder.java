package com.dodo.xinyue.core.ui.dialog.base;

import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.annotation.StyleRes;

import com.dodo.xinyue.core.ui.dialog.bean.DialogPublicParamsBean;
import com.dodo.xinyue.core.ui.dialog.callback.ICloseDialog;
import com.dodo.xinyue.core.ui.dialog.callback.IOpenDialog;
import com.dodo.xinyue.core.ui.dialog.options.DialogOptions;
import com.dodo.xinyue.core.ui.dialog.options.OptionFields;

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
        if (options.isValid(OptionFields.CONTEXT)) {
            context(options.getOption(OptionFields.CONTEXT));
        }
        if (options.isValid(OptionFields.DIALOG_THEME)) {
            theme(options.getOption(OptionFields.DIALOG_THEME));
        }
        if (options.isValid(OptionFields.DIALOG_ANIM)) {
            anim(options.getOption(OptionFields.DIALOG_ANIM));
        }
        if (options.isValid(OptionFields.DIALOG_GRAVITY)) {
            gravity(options.getOption(OptionFields.DIALOG_GRAVITY));
        }
        if (options.isValid(OptionFields.DIALOG_RADIUS)) {
            radius(options.getOption(OptionFields.DIALOG_RADIUS));
        }
        if (options.isValid(OptionFields.DIALOG_TOP_LEFT_RADIUS)) {
            topLeftRadius(options.getOption(OptionFields.DIALOG_TOP_LEFT_RADIUS));
        }
        if (options.isValid(OptionFields.DIALOG_TOP_RIGHT_RADIUS)) {
            topRightRadius(options.getOption(OptionFields.DIALOG_TOP_RIGHT_RADIUS));
        }
        if (options.isValid(OptionFields.DIALOG_BOTTOM_LEFT_RADIUS)) {
            bottomLeftRadius(options.getOption(OptionFields.DIALOG_BOTTOM_LEFT_RADIUS));
        }
        if (options.isValid(OptionFields.DIALOG_BOTTOM_RIGHT_RADIUS)) {
            bottomRightRadius(options.getOption(OptionFields.DIALOG_BOTTOM_RIGHT_RADIUS));
        }
        if (options.isValid(OptionFields.WIDTH_SCALE)) {
            widthScale(options.getOption(OptionFields.WIDTH_SCALE));
        }
        if (options.isValid(OptionFields.HEIGHT_SCALE)) {
            heightScale(options.getOption(OptionFields.HEIGHT_SCALE));
        }
        if (options.isValid(OptionFields.CANCELED_ON_TOUCH_OUTSIDE)) {
            canceledOnTouchOutside(options.getOption(OptionFields.CANCELED_ON_TOUCH_OUTSIDE));
        }
        if (options.isValid(OptionFields.CANCELABLE)) {
            cancelable(options.getOption(OptionFields.CANCELABLE));
        }
        if (options.isValid(OptionFields.COVER_STATUSBAR)) {
            coverStatusBar(options.getOption(OptionFields.COVER_STATUSBAR));
        }
        if (options.isValid(OptionFields.IOPEN_DIALOG)) {
            onOpen(options.getOption(OptionFields.IOPEN_DIALOG));
        }
        if (options.isValid(OptionFields.ICLOSE_DIALOG)) {
            onClose(options.getOption(OptionFields.ICLOSE_DIALOG));
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

    public final T onOpen(IOpenDialog iOpenDialog) {
        mDialogPublicParamsBean.setIOpenDialog(iOpenDialog);
        return (T) this;
    }

    public final T onClose(ICloseDialog iCloseDialog) {
        mDialogPublicParamsBean.setICloseDialog(iCloseDialog);
        return (T) this;
    }

}
